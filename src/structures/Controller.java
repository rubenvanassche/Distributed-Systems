package structures;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import protocols.controller.Failure;
import protocols.controller.LightStatus;

// Class representing the data of a controller
public class Controller extends Device{	
	// List of all the devices (only ip info required)
	public Map<Integer, Entity> users = new HashMap<Integer, Entity>();
	public Map<Integer, Entity> fridges = new HashMap<Integer, Entity>();
	public Map<Integer, Entity> sensors = new HashMap<Integer, Entity>();
	public Map<Integer, Entity> lights = new HashMap<Integer, Entity>();
	
	// List of temperatures
	public HashMap<Integer, TemperatureHistory> temperatures = new HashMap<Integer, TemperatureHistory>();
	
	// List of the status of all the lights
	public List<LightStatus> lightStatus = new LinkedList<LightStatus>();

	// List of fridges and if they are opened
	public Map<Integer, FridgeStatus> openFridges = new HashMap<Integer, FridgeStatus>();
	
	// Amount of measurements to keep
	public int amountOfMeasurements;
	
	public Controller(int fAmountOfMeasurements){
		amountOfMeasurements = fAmountOfMeasurements;
	}
	
	// Register information about the device(id, ip, port and type)
	public void registerDevice(protocols.controller.Device device) throws Failure{
		Type type = Type.valueOf(device.getType().toString());
		Entity entity = new Entity();
		entity.getInfoFromProtocolDevice(device);
		
		if(type.equals(Type.FRIDGE)){
			this.fridges.put(device.getId(), entity);
			this.openFridges.put(device.getId(), new FridgeStatus(device.getId()));
		}else if(type.equals(Type.LIGHT)){
			this.lights.put(device.getId(), entity);
		}else if(type.equals(Type.SENSOR)){
			this.sensors.put(device.getId(), entity);			
		}else if(type.equals(Type.USER)){
			this.users.put(device.getId(), entity);
		}else{
			Failure f = new Failure();
			f.setInfo("[ERROR] trying to register a device with an unkown type");
			throw f;
		}
	}
	
	// Update the temperature in the controller 
	public void updateTemperature(int id, double temperature) throws Failure{
		if(sensors.containsKey(id) == false){
			Failure f = new Failure();
			f.setInfo("[ERROR] trying to update a sensor which ID doens't exists");
			throw f;
		}
		
		// add history
		if(temperatures.containsKey(id) == false){
			TemperatureHistory temperatureHistory = new TemperatureHistory();
			temperatureHistory.deviceID = id;
			temperatureHistory.amountOfMeasurements = amountOfMeasurements;
			
			temperatures.put(id, temperatureHistory);
		}
		
		// Add the previous temperature to history
		temperatures.get(id).addTemperature(temperature);
	}
	
