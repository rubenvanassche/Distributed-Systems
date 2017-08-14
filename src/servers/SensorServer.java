package servers;

import org.apache.avro.AvroRemoteException;

import structures.Device;
import structures.Sensor;

public class SensorServer extends Server implements protocols.sensor.Sensor {
	public Sensor sensor;
	
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
}
