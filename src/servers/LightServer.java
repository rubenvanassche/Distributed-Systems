package servers;

import org.apache.avro.AvroRemoteException;

import managers.LightManager;
import structures.Device;
import structures.Light;

public class LightServer extends Server implements protocols.light.Light {
	public Light light;
	public LightManager manager;
	
	public LightServer(Device d) {
		super(d);
		this.light = (Light) d;
	}
	
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
