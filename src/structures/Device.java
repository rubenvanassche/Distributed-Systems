package structures;

/// Class representing an device with its properties to connect to others
public class Device extends Entity {
	// Information about the controller
	public String controllerIpAdress;
	public int controllerPort;

	// Get an device which can be send over the avro protocol
	public protocols.controller.Device getProtocolDevice(){
		protocols.controller.Device device = new protocols.controller.Device();
		
		device.setId(this.id);
		device.setIpadress(this.ipAdress);
		device.setPort(this.port);
		device.setType(this.type.toString());
		
		return device;
	}
}
