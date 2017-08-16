package structures;

import java.util.Vector;

import protocols.controller.Failure;

public class Fridge extends Device {
	public Vector<String> contents = new Vector<String>();
	
	// Backup Controller
	public Controller backupController = new Controller(0);
	public Boolean isBackup = false;
	
	// Add an item to the fridge, if the item already in the fridge an item number will be added
	public void addItem(String item){
		int counter = 0;
		String suffix = "";
		
		while(true){
			if(this.contents.contains(item + suffix) == false){
				this.contents.add(item + suffix);
				break;
			}else{
				counter += 1;
				suffix = String.valueOf(counter);
			}
		}
	}
	
	// Remove an item from the fridge
	public void removeItem(String item) throws Failure{
		if(!this.contents.contains(item)){
			Failure f = new Failure();
			f.setInfo("Fridge does not contain item " + item);
			throw f;
		}
		
		this.contents.remove(item);
	}
}
