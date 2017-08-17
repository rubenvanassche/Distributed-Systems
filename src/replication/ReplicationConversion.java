package replication;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import protocols.controller.LightStatus;
import structures.Entity;


public class ReplicationConversion {
	public protocols.replication.Entity convert(structures.Entity e){
		protocols.replication.Entity entity = new protocols.replication.Entity();
		
		entity.setId(e.id);
		entity.setIpadress(e.ipAdress);
		entity.setPort(e.port);
		entity.setType(e.type.toString());
		
		return entity;
	}
	
	public protocols.replication.TemperatureHistory convert(structures.TemperatureHistory th){
		protocols.replication.TemperatureHistory temperatureHistory = new protocols.replication.TemperatureHistory();
		
		temperatureHistory.setId(th.deviceID);
		LinkedList<Double> temperatures = (LinkedList<Double>) th.temperatures;
		temperatureHistory.setTemperatures(temperatures);
		
		return temperatureHistory;
	}
	
	public protocols.replication.LightStatus convert(protocols.controller.LightStatus ls){
		protocols.replication.LightStatus status = new protocols.replication.LightStatus();
		
		status.setId(ls.getId());
		status.setState(ls.getState());
		
		return status;
	}
	
	public protocols.replication.FridgeStatus convert(structures.FridgeStatus fs){
		protocols.replication.FridgeStatus status = new protocols.replication.FridgeStatus();
		
		status.setFridgeId(fs.id);
		status.setOpen(fs.open);
		status.setUserId(fs.userid);
		
		return status;
	}
	
	public protocols.replication.Controller convert(structures.Controller c){
		protocols.replication.Controller controller = new protocols.replication.Controller();
		
		// Amount Of Measurements
		controller.setAmountOfMeasurements(c.amountOfMeasurements);
		
		// Entities
		List<protocols.replication.Entity> entities = new LinkedList<protocols.replication.Entity>();
		for(Entry<Integer, structures.Entity> entry : c.fridges.entrySet()){
			entities.add(this.convert(entry.getValue()));
		}
		for(Entry<Integer, structures.Entity> entry : c.lights.entrySet()){
			entities.add(this.convert(entry.getValue()));
		}
		for(Entry<Integer, structures.Entity> entry : c.sensors.entrySet()){
			entities.add(this.convert(entry.getValue()));
		}
		for(Entry<Integer, structures.Entity> entry : c.users.entrySet()){
			entities.add(this.convert(entry.getValue()));
		}
		controller.setEntities(entities);
		
		// Temperature Histories
		List<protocols.replication.TemperatureHistory> temperatureHistories = new LinkedList<protocols.replication.TemperatureHistory>();
		for(Entry<Integer, structures.TemperatureHistory> entry : c.temperatures.entrySet()){
			temperatureHistories.add(this.convert(entry.getValue()));
		}
		controller.setTemperatureHistories(temperatureHistories);
		
		// Light Statusses
		List<protocols.replication.LightStatus> lightStatusses = new LinkedList<protocols.replication.LightStatus>();
		for(LightStatus entry : c.lightStatus){
			lightStatusses.add(this.convert(entry));
		}
		controller.setLightStatusses(lightStatusses);
		
		// Open Fridges
		List<protocols.replication.FridgeStatus> openFridges = new LinkedList<protocols.replication.FridgeStatus>();
		for(Entry<Integer, structures.FridgeStatus> entry : c.openFridges.entrySet()){
			openFridges.add(this.convert(entry.getValue()));
		}
		controller.setOpenFridges(openFridges);
		
		// Controller entity
		structures.Entity e = (Entity) c;
		protocols.replication.Entity entity = this.convert(e);
		controller.setControllerEntity(entity);
		
		return controller;
	}
}
