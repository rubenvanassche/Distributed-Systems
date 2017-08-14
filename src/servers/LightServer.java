package servers;

import org.apache.avro.AvroRemoteException;

import structures.Light;

public class LightServer extends Server implements protocols.light.Light {
	Light light = (Light) device;
	
	@Override
	public boolean powerOn() throws AvroRemoteException {
		light.activated = true;
		return true;
	}

	@Override
	public boolean powerOff() throws AvroRemoteException {
		light.activated = false;
		return true;
	}

	@Override
	public boolean getStatus() throws AvroRemoteException {
		return light.activated;
	}

}
