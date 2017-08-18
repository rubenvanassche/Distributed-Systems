package managers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.SaslSocketServer;
import org.apache.avro.ipc.SaslSocketTransceiver;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.avro.ipc.specific.SpecificResponder;

import asg.cliche.Command;
import core.DeviceFactory;
import core.ManagerFactory;
import election.ElectionProcessor;
import protocols.fridge.Fridge;
import protocols.light.Light;
import protocols.sensor.Sensor;
import protocols.user.User;
import replication.ReplicationServer;
import structures.Controller;
import structures.Device;
import structures.Entity;
import structures.Entity.Type;

public class ReplicatedManager extends ControlledManager {
	public Controller backupControllerStructure;
	ControllerManager backupControllerManager = null;
	Boolean isBackup = false;
	Server replicationServer = null;
	public ElectionProcessor electionProcessor = null;
	public protocols.controller.Controller originalController = null; // RPC connection to the original controller
	Timer timer = null; // For original controller pinging purposes
	
	// Starts an replication server
	class ReplicationServerTask extends Thread{
		ReplicatedManager manager = null;
		
		public ReplicationServerTask(ReplicatedManager m) {
			this.manager = m;
		}
			
		public void run() {
			// Build a replication server
			try{
				InetAddress ipAddress = InetAddress.getByName(structure.ipAdress);
				InetSocketAddress socketAddress = new InetSocketAddress(ipAddress, structure.port + 1);
				replicationServer = new SaslSocketServer(new SpecificResponder(protocols.replication.Replication.class, new ReplicationServer(backupControllerStructure, manager)), socketAddress);
			}catch(IOException e){
				System.err.println("[Error] Failed to start replication server");
			}
			
			replicationServer.start();
			
			try{
				replicationServer.join();
			}catch(InterruptedException e){}
			
		}
		
	}
	
	// Checks if the controller has come back online
	class PingOriginalControllerTask extends TimerTask{
		ReplicatedManager manager = null;
		Boolean couldConnect = false;
		
		public PingOriginalControllerTask(ReplicatedManager m) {
			this.manager = m;
		}
		
		public void run() {
			if(couldConnect == true){
				// We're done
				return;
			}
			
			// Try to start a server
			try {
				InetAddress ipAddress = InetAddress.getByName(this.manager.backupControllerStructure.originalEntity.ipAdress);
				InetSocketAddress socketAddress = new InetSocketAddress(ipAddress, this.manager.backupControllerStructure.originalEntity.port);
				Transceiver client = new SaslSocketTransceiver(socketAddress);
				this.manager.originalController = (protocols.controller.Controller) SpecificRequestor.getClient(protocols.controller.Controller.class, client);
			} catch (IOException e) {
				// Couldn't connect, which is normal because at this moment backup controller is offline
			}
			
			try {
				if(this.manager.originalController != null){
					System.out.println("[INFO] Connection original controller back");
					this.manager.originalController.ping();
					this.couldConnect = true;
					
					// Connection possible
					this.manager.stopBackupController();
				}
			} catch (AvroRemoteException e) {
				// No connection
				
			}
		}
	}
	
	
	@Command()
	public void StartElection(){
		this.electionProcessor.start();
	}

	public ReplicatedManager(Device fstructure, Server server) {
		super(fstructure, server);
		
		// Create a new controller server for backup purpose, dont start it yet
		// Use the same device info as this device, because the original device server will be stopped
		DeviceFactory df = new DeviceFactory(this.structure.id, this.structure.controllerIpAdress, this.structure.controllerPort, this.structure.ipAdress, this.structure.port);
		backupControllerStructure = df.createController(0);
		
		// Start a replication server
		Thread t =  new ReplicationServerTask(this);
		t.start();
		
		// Register this device to the controller
		this.registerToController();
		
		// Create an election processor for when things go wrong
		this.electionProcessor = new ElectionProcessor(this);
	}
	
	@Command(description="Get information about the replication of data")
	public void replicationInfo(){
		this.backupControllerStructure.replicationInfo();
	}
	
	// Give permission to this backup controller to start
	@Command()
	public void startBackupController(){
		// Stop current device server
		this.shutdown();
		
		// Create the new backup controller server
		ManagerFactory mf = new ManagerFactory(null); // we dont use this feature
		this.backupControllerManager = mf.createControllerManager(0, this.backupControllerStructure);
		Thread t = new Thread(this.backupControllerManager);
		t.start();
		
		// Reregister devices
        this.reRegisterDevices(backupControllerStructure, false);
        
        this.isBackup = true;
        
        // start pinging if original controller came back online 
        PingOriginalControllerTask pingOriginalControllerTask = new PingOriginalControllerTask(this);
		
		timer = new Timer(true);
		timer.scheduleAtFixedRate(pingOriginalControllerTask, 0, 5*1000);
        
		// Message we're done
        System.out.println("[INFO] Started a backup controller");
	}
	
	// Give permission stop this backup controller and give it back to the original controller
	public void stopBackupController(){
		// Stop the original controller pinging task
		this.timer.cancel();
		
		this.isBackup = false;
		
		// Stop the backup server
		this.backupControllerManager.shutdown();
		
		// Reregister Devices
		this.reRegisterDevices(this.backupControllerStructure.originalEntity, false);
		
		// Give information from the backup controller back to the original controller
		this.callRestartOriginalController();
		
		// Restart original server
		try{
			if(this.type.equals(Type.USER)){
				servers.UserServer deviceServer = new servers.UserServer(this.structure);
				this.server = this.createServer(deviceServer, protocols.user.User.class);
				this.originalController.register(this.structure.getProtocolDevice());
			}else if(this.type.equals(Type.FRIDGE)){
				servers.FridgeServer deviceServer = new servers.FridgeServer(this.structure);
				this.server = this.createServer(deviceServer, protocols.fridge.Fridge.class);
				this.originalController.register(this.structure.getProtocolDevice());
			}else{
				System.out.println("[ERROR] Can only restrart a fridge or user server");
			}
		}catch(AvroRemoteException e){
			System.out.println("Couldn't reconnect with original controller.");
		}
		
		// Reconnect the controller RPC to the controller
		this.controller = this.originalController;
		
				
		// Restart Cli
		this.rebootCLI();
		
	}
	
