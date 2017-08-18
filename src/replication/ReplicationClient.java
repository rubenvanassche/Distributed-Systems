package replication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.SaslSocketTransceiver;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;

import protocols.replication.Controller;
import protocols.replication.Replication;

public class ReplicationClient {
	public List<Replication> replicators = new LinkedList<Replication>(); // RPC Connection to replication servers
	public structures.Controller controller; // Structure
	public ReplicationConversion convertor = new ReplicationConversion();
	
	public ReplicationClient(structures.Controller c){
		this.controller = c;
	}
	
	public void addReplicator(Replication r){
		this.replicators.add(r);
	}
	
	// Builds a connection to a replication server and adds it to the list of replication servers
	public void buildReplicator(protocols.controller.Device device){
		try{
			InetAddress replicationIpAddress = InetAddress.getByName(device.getIpadress().toString());
			InetSocketAddress replicationSocketAddress = new InetSocketAddress(replicationIpAddress, device.getPort() + 1);
			Transceiver replicationClient = new SaslSocketTransceiver(replicationSocketAddress);
			
			Replication replicator = (Replication) SpecificRequestor.getClient(Replication.class, replicationClient);
			
			this.addReplicator(replicator);
			
			// Do an initial handshake
			this.handshake(replicator);
		}catch(IOException e){
			System.err.println("[Error] Connecting to replication server : " + e.getMessage());
		}
		
	}
	
	// Send all the information we have to the replication server
	public void handshake(Replication replicator){
		protocols.replication.Controller c = this.convertor.convert(this.controller);
		
		try {
			replicator.handshake(c);
		} catch (AvroRemoteException e) {
			System.err.println("[ERROR] Couldn't execute an initial handschake");
		}
	}
	
	public void registerEntity(structures.Entity e){
		protocols.replication.Entity entity = this.convertor.convert(e);
		
		for(Replication replicator : this.replicators){
			try {
				replicator.registerEntity(entity);
			} catch (AvroRemoteException ex) {
				System.err.println("[ERROR] Couldn't register entity for replication");
			}
		}
	}
	
	public void addTemperatureHistory(structures.TemperatureHistory th){
		protocols.replication.TemperatureHistory temperatureHistory = this.convertor.convert(th);
		
		for(Replication replicator : this.replicators){
			try {
				replicator.addTemperatureHistory(temperatureHistory);
			} catch (AvroRemoteException ex) {
				System.err.println("[ERROR] Couldn't register temperature history for replication");
			}
		}
	}
	
	public void updateLightStatus(List<protocols.controller.LightStatus> ls){
		List<protocols.replication.LightStatus> lightstatusses = this.convertor.convert(ls);
		
		for(Replication replicator : this.replicators){
			try {
				replicator.updateLightStatus(lightstatusses);
			} catch (AvroRemoteException ex) {
				System.err.println("[ERROR] Couldn't register light statusses update for replication");
			}
		}
	}
	
	public void updateFridge(structures.FridgeStatus fs){
		protocols.replication.FridgeStatus fridgeStatus = this.convertor.convert(fs);
		
		for(Replication replicator : this.replicators){
			try {
				replicator.updateFridge(fridgeStatus);
			} catch (AvroRemoteException ex) {
				System.err.println("[ERROR] Couldn't register fridge status update for replication");
			}
		}
	}

}
