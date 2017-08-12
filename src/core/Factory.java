package core;

import structures.*;
import structures.Entity.Type;

public class Factory {
	public int id;
	public String controllerIP; // Information about controller
	public int controllerPort;  // Information about controller
	public String ipAddress;  // Information about device self
	public int port;  // Information about device self
	
	public Factory(int fid, String fcontrollerIP, int fcontrollerPort, String fipAdress, int fport){
		id = fid;
		controllerIP = fcontrollerIP;
		controllerPort = fcontrollerPort;
		ipAddress = fipAdress;
		port = fport;
	}
	
	public Controller createController(int amountOfMeasurements){
		Controller d = new Controller(amountOfMeasurements);
		
		d.id = id;
		d.controllerIpAdress = controllerIP;
		d.controllerPort = controllerPort;
		d.ipAdress = ipAddress;
		d.port = port;
		d.type = Type.CONTROLLER;
		
		return d;
	}
	
	public Fridge createFridge(){
		Fridge d = new Fridge();
		
		d.id = id;
		d.controllerIpAdress = controllerIP;
		d.controllerPort = controllerPort;
		d.ipAdress = ipAddress;
		d.port = port;
		d.type = Type.FRIDGE;
		
		return d;		
	}
	
	public Light createLight(){
		Light d = new Light();
		
		d.id = id;
		d.controllerIpAdress = controllerIP;
		d.controllerPort = controllerPort;
		d.ipAdress = ipAddress;
		d.port = port;
		d.type = Type.LIGHT;
		
		return d;
	}
	
	public Sensor createSensor(Double startingTemperature, int updateFrequency){
		Sensor d = new Sensor(startingTemperature, updateFrequency);
		
		d.id = id;
		d.controllerIpAdress = controllerIP;
		d.controllerPort = controllerPort;
		d.ipAdress = ipAddress;
		d.port = port;
		d.type = Type.SENSOR;
		
		return d;
	}
	
	public User createUser(){
		User d = new User();
		
		d.id = id;
		d.controllerIpAdress = controllerIP;
		d.controllerPort = controllerPort;
		d.ipAdress = ipAddress;
		d.port = port;
		d.type = Type.USER;
		
		return d;
	}
}
