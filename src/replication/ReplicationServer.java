package replication;

import java.util.List;

import org.apache.avro.AvroRemoteException;

import managers.ReplicatedManager;
import protocols.replication.Controller;
import protocols.replication.Entity;
import protocols.replication.Failure;
import protocols.replication.FridgeStatus;
import protocols.replication.LightStatus;
import protocols.replication.TemperatureHistory;

public class ReplicationServer implements protocols.replication.Replication {
	structures.Controller structure;
	ReplicatedManager manager = null;
	
	// generate a server with a backup controller structure
	public ReplicationServer(structures.Controller controller, ReplicatedManager m){
		this.structure = controller;
		this.manager = m;
	}

	@Override
	public Void handshake(Controller controller) throws AvroRemoteException, Failure {
		// Clear the controller structure, so that info about previous devices is gone!
		this.structure.clear();
		
		// Set amount of measurements
		this.structure.amountOfMeasurements = controller.getAmountOfMeasurements();
		
		// Set entities
		for(Entity entity : controller.getEntities()){
			this.registerEntity(entity);
		}
		
		// Set the temperature history
		for(TemperatureHistory temperatureHistory : controller.getTemperatureHistories()){
			this.addTemperatureHistory(temperatureHistory);
		}
		
		// Update the light statusses
		this.updateLightStatus(controller.getLightStatusses());
		
		// Update the fridge statusses
		for(FridgeStatus fridgeStatus : controller.getOpenFridges()){
			this.updateFridge(fridgeStatus);
		}
		
		// Set the original entity, for referencing back to the original controller
		this.structure.originalEntity = new structures.Entity();
		this.structure.originalEntity.getInfoFromProtocolEntity(controller.getOriginalEntity());
		
		return null;
	}

	@Override
	public Void registerEntity(Entity entity) throws AvroRemoteException, Failure {
		structures.Entity sEntity = new structures.Entity();
		sEntity.getInfoFromProtocolEntity(entity);
		
		
		if(sEntity.type.toString().equals("FRIDGE")){
			this.structure.fridges.put(entity.getId(), sEntity);
		}else if(sEntity.type.toString().equals("LIGHT")){
			this.structure.lights.put(entity.getId(), sEntity);
		}else if(sEntity.type.toString().equals("SENSOR")){
			this.structure.sensors.put(entity.getId(), sEntity);
		}else if(sEntity.type.toString().equals("USER")){
			this.structure.users.put(entity.getId(), sEntity);
		}else{
			Failure f = new Failure();
			f.setInfo("Entity type unkown");
			throw f;
		}
		
		return null;
	}

	@Override
	public Void addTemperatureHistory(TemperatureHistory temperatureHistory) throws AvroRemoteException, Failure {
		structures.TemperatureHistory th = new structures.TemperatureHistory();
		th.amountOfMeasurements = this.structure.amountOfMeasurements;
		th.deviceID = temperatureHistory.getId();
		for(Double temperature : temperatureHistory.getTemperatures()){
			th.temperatures.add(temperature);
		}
		
		this.structure.temperatures.put(temperatureHistory.getId(), th);
		
		return null;
	}

	@Override
	public Void updateLightStatus(List<LightStatus> lightStatusses) throws AvroRemoteException, Failure {
		this.structure.lightStatus.clear();
		
		for(LightStatus lightStatus : lightStatusses){
			protocols.controller.LightStatus ls = new protocols.controller.LightStatus();
			ls.setId(lightStatus.getId());
			ls.setState(lightStatus.getState());
			
			this.structure.lightStatus.add(ls);
		}
		
		return null;
	}

	@Override
	public Void updateFridge(FridgeStatus data) throws AvroRemoteException, Failure {
		structures.FridgeStatus fs = new structures.FridgeStatus(data.getFridgeId());
		fs.userid = data.getUserId();
		fs.open = data.getOpen();
		
		this.structure.openFridges.put(data.getFridgeId(), fs);
		
		return null;
	}

	@Override
	public Void election(int entityId, int score) throws AvroRemoteException {
		this.manager.electionProcessor.processElection(entityId, score);
		return null;
	}

	@Override
	public Void elected(int entityId) throws AvroRemoteException {
		this.manager.electionProcessor.processElected(entityId);
		return null;
	}



}
