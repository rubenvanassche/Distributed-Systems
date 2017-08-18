package managers;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.SaslSocketTransceiver;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;

import asg.cliche.Command;
import asg.cliche.Param;
import protocols.controller.Controller;
import protocols.controller.DeviceStatus;
import protocols.controller.Failure;
import protocols.controller.LightStatus;
import protocols.fridge.Fridge;
import protocols.light.Light;
import structures.Device;
import structures.User;

public class UserManager extends ReplicatedManager {
	public User user;
	public Boolean usingFridge = false; // True when user has opened the fridge
	public Fridge fridge; // RPC connection to a fridge

	public UserManager(Device fstructure, Server server) {
		super(fstructure, server);
		this.type = type.USER;
		this.user = (User) fstructure;
	}
	
	// Check if the user can call the controller (this cannot when the user is interacting with the fridge)
	public Boolean canCallController(){
		return !usingFridge;
	}
	
    @Command(description="Leave the house")
    public void leave() {
    	if(this.canCallController() == false){
    		System.out.println("[WARNING] User should first close the fridge");
    		return;
    	}
    	
    	if(this.user.inHouse == false){
    		System.out.println("[ERROR] User already left the house");
    		return;
    	}
    	
        this.user.inHouse = false;
        
        try {
			this.controller.userLeave(this.user.id);
		} catch (AvroRemoteException e) {
			System.out.println("[WARNING] Controller down, trying to select another one.");
			this.StartElection();
		}
    }
    
    @Command(description="Enter the house")
    public void enter() {
    	if(this.user.inHouse == true){
    		System.out.println("[ERROR] User already in the house");
    		return;
    	}
    	
    	if(this.canCallController() == false){
    		System.out.println("[WARNING] User should first close the fridge");
    		return;
    	}
    	
        this.user.inHouse = true;
        
        try {
			this.controller.userEnters(this.user.id);
		} catch (AvroRemoteException e) {
			System.out.println("[WARNING] Controller down, trying to select another one.");
			this.StartElection();
		}
    }
    
    @Command(description="Get the temperature in the house")
    public void getTemperature() {
    	if(this.canCallController() == false){
    		System.out.println("[WARNING] User should first close the fridge");
    		return;
    	}
    	
    	if(this.user.inHouse == false){
    		System.out.println("[ERROR] User not in house");
    		return;
    	}

    	try {
			System.out.print(this.controller.getTemperature());
		} catch (AvroRemoteException e) {
			System.out.println("[WARNING] Controller down, trying to select another one.");
			this.StartElection();
		}
    }
    
