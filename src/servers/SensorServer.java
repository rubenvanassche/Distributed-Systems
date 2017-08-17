package servers;

import org.apache.avro.AvroRemoteException;

import managers.SensorManager;
import structures.Device;
import structures.Sensor;

public class SensorServer extends Server implements protocols.sensor.Sensor {
	public Sensor sensor;
	public SensorManager manager;
	
	public SensorServer(Device d) {
		super(d);
		this.sensor = (Sensor) d;
	}

	@Override
	public double getTemperature() throws AvroRemoteException {
		// TODO Auto-generated method stub
		return this.sensor.temperature;
	}

	@Override
	public double getClock() throws AvroRemoteException {
		// TODO Auto-generated method stub
		return this.sensor.time;
	}

	@Override
	public boolean ping() throws AvroRemoteException {
		return true;
	}
	
	@Override
	public Void reRegister(CharSequence ipadress, int port) throws AvroRemoteException {
		// Change the connection to the controller
		this.manager.reRegister(ipadress.toString(), port);
		
		return null;
	}
}
