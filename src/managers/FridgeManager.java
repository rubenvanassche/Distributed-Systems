package managers;

import org.apache.avro.ipc.Server;

import protocols.controller.Controller;
import structures.Device;

public class FridgeManager extends ControlledManager {

	public FridgeManager(Device fdevice, Server server) {
		super(fdevice, server);
		// TODO Auto-generated constructor stub
	}

}
