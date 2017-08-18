package managers;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.Server;

import asg.cliche.Command;
import protocols.controller.Controller;
import protocols.controller.Failure;
import structures.Device;
import structures.Sensor;

public class SensorManager extends ControlledManager {
	public Sensor sensor;
	public int updatesSend; // Needed for calculating when to send the next update
	
	public SensorManager(Device fstructure, Server server) {
		super(fstructure, server);
		this.type = type.SENSOR;
		this.sensor = (Sensor) this.structure;
		
		this.registerToController(); // Register to controller, only needed for light and sensor because user and fridge get registered in the replicationmanager
		
		startUpdating();
	}
	
	// Recalibrates the clocks
	class CalibrateClockTask extends TimerTask{

		@Override
		public void run() {
			try {
				Double oldTime = sensor.time;
				Double syncedTime = controller.getTime();
				Double newTime = sensor.time;
				
				Double delay = (newTime - oldTime)/2.0;
				sensor.time = syncedTime + delay;
				Double calibratedTime = sensor.time;
				
				System.out.println("[INFO] Synced time, old: " + oldTime + ", request: " + syncedTime + ", new: " + calibratedTime);
			} catch (AvroRemoteException e) {
				// Somehting went wrong, let's ignore it!
			}
		}
		
	}
	
	class SendTemperatureTask extends TimerTask{
		@Override
		public void run() {
			if(sensor.time - (sensor.updateFrequency * updatesSend) > sensor.updateFrequency){
				// Send a new temperature to the controller
				updatesSend += 1;
				sendTemperature();
			}
		}
		
		public void sendTemperature(){
			try {
				controller.updateTemperature(sensor.id, sensor.temperature);
			} catch (Failure e){
				System.err.println(e.getMessage());
			} catch (AvroRemoteException e) {
				System.err.println("[ERROR] Couldn't find a controller");
			}
		}
	}
	
	// Starts updating the clock and temperature + calibrating the clock
	public void startUpdating(){
		SendTemperatureTask sendTemperatureTask = new SendTemperatureTask();
		CalibrateClockTask calibrateClockTask = new CalibrateClockTask();
		
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(sendTemperatureTask, 0, 1*10);
		timer.scheduleAtFixedRate(calibrateClockTask, 0, 10*1000);
	}


	@Command(description="get the current temperature")
	public void getTemperature(){
		System.out.println(this.sensor.temperature);
	}
	
	@Command(description="get the current time")
	public void getClock(){
		System.out.println(this.sensor.time);
	}
}
