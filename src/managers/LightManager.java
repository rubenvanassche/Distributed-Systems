package managers;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.Server;

import asg.cliche.Command;
import protocols.controller.Controller;
import protocols.controller.Failure;
import structures.Device;
import structures.Light;

public class LightManager extends ControlledManager {
	public Light light;
	
	public LightManager(Device fstructure, Server server) {
		super(fstructure, server);
		this.type = type.LIGHT;
		this.light = (Light) fstructure;
		// TODO Auto-generated constructor stub
		
		this.registerToController(); // Register to controller, only needed for light and sensor because user and fridge get registered in the replicationmanager
	}
	
    @Command(description="Get the status of the light: on/off")
    public void status() {
        if(this.light.activated == true){
        	System.out.println("On");
        }else{
        	System.out.println("Off");
        }
    }
    
    @Command(description="Turn the light on")
    public void turnOn() {
        this.light.activated = true;
    }
    
    @Command(description="Turn the light off")
    public void turnOff() {
        this.light.activated = false;
    }

}
