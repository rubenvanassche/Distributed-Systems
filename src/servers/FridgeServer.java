package servers;

import org.apache.avro.AvroRemoteException;

import structures.Device;
import structures.Fridge;

public class FridgeServer extends Server implements protocols.fridge.Fridge  {
	public Fridge fridge;
	
	public FridgeServer(Device d) {
		super(d);
		this.fridge = (Fridge) d;
	}

	@Override
	public boolean ping() throws AvroRemoteException {
		return true;
	}

}
