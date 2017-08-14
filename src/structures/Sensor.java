package structures;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Sensor extends Device{
	// Temperature to start from when booting
	public Double startingTemperature;
	
	// Drift value
	public Double driftValue;
	
	// The interval in seconds when to update the temperature
	public int updateFrequency;
	
	// Temperature
	public Double temperature;
	
	// Clock
	public Double time;
	
	class UpdateTask extends TimerTask{		
		@Override
		public void run() {
			this.updateTime();
			this.updateTemperature();
		}
		
		public void updateTime(){
			time += 1 + driftValue;
		}
		
		public void updateTemperature(){
			// generate randomness
			Random generator = new Random();
			Double min = -1.0;
			Double max = 1.0;
			Double number = min + (max - min) * generator.nextDouble();
			
			temperature += number;
		}
	}
	
	
	public Sensor(Double fStartingTemperature, int fUpdateFrequency, Double fDriftValue){
		startingTemperature = fStartingTemperature;
		temperature = fStartingTemperature;
		updateFrequency = fUpdateFrequency;
		driftValue = fDriftValue;
		time = 0.0;
		
		startUpdating();
	}
	
	// Starts updating the clock and temperature
	public void startUpdating(){
		UpdateTask updateTask = new UpdateTask();
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(updateTask, 0, 1*1000);
	}
}
