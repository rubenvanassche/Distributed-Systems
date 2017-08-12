package structures;

import java.util.Vector;

// Class representing the data of a controller
public class Controller extends Device{	
	// List of all the devices
	public Vector<Entity> users = new Vector<Entity>();
	public Vector<Entity> fridges = new Vector<Entity>();
	public Vector<Entity> sensors = new Vector<Entity>();
	public Vector<Entity> lights = new Vector<Entity>();
	
	// List of temperatures
	public Vector<TemperatureHistory> temperatures = new Vector<TemperatureHistory>();
	
	// Amount of measurements to keep
	public int amountOfMeasurements;
	
	public Controller(int fAmountOfMeasurements){
		amountOfMeasurements = fAmountOfMeasurements;
	}
}
