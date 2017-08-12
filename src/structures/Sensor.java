package structures;

public class Sensor extends Device{
	// Temperature to start from when booting
	Double startingTemperature;
	
	// Drift value
	Double driftValue;
	
	// The interval in seconds when to update the temperature
	int updateFrequency;
	
	// Temperature
	Double temperature;
	
	// Clock
	Double time;
	
	public Sensor(Double fStartingTemperature, int fUpdateFrequency){
		startingTemperature = fStartingTemperature;
		temperature = fStartingTemperature;
		updateFrequency = fUpdateFrequency;
	}
}
