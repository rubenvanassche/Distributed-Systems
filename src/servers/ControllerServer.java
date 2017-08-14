package servers;

import org.apache.avro.AvroRemoteException;

import protocols.controller.Device;
import protocols.controller.Failure;

public class ControllerServer extends Server implements protocols.controller.Controller {


	@Override
	public boolean register(Device device) throws AvroRemoteException, Failure {
		// TODO Auto-generated method stub
		return false;
	}

}
