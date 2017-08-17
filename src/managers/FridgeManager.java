package managers;

import org.apache.avro.ipc.Server;

import protocols.controller.Controller;
import structures.Device;

public class FridgeManager extends ReplicatedManager {

	public FridgeManager(Device fstructure, Server server) {
		super(fstructure, server);
		this.type = type.FRIDGE;
		// TODO Auto-generated constructor stub
	}

}
