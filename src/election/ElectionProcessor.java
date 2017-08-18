package election;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.CallFuture;
import org.apache.avro.ipc.SaslSocketTransceiver;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;

import managers.ReplicatedManager;
import protocols.replication.Replication;
import structures.Entity;

public class ElectionProcessor {
	ReplicatedManager manager;
	Replication.Callback replicator;
	Entity next = null;
	Boolean partcipant = false;
	
	public ElectionProcessor(ReplicatedManager m){
		this.manager = m;
	}
	
	// starts an election round
	public void start(){
		// Only device capable of backup controller so start
		if(this.isOnlyBackupController()){
			this.manager.startBackupController();
			return;
		}
		
		this.connectRing();
		
		// Start election
		this.partcipant = true;
		this.sendElection(this.manager.structure.id, this.manager.structure.id);
	}
	
	// Make connection with the next device in ring
	public void connectRing(){
		if(this.next != null){
			// Already connected
			return;
		}
		
		// Connect with the next entity in the ring
		next = this.nextDeviceInRing();
		try {
			this.connectWithEntity(next);
		} catch (IOException e) {
			// Couldn't connect
			System.out.println("[ERROR] Couldn't connect with next device in ring, so this device becomes the controller");
			this.manager.startBackupController();
			return;
		}
	}
	
	// Check if this device is the only one capable of running a backup controller
	public Boolean isOnlyBackupController(){
		int fridges = this.manager.backupControllerStructure.fridges.size();
		int users = this.manager.backupControllerStructure.users.size();
		
		if(fridges + users == 1){
			return true;
		}else{
			return false;
		}
	}
	
	// Get information about the next device in the ring
	public Entity nextDeviceInRing(){
		Entity entity = null;
		Entity currentEntity = this.manager.structure;
		Map<Integer, Entity> fridges = this.manager.backupControllerStructure.fridges;
		Map<Integer, Entity> users = this.manager.backupControllerStructure.users;
		
		if(this.manager.structure.type.toString().equals("FRIDGE")){
			// Sort the keys from smal to large
			SortedSet<Integer> fridgeIds = new TreeSet<Integer>(fridges.keySet());
			
			if(currentEntity.id == fridgeIds.last()){
				// No more fridges
				SortedSet<Integer> userIds = new TreeSet<Integer>(users.keySet());
				if(userIds.size() == 0){
					// there are no users so select the first fridge
					entity = fridges.get(fridgeIds.first());
				}else{
					// select the first user
					entity = users.get(userIds.first());
				}
			}else{
				// there are plenty fridges available, select the fidge with the next id
				Iterator it = fridgeIds.iterator();
				Boolean check = false;
				while(it.hasNext()){
					if(check == true){
						entity = fridges.get(it.next());
						break;
					}
					if(it.next().equals(currentEntity.id)){
						check = true;
					}
				}
			}
		}else if(this.manager.structure.type.toString().equals("USER")){
			// Sort the keys from smal to large
			SortedSet<Integer> userIds = new TreeSet<Integer>(users.keySet());
			
			if(currentEntity.id == userIds.last()){
				// No more users
				SortedSet<Integer> fridgesIds = new TreeSet<Integer>(fridges.keySet());
				if(fridgesIds.size() == 0){
					// there are no fridges so select the first user
					entity = users.get(userIds.first());
				}else{
					// select the first fridge
					entity = fridges.get(fridgesIds.first());
				}
			}else{
				// there are plenty users available, select the users with the next id
				Iterator it = userIds.iterator();
				Boolean check = false;
				while(it.hasNext()){
					if(check == true){
						entity = users.get(it.next());
						break;
					}
					if(it.next().equals(currentEntity.id)){
						check = true;
					}
				}
			}
		}else{
			System.out.println("[WARNING] Device cannot be elected because its not a fridge or user");
		}
		
		return entity;
	}

	// Connect with the next device in ring
	public void connectWithEntity(Entity e) throws IOException{
		InetAddress replicationIpAddress = InetAddress.getByName(e.ipAdress);
		InetSocketAddress replicationSocketAddress = new InetSocketAddress(replicationIpAddress, e.port + 1);
		Transceiver replicationClient = new SaslSocketTransceiver(replicationSocketAddress);
		replicator = (Replication.Callback) SpecificRequestor.getClient(Replication.Callback.class, replicationClient);
	}
	
	// Process an incoming election message
	public void processElection(int entityId, int score){
		int id = this.manager.structure.id; // this device id
		Boolean becomeController = false; // When something goes wrong with sending messages to next entities this becomes true
		if(score > id){
			// It's not our turn
			becomeController = this.sendElection(entityId, score);
			this.partcipant = true;
		}else if(entityId <= id && (entityId != id)){
			if(this.partcipant == false){
				becomeController = this.sendElection(id, id);
				this.partcipant = true;
			}
		}else if(entityId == id){
			// Election selected me!
			this.partcipant = false;
			this.sendElected(id);
			
			this.manager.startBackupController();
			return;
		}
		
		if(becomeController == true){
			this.manager.startBackupController();
		}
	}
	
	class SendElection implements Runnable{

		int entityId = 0;
		int score = 0;
		
		public SendElection(int entityId, int score) {
			this.entityId = entityId;
			this.score = score;
		}
		
		@Override
		public void run() {
			try {
				CallFuture<Void> future = new CallFuture<Void>();
				replicator.election(entityId, score, future);
			} catch (IOException e) {
				System.out.println("[ERROR] Couldn't connect with next device in ring, so this device becomes the controller");
			}
			
		}
		
	}
	
	// return true when something went wrong and this device now becomes the controller
	public Boolean sendElection(int entityId, int score){
		this.connectRing();
	
		Thread t = new Thread(new SendElection(entityId, score));
		t.start();
		
		return false;
	}
	
	public void processElected(int entityId){
		if(entityId != this.manager.structure.id){
			this.partcipant = false;
			this.sendElected(entityId);
		}
	}
	
	class SendElected implements Runnable{

		int entityId = 0;
		
		public SendElected(int entityId) {
			this.entityId = entityId;
		}
		
		@Override
		public void run() {
			try {
				CallFuture<Void> future = new CallFuture<Void>();
				replicator.elected(entityId, future);
			} catch (IOException e) {
				System.out.println("[ERROR] Couldn't connect with next device in ring");
			}
			
		}
		
	}
	
	
	public void sendElected(int entityId){
		this.connectRing();
		
		Thread t = new Thread(new SendElected(entityId));
		t.start();

	}
}
