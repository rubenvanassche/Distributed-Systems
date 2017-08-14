package managers;


import java.util.List;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.Server;

import asg.cliche.Command;
import asg.cliche.Param;
import protocols.controller.Controller;
import protocols.controller.DeviceStatus;
import protocols.controller.LightStatus;
import protocols.light.Light;
import structures.Device;
import structures.User;

public class UserManager extends ControlledManager {
	public User user;

	public UserManager(Device fstructure, Server server) {
		super(fstructure, server);
		this.type = type.USER;
		this.user = (User) fstructure;
	}
	
    @Command(description="Leave the house")
    public void leave() {
    	if(this.user.inHouse == false){
    		System.out.println("[ERROR] User already left the house");
    		return;
    	}
    	
        this.user.inHouse = false;
        
        try {
			this.controller.userLeave(this.user.id);
		} catch (AvroRemoteException e) {
			// TODO controlelr down, take over
			e.printStackTrace();
		}
    }
    
    @Command(description="Enter the house")
    public void enter() {
    	if(this.user.inHouse == true){
    		System.out.println("[ERROR] User already in the house");
    		return;
    	}
    	
        this.user.inHouse = true;
        
        try {
			this.controller.userEnters(this.user.id);
		} catch (AvroRemoteException e) {
			// TODO controlelr down, take over
			e.printStackTrace();
		}
    }
    
    @Command(description="Get the temperature in the house")
    public void getTemperature() {
    	if(this.user.inHouse == false){
    		System.out.println("[ERROR] User not in house");
    		return;
    	}

    	try {
			System.out.print(this.controller.getTemperature());
		} catch (AvroRemoteException e) {
			// TODO controlelr down, take over
			e.printStackTrace();
		}
    }
    
    @Command(description="Get the temperature history in the house")
    public void getTemperatureHistory() {
    	if(this.user.inHouse == false){
    		System.out.println("[ERROR] User not in house");
    		return;
    	}

    	try {
			List<Double> temperatures = this.controller.getTemperatureHistory();
			for(int i = 0;i < temperatures.size();i++){
				System.out.println(temperatures.get(i));
			}
		} catch (AvroRemoteException e) {
			// TODO controlelr down, take over
			e.printStackTrace();
		}
    }
    
	@Command(description="Turn a light on")
    public void turnLightOn(
    		@Param(name="id", description="The identifier of the light")
    		Integer id
    		) {
		if(this.user.inHouse == false){
    		System.out.println("[ERROR] User not in house");
    		return;
    	}
		
		this.setLight(id, true);
    }
	
	@Command(description="Turn a light off")
    public void turnLightOff(
    		@Param(name="id", description="The identifier of the light")
    		Integer id
    		) {
		if(this.user.inHouse == false){
    		System.out.println("[ERROR] User not in house");
    		return;
    	}
		
		this.setLight(id, false);
    }
	
	public void setLight(int id, Boolean active){
		try {
			if(active == true){
				this.controller.turnLightOn(id);
			}else{
				this.controller.turnLightOff(id);
			}
		} catch (AvroRemoteException e) {
			// TODO controlelr down, take over
			e.printStackTrace();
		}
	}
	
	@Command(description="Get information about devices in the house")
    public void devices() {
		if(this.user.inHouse == false){
    		System.out.println("[ERROR] User not in house");
    		return;
    	}
		
		List<DeviceStatus> devices = null;
		try {
			devices = this.controller.getDevices();
		} catch (AvroRemoteException e) {
			// TODO controlelr down, take over
			e.printStackTrace();
		}
		
		for(int i = 0;i < devices.size();i++){
			DeviceStatus status = devices.get(i);
			if(status.getOnline() == true){
				System.out.println(status.getType().toString() + " " + status.getId() + " - Online");
			}else{
				System.out.println(status.getType().toString() + " " + status.getId() + " - Offline");
			}
			
		}
    }
	
	@Command(description="Get information about the lights")
    public void lights() {
		if(this.user.inHouse == false){
    		System.out.println("[ERROR] User not in house");
    		return;
    	}
		
		List<LightStatus> lights = null;
		try {
			lights = this.controller.getLights();
		} catch (AvroRemoteException e) {
			// TODO controlelr down, take over
			e.printStackTrace();
		}
		
		for(int i = 0;i < lights.size();i++){
			LightStatus status = lights.get(i);
			if(status.getState() == true){
				System.out.println("Light " + status.getId() + " - On");
			}else{
				System.out.println("Light " + status.getId() + " - Off");
			}
			
		}
    }


}
