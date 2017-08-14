package managers;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.Server;

import protocols.controller.Controller;
import protocols.controller.Failure;
import structures.Device;

public class LightManager extends ControlledManager {

	public LightManager(Device fdevice, Server server) {
		super(fdevice, server);
		// TODO Auto-generated constructor stub
	}

}