    @Command(description="Get the temperature history in the house")
    public void getTemperatureHistory() {
    	if(this.canCallController() == false){
    		System.out.println("[WARNING] User should first close the fridge");
    		return;
    	}
    	
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
			System.out.println("[WARNING] Controller down, trying to select another one.");
			this.StartElection();
		}
    }
    
	@Command(description="Turn a light on")
    public void turnLightOn(
    		@Param(name="id", description="The identifier of the light")
    		Integer id
    		) {
		this.setLight(id, true);
    }
	
	@Command(description="Turn a light off")
    public void turnLightOff(
    		@Param(name="id", description="The identifier of the light")
    		Integer id
    		) {
		this.setLight(id, false);
    }
	
	public void setLight(int id, Boolean active){
    	if(this.canCallController() == false){
    		System.out.println("[WARNING] User should first close the fridge");
    		return;
    	}
		
		if(this.user.inHouse == false){
    		System.out.println("[ERROR] User not in house");
    		return;
    	}
		
		try {
			Boolean success = null;
			if(active == true){
				success = this.controller.turnLightOn(id);
				
				if(success){
					System.out.println("Light " + id + " On");
				}
			}else{
				success = this.controller.turnLightOff(id);
				
				if(success){
					System.out.println("Light " + id + " Off");
				}
			}
			
			if(success == false){
				System.out.println("Light " + id + " offline");
			}
		} catch (Failure e ){
			System.out.println(e.getInfo());
		} catch (AvroRemoteException e) {
			System.out.println("[WARNING] Controller down, trying to select another one.");
			this.StartElection();
		}
	}
	
	@Command(description="Get information about devices in the house")
    public void devices() {
    	if(this.canCallController() == false){
    		System.out.println("[WARNING] User should first close the fridge");
    		return;
    	}
		
		if(this.user.inHouse == false){
    		System.out.println("[ERROR] User not in house");
    		return;
    	}
		
		List<DeviceStatus> devices = null;
		try {
			devices = this.controller.getDevices();
		} catch (AvroRemoteException e) {
			System.out.println("[WARNING] Controller down, trying to select another one.");
			this.StartElection();
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
    	if(this.canCallController() == false){
    		System.out.println("[WARNING] User should first close the fridge");
    		return;
    	}
		
		if(this.user.inHouse == false){
    		System.out.println("[ERROR] User not in house");
    		return;
    	}
		
		List<LightStatus> lights = null;
		try {
			lights = this.controller.getLights();
		} catch (AvroRemoteException e) {
			System.out.println("[WARNING] Controller down, trying to select another one.");
			this.StartElection();
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

	@Command(description="Try to open the fridge")
    public void openFridge(
    		@Param(name="id", description="The identifier of the fridge")
    		int id) {
    	if(this.canCallController() == false){
    		System.out.println("[WARNING] User should first close the other fridge");
    		return;
    	}
		
		if(this.user.inHouse == false){
    		System.out.println("[ERROR] User not in house");
    		return;
    	}
		
		protocols.controller.Device fridgeInfo = null; 
		try {
			fridgeInfo = this.controller.openFridge(id, this.user.id);
			this.usingFridge = true;
		} catch(Failure e){
			System.out.println(e.getInfo());
			return; // We dont want to start a connection with this fridge
		} catch (AvroRemoteException e) {
			System.out.println("[WARNING] Controller down, trying to select another one.");
			this.StartElection();
			return;  // We dont want to start a connection with this fridge
		}
		
		
		// Now lets connect the fridge proxy with the actual device
		try{
			InetAddress ipAddress = InetAddress.getByName(fridgeInfo.getIpadress().toString());
			InetSocketAddress socketAddress = new InetSocketAddress(ipAddress, fridgeInfo.getPort());
			Transceiver client = new SaslSocketTransceiver(socketAddress);
			fridge = (Fridge) SpecificRequestor.getClient(Fridge.class, client);
		}catch(IOException e){
			System.err.println("[Error] Connecting to fridge");
		}
    }
	
	@Command(description="Try to close the fridge")
    public void closeFridge(
    		@Param(name="id", description="The identifier of the fridge")
    		int id) {
		if(this.user.inHouse == false){
    		System.out.println("[ERROR] User not in house");
    		return;
    	}
		
		try {
			this.controller.closeFridge(id, this.user.id);
			
			// Remove the RPC connection
			this.fridge = null;
			
			this.usingFridge = false;
		} catch(Failure e){
			System.out.println(e.getInfo());
		} catch (AvroRemoteException e) {
			System.out.println("[WARNING] Controller down, trying to select another one.");
			this.StartElection();
		}
    }
	
	@Command(description="Add an item to the fridge")
    public void addItemToFridge(
    		@Param(name="item", description="string")
    		String item) {
		
		if(this.user.inHouse == false){
    		System.out.println("[ERROR] User not in house");
    		return;
    	}
		
		if(this.canCallController() == true){
    		System.out.println("[ERROR] User has not opened a fridge");
    		return;
    	}
		
		try {
			this.fridge.addItem(item);
		} catch(protocols.fridge.Failure e){
			System.out.println(e.getInfo());
		} catch (AvroRemoteException e) {
			// Connection to fridge lost
			this.fridge = null;
			this.usingFridge = false;
		}
    }
	
	@Command(description="Remove an item from the fridge")
    public void removeItemFromFridge(
    		@Param(name="item", description="string")
    		String item) {
		
		if(this.user.inHouse == false){
    		System.out.println("[ERROR] User not in house");
    		return;
    	}
		
		if(this.canCallController() == true){
    		System.out.println("[ERROR] User has not opened a fridge");
    		return;
    	}
		
		try {
			this.fridge.removeItem(item);
		} catch(protocols.fridge.Failure e){
			System.out.println(e.getInfo());
		} catch (AvroRemoteException e) {
			// Connection to fridge lost
			this.fridge = null;
			this.usingFridge = false;
		}
    }
	
	@Command(description="Get items in a fridge identified by id")
    public void getItemsInFridge(
    		@Param(name="id", description="int")
    		int id) {
		
			try {
				for(CharSequence item : this.getFridgeItems(id)){
					System.out.println(item);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
    }
	
	public List<CharSequence> getFridgeItems(int id) throws Exception{
		if(this.user.inHouse == false){
    		throw new Exception("[ERROR] User not in house");
    	}
		
		List<CharSequence> out = null;
		
		try {
			out =  this.controller.getFridgeItems(id);
		} catch(Failure e){
			throw new Exception(e.getInfo().toString());
		} catch (AvroRemoteException e) {
			System.out.println("[WARNING] Controller down, trying to select another one.");
			this.StartElection();
		}
		
		return out;
	}
}
