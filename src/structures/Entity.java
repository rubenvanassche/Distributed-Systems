package structures;

public class Entity {
	public enum Type{
		CONTROLLER, USER, SENSOR, LIGHT, FRIDGE
	}
	
	// Information about this device
	public String ipAdress;
	public int port;
	
	public int id;
	public Type type;
	
	public Boolean online;
}
