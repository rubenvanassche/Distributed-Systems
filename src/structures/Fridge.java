package structures;

import java.util.Vector;

public class Fridge extends Device {
	public Vector<String> contents = new Vector<String>();
	
	// Backup Controller
	public Controller backupController = new Controller(0);
	public Boolean isBackup = false;
}
