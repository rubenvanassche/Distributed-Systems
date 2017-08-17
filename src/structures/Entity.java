package structures;

import structures.Entity.Type;

public class Entity {
	public enum Type{
		CONTROLLER, USER, SENSOR, LIGHT, FRIDGE
	}
	
	// Information about this device
	public String ipAdress;
	public int port;
	
	public int id;
	public Type type;
	
	// Get an device which can be send over the avro protocol
	public protocols.controller.Device getProtocolDevice(){
		protocols.controller.Device device = new protocols.controller.Device();
		
		device.setId(this.id);
		device.setIpadress(this.ipAdress);
		device.setPort(this.port);
		device.setType(this.type.toString());
		
		return device;
	}
	
	// Set information about the device via avro protocol
	public void getInfoFromProtocolDevice(protocols.controller.Device device){
		this.id = device.getId();
		this.ipAdress = device.getIpadress().toString();
		this.port = device.getPort();
		this.type = Type.valueOf(device.getType().toString());
	}
	
	// Set information about the device via avro protocol
	public void getInfoFromProtocolEntity(protocols.replication.Entity entity){
		this.id = entity.getId();
		this.ipAdress = entity.getIpadress().toString();
		this.port = entity.getPort();
		this.type = Type.valueOf(entity.getType().toString());
	}
}
