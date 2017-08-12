package structures;

import java.util.HashMap;
import java.util.Map;

// Class representing the temperature
public class TemperatureHistory {
	public int deviceID;
	public Map<Double, Double> temperatures = new HashMap<Double, Double>();
}
