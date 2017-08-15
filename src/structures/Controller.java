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
		}else if(type.equals(Type.LIGHT)){
			this.lights.put(device.getId(), entity);
		}else if(type.equals(Type.SENSOR)){
			this.sensors.put(device.getId(), entity);			
		}else if(type.equals(Type.USER)){
			this.users.put(device.getId(), entity);
		}else{
			throw new Failure("[ERROR] trying to register a device with an unkown type");
		}
	}
	
	// Update the temperature in the controller 
	public void updateTemperature(int id, double temperature) throws Failure{
		if(sensors.containsKey(id) == false){
			throw new Failure("[ERROR] trying to update a sensor which ID doens't exists");
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
		for(int i = 0;i < 5;i++){
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
		for(int i = 0;i < 5;i++){
			output.set(i, output.get(i) / samples);
		}
		
		return output;
	}
	
	// Set the boolean of the onliness device offline
	public void setDeviceOffline(int id, Type type) throws Exception{
		this.setDeviceStatus(id, type, false);
	}
	
	// Set the boolean of the onliness device online
	public void setDeviceOnline(int id, Type type) throws Exception{
		this.setDeviceStatus(id, type, true);
	}
	
	public void setDeviceStatus(int id, Type type, Boolean status) throws Exception{
		if(type.equals(Type.FRIDGE)){
			this.fridges.get(id).online = status;
		}else if(type.equals(Type.LIGHT)){
			this.lights.get(id).online = status;
		}else if(type.equals(Type.SENSOR)){
			this.sensors.get(id).online = status;
		}else if(type.equals(Type.USER)){
			this.users.get(id).online = status;
		}else{
			throw new Exception("[ERROR]Unknown device type!");
		}
	}

	// Checks whether the house is empty and light statusses are saved
	public Boolean isLightStatusSaved(){
		if(this.lightStatus.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
}
