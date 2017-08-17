package managers;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map.Entry;

import org.apache.avro.ipc.SaslSocketServer;
import org.apache.avro.ipc.SaslSocketTransceiver;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.avro.ipc.specific.SpecificResponder;

import asg.cliche.Command;
import avro.hello.proto.Hello;
import core.DeviceFactory;
import core.GUI;
import core.ManagerFactory;
import protocols.fridge.Fridge;
import protocols.light.Light;
import protocols.sensor.Sensor;
import protocols.user.User;
import replication.ReplicationServer;
import structures.Controller;
import structures.Device;
import structures.Entity;

public class ReplicatedManager extends ControlledManager {
	Controller backupController;
	ControllerManager backupControllerManager = null;
	Boolean isBackup = false;
	Server replicationServer = null;
	
	class ReplicationServerTask implements Runnable{

		@Override
		public void run() {
			// Build a replication server
			try{
				InetAddress ipAddress = InetAddress.getByName(structure.ipAdress);
				InetSocketAddress socketAddress = new InetSocketAddress(ipAddress, structure.port + 1);
				replicationServer = new SaslSocketServer(new SpecificResponder(protocols.replication.Replication.class, new ReplicationServer(backupController)), socketAddress);
			}catch(IOException e){
				System.err.println("[Error] Failed to start replication server");
			}
			
			replicationServer.start();
			
			try{
				replicationServer.join();
			}catch(InterruptedException e){}
			
		}
		
	}

	public ReplicatedManager(Device fstructure, Server server) {
		super(fstructure, server);
		
		// Create a new controller server for backup purpose
		// Use the same device info as this device, because the original device server will be stopped
		DeviceFactory df = new DeviceFactory(this.structure.id, this.structure.controllerIpAdress, this.structure.controllerPort, this.structure.ipAdress, this.structure.port);
		backupController = df.createController(0);
		
		// Start a replication server
		Thread t =  new Thread(new ReplicationServerTask());
		t.start();
		
		this.registerToController();
	}
	
	@Command(description="Get information about the replication of data")
	public void replicationInfo(){
		this.backupController.replicationInfo();
	}
	
	// Give permission to this backup controller to start
	@Command()
	public void startBackupController(){
		// Stop current device server
		this.shutdown();
		
		// Create the new backup controller server
		ManagerFactory mf = new ManagerFactory(null); // we dont use this feature
		this.backupControllerManager = mf.createControllerManager(0, this.backupController);
		Thread t = new Thread(this.backupControllerManager);
		t.start();
		
		// Stop the replication sever
		this.replicationServer.close();
		
        
        // Reregister all devices
        for(Entry<Integer, Entity> entry : this.backupController.fridges.entrySet()){
        	// Check if the original type of this device is a fridge, if it has the same id, don't try to reconnect
        	if(this.structure.type.toString().equals("FRIDGE") && this.structure.id == entry.getKey()){
        		continue;
        	}
        	
        	this.callReRegister(entry.getValue());
        }
        
        for(Entry<Integer, Entity> entry : this.backupController.lights.entrySet()){        	
        	this.callReRegister(entry.getValue());
        }
        
        for(Entry<Integer, Entity> entry : this.backupController.sensors.entrySet()){
        	this.callReRegister(entry.getValue());
        }
        
        for(Entry<Integer, Entity> entry : this.backupController.users.entrySet()){
        	// Check if the original type of this device is a user, if it has the same id, don't try to reconnect
        	if(this.structure.type.toString().equals("USER") && this.structure.id == entry.getKey()){
        		continue;
        	}
        	
        	this.callReRegister(entry.getValue());
        }
        
	}
	
	// Calls the reregister method on controlled Devices
	public void callReRegister(Entity entity){
		try{
			InetAddress ipAddress = InetAddress.getByName(entity.ipAdress);
			InetSocketAddress socketAddress = new InetSocketAddress(ipAddress, entity.port);
			Transceiver client = new SaslSocketTransceiver(socketAddress);
			
			if(entity.type.toString().equals("FRIDGE")){
				Fridge proxy = (Fridge) SpecificRequestor.getClient(Fridge.class, client);
				proxy.reRegister(backupController.ipAdress, backupController.port);
			}else if(entity.type.toString().equals("LIGHT")){
				Light proxy = (Light) SpecificRequestor.getClient(Light.class, client);
				proxy.reRegister(backupController.ipAdress, backupController.port);
			}else if(entity.type.toString().equals("SENSOR")){
				Sensor proxy = (Sensor) SpecificRequestor.getClient(Sensor.class, client);
				proxy.reRegister(backupController.ipAdress, backupController.port);
			}else if(entity.type.toString().equals("USER")){
				User proxy = (User) SpecificRequestor.getClient(User.class, client);
				proxy.reRegister(backupController.ipAdress, backupController.port);
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

}
