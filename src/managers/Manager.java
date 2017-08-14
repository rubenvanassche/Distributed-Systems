package managers;

import org.apache.avro.ipc.Server;

public class Manager {
	Server server;
	
	Manager(Server fserver){
		this.server = fserver;
	}
}
