package managers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.SaslSocketTransceiver;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;

import asg.cliche.Command;
import asg.cliche.Param;
import protocols.controller.Controller;
import protocols.controller.Device;
import protocols.controller.DeviceStatus;
import protocols.controller.LightStatus;
import protocols.fridge.Fridge;
import protocols.light.Light;
import protocols.sensor.Sensor;
import protocols.user.User;

import structures.Entity.Type;


public class ControllerManager extends Manager {
	public structures.Controller structure; // Structure
	Map<Integer, Fridge> fridgeProxies = new HashMap<Integer, Fridge>(); // RPC connection to fridges
	Map<Integer, Light> lightProxies = new HashMap<Integer, Light>(); // RPC connection to lights
	Map<Integer, Sensor> sensorProxies = new HashMap<Integer, Sensor>(); // RPC connection to sensors
	Map<Integer, User> userProxies = new HashMap<Integer, User>(); // RPC connection to users
	

	public ControllerManager(structures.Controller controller, Server server) {
		super(server);
		this.type = type.CONTROLLER;
		this.structure = controller;
	}
	
	public void AddProxy(Device device){
		// Create the proxy to connect with
		try{
			InetAddress ipAddress = InetAddress.getByName(device.getIpadress().toString());
			InetSocketAddress socketAddress = new InetSocketAddress(ipAddress, device.getPort());
			Transceiver client = new SaslSocketTransceiver(socketAddress);
			
			Type type = Type.valueOf(device.getType().toString());
			Integer id = device.getId();
			if(type.equals(Type.CONTROLLER)){
				System.err.println("[Error] Trying to create a proxy for the controller to an controller, there can only be one.");
				System.exit(1);
			}else if(type.equals(Type.FRIDGE)){
				Fridge fridge = (Fridge) SpecificRequestor.getClient(Fridge.class, client);
				this.fridgeProxies.put(id, fridge);
			}else if(type.equals(Type.LIGHT)){
				Light light = (Light) SpecificRequestor.getClient(Light.class, client);
				this.lightProxies.put(id, light);
			}else if(type.equals(Type.SENSOR)){
				Sensor sensor = (Sensor) SpecificRequestor.getClient(Sensor.class, client);
				this.sensorProxies.put(id, sensor);
			}else if(type.equals(Type.USER)){
				User user = (User) SpecificRequestor.getClient(User.class, client);
				this.userProxies.put(id, user);
			}else{
				System.err.println("[Error] Trying to create a proxy for the controller to an unknown type.");
				System.exit(1);
			}
		
		}catch(IOException e){
			System.err.println("[Error] Connecting to server");
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	@Command(description="Get information about the lights")
    public void lights() {
        for(Map.Entry<Integer, Light> entry : this.lightProxies.entrySet()){
        	Integer id = entry.getKey();
        	try {
				if(entry.getValue().getStatus() == true){
					System.out.println("Light ID: " + id + " - on");
				}else{
					System.out.println("Light ID: " + id + " - off");
				}
			} catch (AvroRemoteException e) {
				System.out.println("Light ID: " + id  + " - data unavailable");
				
				// Set device offline
				try {
					this.structure.setDeviceOffline(id, Type.LIGHT);
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
        }
    }
	
	@Command(description="Turn a light on")
    public void turnLightOn(
    		@Param(name="id", description="The identifier of the light")
    		Integer id
    		) {
		Light light = this.lightProxies.get(id);
		
		if(light == null){
			System.out.println("Light ID: " + id  + " does not exists");
			return;
		}
		
		try {
			light.powerOn();
			System.out.println("Light ID: " + id  + " on");
		} catch (AvroRemoteException e) {
			System.out.println("Light ID: " + id  + " is offline");
			
			// Set device offline
			try {
				this.structure.setDeviceOffline(id, Type.LIGHT);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
    }
	
	@Command(description="Turn a light off")
    public void turnLightOff(
    		@Param(name="id", description="The identifier of the light")
    		Integer id
    		) {
		Light light = this.lightProxies.get(id);
		
		if(light == null){
			System.out.println("Light ID: " + id  + " does not exists");
			return;
		}
		
		try {
			light.powerOff();
			System.out.println("Light ID: " + id  + " off");
		} catch (AvroRemoteException e) {
			System.out.println("Light ID: " + id  + " is offline");
			
			// Set device offline
			try {
				this.structure.setDeviceOffline(id, Type.LIGHT);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
    }

	@Command(description="Get information about the temperature sensors")
    public void sensors() {
        for(Map.Entry<Integer, Sensor> entry : this.sensorProxies.entrySet()){
        	Integer id = entry.getKey();
        	try {
				System.out.println("Sensor ID: " + id + " - " + entry.getValue().getTemperature());
			} catch (AvroRemoteException e) {
				System.out.println("Sensor ID: " + id + " - data unavailable");
				
				// Set device offline
				try {
					this.structure.setDeviceOffline(id, Type.SENSOR);
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
        }
    }

	@Command(description="Get the temperature of a sensor")
    public void sensor(
    		@Param(name="id", description="The identifier of the temperature sensor")
    		Integer id
    		) {
		Sensor sensor = this.sensorProxies.get(id);
		
		if(sensor == null){
			System.out.println("Sensor ID: " + id  + " does not exists");
			return;
		}
		
		try {
			System.out.println("Sensor ID: " + id  + " " + sensor.getTemperature());
		} catch (AvroRemoteException e) {
			System.out.println("Sensor ID: " + id  + " is offline, last measurement: " + this.structure.getLastTemperature(id));
			
			// Set device offline
			try {
				this.structure.setDeviceOffline(id, Type.SENSOR);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
    }
	
	@Command(description="Get the temperatures measured by a sensor during time")
    public void sensorHistory(
    		@Param(name="id", description="The identifier of the temperature sensor")
    		Integer id
    		) {
		System.out.println("Temperature history of sensor " + id + ":");
		for (Double temperature : this.structure.getLastTemperatures(id)) {
            System.out.println(temperature);
        }
    }
	
	public void userEnters(int id){
		this.structure.users.get(id).online = true;
		
		// Send information to the other users
		for(Entry<Integer, User> entry : this.userProxies.entrySet()){
			if(entry.getKey() == id){
				// User who just entered
				continue;
			}
			
			try {
				entry.getValue().message("[INFO] User " + id + " has entered the house.");
			} catch (AvroRemoteException e) {
				// Set device offline
				try {
					this.structure.setDeviceOnline(id, Type.USER);
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				
			}
		}
		
		// Check if lights need to be restored
		if(this.structure.isLightStatusSaved() == true){
			this.restoreLightStatus();
			this.structure.lightStatus.clear();
		}
	}
    
	public void userLeave(int id){
		this.structure.users.get(id).online = false;
		
		// Send information to the other users
		for(Entry<Integer, User> entry : this.userProxies.entrySet()){
			if(entry.getKey() == id){
				// User who left
				continue;
			}
			
			try {
				entry.getValue().message("[INFO] User " + id + " has left the house.");
			} catch (AvroRemoteException e) {
				// Set device offline
				try {
					this.structure.setDeviceOffline(id, Type.USER);
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				
			}
		}
		
		// check if we have to save the light status and dim the lights
		if(this.getAmountOfUsersHome() == 0){
			this.saveLightStatus();
			this.dimLights();
		}
	}
	
	public List<DeviceStatus> devicesStatus(){
		List<DeviceStatus> devices = new LinkedList<DeviceStatus>();
		
		// Check if fridges are online
		for(Entry<Integer, Fridge> entry : this.fridgeProxies.entrySet()){
			DeviceStatus status = new DeviceStatus(entry.getKey(), true, Type.FRIDGE.toString());
					
			try {
				entry.getValue().ping();
			} catch (AvroRemoteException e) {
				// device is not online
				status.setOnline(false);
			}
			
			// Set device status in the controller structure
			try {
				this.structure.setDeviceStatus(entry.getKey(), Type.FRIDGE, status.getOnline());
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			
			devices.add(status);
		}
		
		// Check if lights are online
		for(Entry<Integer, Light> entry : this.lightProxies.entrySet()){
			DeviceStatus status = new DeviceStatus(entry.getKey(), true, Type.LIGHT.toString());
					
			try {
				entry.getValue().ping();
			} catch (AvroRemoteException e) {
				// device is not online
				status.setOnline(false);
			}
			
			// Set device status in the controller structure
			try {
				this.structure.setDeviceStatus(entry.getKey(), Type.LIGHT, status.getOnline());
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			
			devices.add(status);
		}
		
		// Check if sensors are online
		for(Entry<Integer, Sensor> entry : this.sensorProxies.entrySet()){
			DeviceStatus status = new DeviceStatus(entry.getKey(), true, Type.SENSOR.toString());
					
			try {
				entry.getValue().ping();
			} catch (AvroRemoteException e) {
				// device is not online
				status.setOnline(false);
			}
			
			// Set device status in the controller structure
			try {
				this.structure.setDeviceStatus(entry.getKey(), Type.SENSOR, status.getOnline());
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			
			devices.add(status);
		}
		
		// Check if users are online
		for(Entry<Integer, User> entry : this.userProxies.entrySet()){
			DeviceStatus status = new DeviceStatus(entry.getKey(), true, Type.USER.toString());
			
			try {
				Boolean inHouse = entry.getValue().inHouse();
				status.setOnline(inHouse);
			} catch (AvroRemoteException e) {
				// device is not online
				status.setOnline(false);
			}
			
			// Set device status in the controller structure
			try {
				this.structure.setDeviceStatus(entry.getKey(), Type.USER, status.getOnline());
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			
			
			devices.add(status);
		}
		
		return devices;
	}
	
	@Command(description="Get all the devices")
	public void devices(){
		System.out.println("Lights");
		System.out.println("------");
		this.lights();
		System.out.println("Temperature Sensors");
		System.out.println("-------------------");
		this.sensors();
	}

	// Get the status of all the lights
	public List<LightStatus> lightStatus(){
		List<LightStatus> lights = new LinkedList<LightStatus>();
		
		// Check if lights are online
		for(Entry<Integer, Light> entry : this.lightProxies.entrySet()){
			LightStatus status = new LightStatus(entry.getKey(), false);
					
			try {
				status.setState(entry.getValue().getStatus());
			} catch (AvroRemoteException e) {
				// device is not online
				continue;
			}
			lights.add(status);
		}
		
		return lights;
	}
	
	// Save the configuration of the lights to the structure
	public void saveLightStatus(){
		this.structure.lightStatus = this.lightStatus();
	}
	
	// restore the configuration of the lights to to all the lights
	public void restoreLightStatus(){
		for(LightStatus status : this.structure.lightStatus){
			// Get the RPC connection to the light
			try{
				if(status.getState() == true){
					this.lightProxies.get(status.getId()).powerOn();
				}else{
					this.lightProxies.get(status.getId()).powerOff();
				}
			} catch (AvroRemoteException e){
				// device not online
				continue;
			}
			
		}
	}
	
	// Poweroff all the lights
	public void dimLights(){
		for(LightStatus status : this.structure.lightStatus){
			// Get the RPC connection to the light
			try{
				this.lightProxies.get(status.getId()).powerOff();
			} catch (AvroRemoteException e){
				// device not online
				continue;
			}
			
		}
	}
	
	// Returns the amount of users in the house
	public int getAmountOfUsersHome(){
		int count = 0;
		
		for(Entry<Integer, User> entry : this.userProxies.entrySet()){
			try {
				if(entry.getValue().inHouse() == true){
					count += 1;
				}
			} catch (AvroRemoteException e) {
				// user not at home
				continue;
			}
		}
		
		return count;
	}
}
