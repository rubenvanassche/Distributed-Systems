package managers;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.avro.ipc.SaslSocketServer;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;

import asg.cliche.Command;
import core.DeviceFactory;
import core.ManagerFactory;
import replication.ReplicationServer;
import structures.Controller;
import structures.Device;

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
		this.server.close();
		
		// Create the new backup controller server
		ManagerFactory mf = new ManagerFactory(null); // we dont use this feature
		this.backupControllerManager = mf.createControllerManager(0, this.backupController);
		this.backupControllerManager.run();
	}

}
