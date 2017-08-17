package core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.avro.ipc.SaslSocketServer;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;

import managers.*;
import servers.ControllerServer;
import structures.*;

public class ManagerFactory {
	DeviceFactory devicefactory;
	
	public ManagerFactory(DeviceFactory factory){
		this.devicefactory = factory;
	}
	
	// Create a server for a certain device
	private static Server createServer(servers.Server deviceServer, Class proto){
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
	
	public ControllerManager createControllerManager(int amountOfMeasurements, Controller device){
		servers.ControllerServer deviceServer = new servers.ControllerServer(device);
		Server server = createServer(deviceServer, protocols.controller.Controller.class);
		
		ControllerManager manager = new ControllerManager(device, server);
		deviceServer.manager = manager;
		return manager;
	}
	
	public ControllerManager createControllerManager(int amountOfMeasurements){
		Controller device = devicefactory.createController(amountOfMeasurements);
		return this.createControllerManager(amountOfMeasurements, device);
	}
	
	
	public FridgeManager createFridgeManager(){
		Fridge device = devicefactory.createFridge();
		servers.FridgeServer deviceServer = new servers.FridgeServer(device);
		Server server = createServer(deviceServer, protocols.fridge.Fridge.class);
		
		FridgeManager manager = new FridgeManager(device, server);
		deviceServer.manager = manager;
		return manager;
	}
	
	public LightManager createLightManager(){
		Light device = devicefactory.createLight();
		servers.LightServer deviceServer = new servers.LightServer(device);
		Server server = createServer(deviceServer, protocols.light.Light.class);
		
		LightManager manager = new LightManager(device, server);
		deviceServer.manager = manager;
		return manager;
	}
	
	public SensorManager createSensorManager(Double startingTemperature, int updateFrequency, Double driftValue){
		Sensor device = devicefactory.createSensor(startingTemperature, updateFrequency, driftValue);
		servers.SensorServer deviceServer = new servers.SensorServer(device);
		Server server = createServer(deviceServer, protocols.sensor.Sensor.class);
		
		SensorManager manager = new SensorManager(device, server);
		deviceServer.manager = manager;
		return manager;
	}
	
	public UserManager createUserManager(){
		User device = devicefactory.createUser();
		servers.UserServer deviceServer = new servers.UserServer(device);
		Server server = createServer(deviceServer, protocols.user.User.class);
		
		UserManager manager = new UserManager(device, server);
		deviceServer.manager = manager;
		return manager;
	}
}