	// Get the last temperature of a specific sensor
	public Double getLastTemperature(int id){
		try{
			if(temperatures.containsKey(id) == false){
				throw new Exception("[ERROR] Tried to get the  history of temperature of an sensor which is not registered");
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		LinkedList<Double> temperatures = (LinkedList<Double>) this.temperatures.get(id).temperatures;
		return temperatures.getLast();
	}
	
	// Get the last temperature from all sensors
	public Double getLastTemperature(){
		Double output = 0.0;
		int samples = 0;
		
		for(Entry<Integer, TemperatureHistory> entry : this.temperatures.entrySet()){
			try{
				output += this.getLastTemperature(entry.getKey());
				samples += 1;
			}catch(Exception e){	}
		}
		
		return output/samples;
	}
	
	// Get the last temperatures of a specific sensor
	public LinkedList<Double> getLastTemperatures(int id){	
		try{
			if(temperatures.containsKey(id) == false){
				throw new Exception("[ERROR] Tried to get the  history of temperature of an sensor which is not registered");
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		LinkedList<Double> temperatures = (LinkedList<Double>) this.temperatures.get(id).temperatures;
		return temperatures;
	}
	
	// Get the last temperatures from all sensors
	public LinkedList<Double> getLastTemperatures(){
		// generate the output list
		LinkedList<Double> output = new LinkedList<Double>();
		for(int i = 0;i < this.amountOfMeasurements;i++){
			output.add(0.0);
		}
		
		int samples = 0;
		
		for(Entry<Integer, TemperatureHistory> entry : this.temperatures.entrySet()){
			try{
				LinkedList<Double> history = this.getLastTemperatures(entry.getKey());
				for(int i = 0;i < history.size();i++){
					output.set(i, output.get(i) + history.get(i));
				}
				samples += 1;
			}catch(Exception e){	}
		}
		
		// Devide the output by the amount of samples
		for(int i = 0;i < this.amountOfMeasurements;i++){
			output.set(i, output.get(i) / samples);
		}
		
		return output;
	}
	

	// Checks whether the house is empty and light statusses are saved
	public Boolean isLightStatusSaved(){
		if(this.lightStatus.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	// Open a fridge
	public Boolean openFridge(int fridgeId, int userId) throws Failure{
		if(this.openFridges.get(id).open == true){
			Failure f = new Failure();
			f.setInfo("Fridge already opened");
			throw f;
		}
		
		this.openFridges.get(id).open = true;
		this.openFridges.get(id).userid = userId;
		return true;
	}
	
	// Close a fridge
	public Boolean closeFridge(int id) throws Failure{
		if(this.openFridges.get(id).open == false){
			Failure f = new Failure();
			f.setInfo("Fridge already closed");
			throw f;
		}
		
		this.openFridges.get(id).open = false;
		this.openFridges.get(id).userid = 0;
		return true;
	}
	
	// Get the fridge status
	public FridgeStatus getFridgeStatus(){
		return this.openFridges.get(id);
	}
	
	// Print information for replication purposes
	public void replicationInfo(){
		System.out.println("Entities");
		System.out.println("--------");
		for(Entry<Integer, Entity> entity : this.fridges.entrySet()){
			Entity e = entity.getValue();
			System.out.println("type: fridge, id:" + e.id + ", ip:" + e.ipAdress + ", port: " + e.port);
		}
		for(Entry<Integer, Entity> entity : this.lights.entrySet()){
			Entity e = entity.getValue();
			System.out.println("type: light, id:" + e.id + ", ip:" + e.ipAdress + ", port: " + e.port);
		}
		for(Entry<Integer, Entity> entity : this.sensors.entrySet()){
			Entity e = entity.getValue();
			System.out.println("type: sensor, id:" + e.id + ", ip:" + e.ipAdress + ", port: " + e.port);
		}
		for(Entry<Integer, Entity> entity : this.users.entrySet()){
			Entity e = entity.getValue();
			System.out.println("type: user, id:" + e.id + ", ip:" + e.ipAdress + ", port: " + e.port);
		}
		System.out.println("Temperature History");
		System.out.println("-------------------");
		for(Entry<Integer, TemperatureHistory> entry : this.temperatures.entrySet()){
			TemperatureHistory t = entry.getValue();
			LinkedList<Double> temperatures = this.getLastTemperatures(t.deviceID);
			String temperaturesString = "";
			
			for(Double temperature : temperatures){
				temperaturesString += String.valueOf(temperature) + ", ";
			}
			
			System.out.println("Sensorid: " + t.deviceID + ", temperatures: " + temperaturesString);
		}
		System.out.println("Light Status");
		System.out.println("------------");
		for(LightStatus status : this.lightStatus){
			if(status.getState() == true){
				System.out.println("LightId :" + status.getId() + ", status : on");
			}else{
				System.out.println("LightId :" + status.getId() + ", status : off");
			}
		}
		System.out.println("Open Fridges");
		System.out.println("------------");
		for(Entry<Integer, FridgeStatus> entry : this.openFridges.entrySet()){
			FridgeStatus status = entry.getValue();
			if(status.open == true){
				System.out.println("fridgeId: " + status.id + ", userId: " + status.userid + ", opened");
			}else{
				System.out.println("fridgeId: " + status.id + ", userId: " + status.userid + ", closed");
			}
		}
		System.out.println("Amount Of Measurements");
		System.out.println("----------------------");
		System.out.println(this.amountOfMeasurements);
	}
	
	// Clear the whole controller
	public void clear(){
		this.fridges.clear();
		this.lights.clear();
		this.sensors.clear();
		this.users.clear();
		
		this.temperatures.clear();
		
		this.lightStatus.clear();
		
		this.openFridges.clear();
		
		this.amountOfMeasurements = 0;
	}
	
}
