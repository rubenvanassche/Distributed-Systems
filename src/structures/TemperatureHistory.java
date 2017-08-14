package structures;

import java.util.LinkedList;
import java.util.Queue;

// Class representing the temperature
public class TemperatureHistory {
	public int deviceID;
	public int amountOfMeasurements;
	public Queue<Double> temperatures = new LinkedList<Double>();
	
	public void addTemperature(double temperature){
		if(temperatures.size() == amountOfMeasurements){
			temperatures.remove();
		}
		
		temperatures.add(temperature);
	}
}
