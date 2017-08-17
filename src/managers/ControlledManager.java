package managers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.SaslSocketTransceiver;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;

import avro.hello.proto.Hello;
import protocols.controller.Controller;
import protocols.controller.Failure;
import structures.Device;

public class ControlledManager extends Manager {
	public Controller controller;  // RPC connection to controller
	public Device structure;  // Structure
	
	
	public ControlledManager(Device fstructure, Server server) {
		super(server);
		this.structure = fstructure;
		
		// Create the proxy
		try{
			InetAddress ipAddress = InetAddress.getByName(structure.controllerIpAdress);
			InetSocketAddress socketAddress = new InetSocketAddress(ipAddress, structure.controllerPort);
			Transceiver client = new SaslSocketTransceiver(socketAddress);
			controller = (Controller) SpecificRequestor.getClient(Controller.class, client);
		}catch(IOException e){
			System.err.println("[Error] Connecting to server");
			e.printStackTrace(System.err);
			System.exit(1);
		}
		
	}
	
	public void registerToController(){
		// Try to register this device to the controller
		try{
			this.controller.register(structure.getProtocolDevice());
		}catch (Failure e){
			System.err.println("[Error] " + e.getInfo());
		}catch (Exception e) {
			// TODO: handle exception
			System.err.println("[Error] Error in registering device with controller server");
			e.printStackTrace(System.err);
			System.exit(1);
		}	
	}
	
	// Gets called when a new controller was selected and information needs to be updated
	public void reRegister(String ipadress, int port){
		// Stop the connection to the old proxy
		this.controller = null;
		
		
		// recreate the proxy
		try{
			InetAddress ipAddress = InetAddress.getByName(ipadress);
			InetSocketAddress socketAddress = new InetSocketAddress(ipAddress, port);
			Transceiver client = new SaslSocketTransceiver(socketAddress);
			controller = (Controller) SpecificRequestor.getClient(Controller.class, client);
		}catch(IOException e){
			System.err.println("[Error] Connecting to new controller server");
			e.printStackTrace(System.err);
			System.exit(1);
		}
		
		System.out.println("Reregistered with " + ipadress + ":" + port);
		
		// Now register with the new controller
		this.registerToController();
	}
	
}
