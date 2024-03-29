package servers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.avro.AvroRemoteException;

import managers.ControllerManager;
import protocols.controller.Device;
import protocols.controller.DeviceStatus;
import protocols.controller.Failure;
import protocols.controller.FridgeStatus;
import protocols.controller.LightStatus;
import structures.Controller;
import structures.Entity;
import structures.TemperatureHistory;

public class ControllerServer extends Server implements protocols.controller.Controller {
	public Controller controller;
	public ControllerManager manager = null;

	public ControllerServer(structures.Device d) {
		super(d);
		this.controller = (Controller) d;
	}

	@Override
	public boolean register(Device device) throws AvroRemoteException, Failure {
		// TODO Auto-generated method stub
		System.out.println("[info] Registering " + device.getType().toString() + " " + device.getId() + " at " + device.getIpadress() + ":" + device.getPort());
		
		try{
			// Create a proxy to the device in the ControllerManager
			this.manager.AddProxy(device);
			// Add information about the device to the controller structure
			this.manager.structure.registerDevice(device);
			
			// Replication purposes
			if(device.getType().toString().equals("FRIDGE")){
				this.manager.replication.updateFridge(this.controller.openFridges.get(device.getId()));
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.err.println("[Error] Error in registering device with controller, no manager set");
			System.exit(1);
		}
		
		return false;
	}

	@Override
	public boolean updateTemperature(int id, double value) throws AvroRemoteException, Failure {
		TemperatureHistory th = this.manager.structure.updateTemperature(id, value);
		
		// For replication purpose
		this.manager.replication.addTemperatureHistory(th);
		
		return true;
	}

	@Override
	public boolean userLeave(int id) throws AvroRemoteException, Failure {
		this.manager.userLeave(id);
		return true;
	}

	@Override
	public boolean userEnters(int id) throws AvroRemoteException, Failure {
		this.manager.userEnters(id);
		return true;
	}

	@Override
	public double getTemperature() throws AvroRemoteException, Failure {
		return this.manager.structure.getLastTemperature();
	}

	@Override
	public List<Double> getTemperatureHistory() throws AvroRemoteException, Failure {
		return this.manager.structure.getLastTemperatures();
	}

	@Override
	public List<DeviceStatus> getDevices() throws AvroRemoteException, Failure {
		return this.manager.devicesStatus();
	}

	@Override
	public boolean turnLightOn(int id) throws AvroRemoteException, Failure {
		this.manager.setLightStatus(id, true);
		return true;
	}

	@Override
	public boolean turnLightOff(int id) throws AvroRemoteException, Failure {
		this.manager.setLightStatus(id, false);
		return true;
	}

	@Override
	public List<LightStatus> getLights() throws AvroRemoteException, Failure {
		return this.manager.lightStatus();
	}

	@Override
	public Void fridgeIsEmpty(int id) throws AvroRemoteException, Failure {
		this.manager.sendMessage("Fridge " + id + " is empty!");
		return null;
	}

	@Override
	public Device openFridge(int fridgeId, int userId) throws AvroRemoteException, Failure {
		if(this.manager.structure.fridges.containsKey(fridgeId) == false || this.manager.fridgeProxies.containsKey(fridgeId) == false){
			Failure f = new Failure();
			f.setInfo("Fridge " + fridgeId + " does not exists");
			throw f;
		}
		
		// Check if fridge is still online
		try{
			this.manager.fridgeProxies.get(fridgeId).ping();
		}catch(AvroRemoteException e){
			Failure f = new Failure();
			f.setInfo("Fridge " + fridgeId + " is offline");
			throw f;
		}
		
		structures.FridgeStatus fs = this.manager.structure.getFridgeStatus(fridgeId);
		if(fs.open == true){
			Failure f = new Failure();
			f.setInfo("Fridge " + fridgeId + " already opened by user " + this.manager.structure.getFridgeStatus(fridgeId).userid);
			throw f;
		}else{
			this.manager.structure.openFridge(fridgeId, userId);
			
			// Replication purposes
			this.manager.replication.updateFridge(this.manager.structure.openFridges.get(fridgeId));
			
			// Send information for direct RPC between Fridge and User
			Entity fridgeEntity = this.manager.structure.fridges.get(fridgeId);
			return fridgeEntity.getProtocolDevice();
		}
	}

	@Override
	public Void closeFridge(int fridgeId, int userId) throws AvroRemoteException, Failure {
		if(this.manager.structure.fridges.containsKey(fridgeId) == false || this.manager.fridgeProxies.containsKey(fridgeId) == false){
			Failure f = new Failure();
			f.setInfo("Fridge " + fridgeId + " does not exists");
			throw f;
		}
		
		// Check if fridge is still online
		try{
			this.manager.fridgeProxies.get(fridgeId).ping();
		}catch(AvroRemoteException e){
			Failure f = new Failure();
			f.setInfo("Fridge " + fridgeId + " is offline");
			throw f;
		}
		
		if(this.manager.structure.getFridgeStatus(fridgeId).open == false){
			Failure f = new Failure();
			f.setInfo("Fridge " + fridgeId + " already closed");
			throw f;
		}else if(this.manager.structure.getFridgeStatus(fridgeId).userid != userId){
			Failure f = new Failure();
			f.setInfo("Fridge " + fridgeId + " opened by user " + this.manager.structure.getFridgeStatus(fridgeId).userid + ", not user " + userId);
			throw f;
		}else{
			this.manager.structure.closeFridge(fridgeId);
			
			// Replication purposes
			this.manager.replication.updateFridge(this.manager.structure.openFridges.get(fridgeId));
		}
		
		return null;
	}

	@Override
	public FridgeStatus fridgeStatus(int fridgeId) throws AvroRemoteException, Failure {
		FridgeStatus status = new FridgeStatus();
		
		structures.FridgeStatus s = this.manager.structure.getFridgeStatus(fridgeId);
		status.setId(s.id);
		status.setOpened(s.open);
		status.setUserid(s.userid);
		
		return status;
	}

	@Override
	public List<CharSequence> getFridgeItems(int fridgeId) throws AvroRemoteException, Failure {
		return this.manager.getFridgeItems(fridgeId);
	}

	@Override
	public boolean ping() throws AvroRemoteException {
		return true;
	}

	@Override
	public Void restart(List<LightStatus> lightStatusses, List<FridgeStatus> openFridges,
			List<protocols.controller.TemperatureHistory> temperatureHistories, int amountOfMeasurements, double time)
			throws AvroRemoteException, Failure {
		// Set the time
		this.controller.time = time;
		
		// Set light Statusses back
		this.controller.lightStatus = lightStatusses;
		
		// Set openfridges back
		for(FridgeStatus st : openFridges){
			structures.FridgeStatus status = new structures.FridgeStatus(st.getId());
			status.open = st.getOpened();
			status.userid = st.getUserid();
			
			this.controller.openFridges.put(st.getId(), status);
		}
		
		// Set temperaturehistories back
		for(protocols.controller.TemperatureHistory th : temperatureHistories){
			structures.TemperatureHistory history = new structures.TemperatureHistory();
			history.amountOfMeasurements = amountOfMeasurements;
			history.deviceID = th.getId();
			for(Double temperature : th.getTemperatures()){
				history.addTemperature(temperature);
			}
			
			this.controller.temperatures.put(th.getId(), history);
		}
		
		// set Amount of Measuremnts back
		this.controller.amountOfMeasurements = amountOfMeasurements;
		
		return null;
	}

	@Override
	public double getTime() throws AvroRemoteException {
		return this.controller.time;
	}

}
