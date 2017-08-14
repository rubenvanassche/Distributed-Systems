package core;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.avro.ipc.SaslSocketServer;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;

import managers.*;
import structures.*;

public class ManagerFactory {
	DeviceFactory devicefactory;
	
	public ManagerFactory(DeviceFactory factory){
		this.devicefactory = factory;
	}
	
	// Create a server for a certain device
	public static Server createServer(Device device, Class proto){
		Server server = null;
		try{
			server = new SaslSocketServer(new SpecificResponder(proto, device), new InetSocketAddress(6789));
		}catch(IOException e){
			System.err.println("[Error] Failed to start server");
			e.printStackTrace(System.err);
			System.exit(1);
		}
		
		return server;
	}
	
	public FridgeManager createFridgeManager(){
		Fridge device = devicefactory.createFridge();
		Server server = createServer(device, protocols.fridge.Fridge.class);
		
		FridgeManager manager = new FridgeManager(device, server);
		return manager;
	}
	
	public LightManager createLightManager(){
		Light device = devicefactory.createLight();
		Server server = createServer(device, protocols.light.Light.class);
		
		LightManager manager = new LightManager(device, server);
		return manager;
	}
	
	public SensorManager createSensorManager(Double startingTemperature, int updateFrequency){
		Sensor device = devicefactory.createSensor(startingTemperature, updateFrequency);
		Server server = createServer(device, protocols.sensor.Sensor.class);
		
		SensorManager manager = new SensorManager(device, server);
		return manager;
	}
	
	public UserManager createUserManager(){
		User device = devicefactory.createUser();
		Server server = createServer(device, protocols.user.User.class);
		
		UserManager manager = new UserManager(device, server);
		return manager;
	}
}