	// Regegister all devices summed up in the backup controller structure 
	// controllerEntity -> entity of controller to register to
	// allDevices -> register this entity of this device also
	private void reRegisterDevices(Entity controllerEntity, Boolean allDevices){
        // Reregister all devices
        for(Entry<Integer, Entity> entry : this.backupControllerStructure.fridges.entrySet()){
        	// Check if the original type of this device is a fridge, if it has the same id, don't try to reconnect
        	if(this.structure.type.toString().equals("FRIDGE") && this.structure.id == entry.getKey() && allDevices == false){
        		continue;
        	}
        	
        	this.callReRegister(entry.getValue(), controllerEntity);
        }
        
        for(Entry<Integer, Entity> entry : this.backupControllerStructure.lights.entrySet()){        	
        	this.callReRegister(entry.getValue(), controllerEntity);
        }
        
        for(Entry<Integer, Entity> entry : this.backupControllerStructure.sensors.entrySet()){
        	this.callReRegister(entry.getValue(), controllerEntity);
        }
        
        for(Entry<Integer, Entity> entry : this.backupControllerStructure.users.entrySet()){
        	// Check if the original type of this device is a user, if it has the same id, don't try to reconnect
        	if(this.structure.type.toString().equals("USER") && this.structure.id == entry.getKey() && allDevices == false){
        		continue;
        	}
        	
        	this.callReRegister(entry.getValue(), controllerEntity);
        }
	}
	
	
	// Call reregister on devices and connect the with an given controller
	private void callReRegister(Entity entity, Entity controllerEntity){
		try{
			InetAddress ipAddress = InetAddress.getByName(entity.ipAdress);
			InetSocketAddress socketAddress = new InetSocketAddress(ipAddress, entity.port);
			Transceiver client = new SaslSocketTransceiver(socketAddress);
			
			if(entity.type.toString().equals("FRIDGE")){
				Fridge proxy = (Fridge) SpecificRequestor.getClient(Fridge.class, client);
				proxy.reRegister(controllerEntity.ipAdress, controllerEntity.port);
			}else if(entity.type.toString().equals("LIGHT")){
				Light proxy = (Light) SpecificRequestor.getClient(Light.class, client);
				proxy.reRegister(controllerEntity.ipAdress, controllerEntity.port);
			}else if(entity.type.toString().equals("SENSOR")){
				Sensor proxy = (Sensor) SpecificRequestor.getClient(Sensor.class, client);
				proxy.reRegister(controllerEntity.ipAdress, controllerEntity.port);
			}else if(entity.type.toString().equals("USER")){
				User proxy = (User) SpecificRequestor.getClient(User.class, client);
				proxy.reRegister(controllerEntity.ipAdress, controllerEntity.port);
			}else{
				client.close();
				throw new Exception("[ERROR] Trying to reregister unkown type.");
			}
			
			client.close();
		}catch(Exception e){
			System.err.println("[Error] Connecting to client");
			e.printStackTrace();
		}
	}
	

	
	private void callRestartOriginalController(){
		List<protocols.controller.FridgeStatus> fridgeStatusses = new LinkedList<protocols.controller.FridgeStatus>();
		for(Entry<Integer, structures.FridgeStatus> entry : this.backupControllerStructure.openFridges.entrySet()){
			protocols.controller.FridgeStatus status = new protocols.controller.FridgeStatus();
			status.setId(entry.getKey());
			status.setOpened(entry.getValue().open);
			status.setUserid(entry.getValue().userid);
			
			fridgeStatusses.add(status);
		}
		
		List<protocols.controller.TemperatureHistory> temperatureHistories = new LinkedList<protocols.controller.TemperatureHistory>();
		for(Entry<Integer, structures.TemperatureHistory> entry : this.backupControllerStructure.temperatures.entrySet()){
			protocols.controller.TemperatureHistory status = new protocols.controller.TemperatureHistory();
			status.setId(entry.getKey());
			status.setTemperatures(backupControllerStructure.getLastTemperatures(entry.getKey()));
			
			temperatureHistories.add(status);
		}
		
		try {
			this.originalController.restart(this.backupControllerStructure.lightStatus,
					fridgeStatusses, temperatureHistories,
					this.backupControllerStructure.amountOfMeasurements,
					this.backupControllerStructure.time);
		} catch (AvroRemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("[ERROR] Couldn't send initial controller info to original controller");
		}
	}
	
	// creates a new server
	private Server createServer(servers.Server deviceServer, Class proto){
		Server server = null;
		try{
			InetAddress ipAddress = InetAddress.getByName(deviceServer.device.ipAdress);
			InetSocketAddress socketAddress = new InetSocketAddress(ipAddress, deviceServer.device.port);
			server = new SaslSocketServer(new SpecificResponder(proto, deviceServer), socketAddress);
		}catch(IOException e){
			System.err.println("[Error] Failed to start server");
			e.printStackTrace(System.err);
			System.exit(1);
		}catch(Exception e){
			System.err.println("[Error] Failed to start server");
			e.printStackTrace(System.err);
		}
		
		return server;
	}

}
