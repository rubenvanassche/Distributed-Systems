package structures;

import java.util.Vector;

public class Fridge extends Device {
	public Vector<String> contents = new Vector<String>();
	
	// Backup Controller
	public Controller backupController = new Controller(0);
	public Boolean isBackup = false;
	
	// Add an item to the fridge, if the item already in the fridge an item number will be added
	public Boolean addItem(String item){
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
		
		return true;
	}
	
	// Remove an item from the fridge
	public Boolean removeItem(String item){
		if(!this.contents.contains(item)){
			return false;
		}
		
		this.contents.remove(item);
		return true;
	}
}
