package servers;

import java.util.List;

import org.apache.avro.AvroRemoteException;

import managers.ControllerManager;
import protocols.controller.Device;
import protocols.controller.DeviceStatus;
import protocols.controller.Failure;
import protocols.controller.FridgeStatus;
import protocols.controller.LightStatus;
import structures.Controller;
import structures.Entity;

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
		System.out.println("Registering " + device.getPort());
		
		try{
			// Create a proxy to the device in the ControllerManager
			this.manager.AddProxy(device);
			// Add information about the device to the controller structure
			this.manager.structure.registerDevice(device);
		}catch (Exception e) {
			// TODO: handle exception
			System.err.println("[Error] Error in registering device with controller, no manager set");
			e.printStackTrace(System.err);
			System.exit(1);
		}
		
		return false;
	}

	@Override
	public boolean updateTemperature(int id, double value) throws AvroRemoteException, Failure {
		this.manager.structure.updateTemperature(id, value);
		
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
		if(this.manager.structure.fridges.containsKey(fridgeId) == false){
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
		
		if(this.manager.structure.getFridgeStatus().open == true){
			Failure f = new Failure();
			f.setInfo("Fridge " + fridgeId + " already opened by user " + this.manager.structure.getFridgeStatus().userid);
			throw f;
		}else{
			this.manager.structure.openFridge(fridgeId, userId);
			
			// Send information for direct RPC between Fridge and User
			Entity fridgeEntity = this.manager.structure.fridges.get(fridgeId);
			return fridgeEntity.getProtocolDevice();
		}
	}

	@Override
	public Void closeFridge(int fridgeId, int userId) throws AvroRemoteException, Failure {
		if(this.manager.structure.fridges.containsKey(fridgeId) == false){
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
		
		if(this.manager.structure.getFridgeStatus().open == false){
			Failure f = new Failure();
			f.setInfo("Fridge " + fridgeId + " already closed");
			throw f;
		}else if(this.manager.structure.getFridgeStatus().userid != userId){
			Failure f = new Failure();
			f.setInfo("Fridge " + fridgeId + " opened by user " + this.manager.structure.getFridgeStatus().userid + ", not user " + userId);
			throw f;
		}else{
			this.manager.structure.closeFridge(fridgeId);
		}
		
		return null;
	}

	@Override
	public FridgeStatus fridgeStatus(int fridgeId) throws AvroRemoteException, Failure {
		FridgeStatus status = new FridgeStatus();
		
		structures.FridgeStatus s = this.manager.structure.getFridgeStatus();
		status.setId(s.id);
		status.setOpened(s.open);
		status.setUserid(s.userid);
		
		return status;
	}

	@Override
	public List<CharSequence> getFridgeItems(int fridgeId) throws AvroRemoteException, Failure {
		return this.manager.getFridgeItems(fridgeId);
	}

}
