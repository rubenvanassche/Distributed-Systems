package servers;

import org.apache.avro.AvroRemoteException;

public class LightServer extends Server implements protocols.light.Light {

	@Override
	public boolean powerOn() throws AvroRemoteException {
		return true;
	}

	@Override
	public boolean powerOff() throws AvroRemoteException {
		return true;
	}

}
