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
		try{
			this.manager.setLightStatus(id, true);
		}catch(Failure e){
			return false;
		}
		return true;
	}

	@Override
	public boolean turnLightOff(int id) throws AvroRemoteException, Failure {
		try{
			this.manager.setLightStatus(id, false);
		}catch(Failure e){
			return false;
		}
		return true;
	}

	@Override
	public List<LightStatus> getLights() throws AvroRemoteException, Failure {
		return this.manager.lightStatus();
	}

	@Override
	public Void fridgeIsEmpty(int id) throws AvroRemoteException {
		this.manager.sendMessage("Fridge " + id + " is empty!");
		return null;
	}

	@Override
	public Device openFridge(int fridgeId, int userId) {
		if(this.manager.structure.fridges.containsKey(fridgeId) == false){
			throw new Exception("Fridge " + fridgeId + " does not exists");
		}
		
		if(this.manager.structure.getFridgeStatus().open == true){
			throw new Failure("Fridge " + fridgeId + " already opened by user " + this.manager.structure.getFridgeStatus().userid);
		}else{
			this.manager.structure.openFridge(fridgeId, userId);
			
			// Send information for direct RPC between Fridge and User
			Entity fridgeEntity = this.manager.structure.fridges.get(fridgeId);
			return fridgeEntity.getProtocolDevice();
		}
	}

	@Override
	public Void closeFridge(int fridgeId) throws AvroRemoteException {
		if(this.manager.structure.fridges.containsKey(fridgeId) == false){
			throw new Failure("Fridge " + fridgeId + " does not exists");
		}
		
		if(this.manager.structure.getFridgeStatus().open == false){
			throw new Failure("Fridge " + fridgeId + " already closed");
		}else{
			this.manager.structure.closeFridge(fridgeId);
		}
		
		return null;
	}

	@Override
	public FridgeStatus fridgeStatus(int fridgeId) throws AvroRemoteException {
		FridgeStatus status = new FridgeStatus();
		
		structures.FridgeStatus s = this.manager.structure.getFridgeStatus();
		status.setId(s.id);
		status.setOpened(s.open);
		status.setUserid(s.userid);
		
		return status;
	}

}
