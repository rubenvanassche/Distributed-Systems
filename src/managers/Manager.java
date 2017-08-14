package managers;

import org.apache.avro.ipc.Server;

import asg.cliche.Command;
import structures.Entity.Type;

public class Manager {
	public Server server;
	public Type type;
	
	Manager(Server fserver){
		this.server = fserver;
	}
	
	@Command(description="Stops the execution of the device")
    public void shutdown() {
        this.server.close();
    }

	public void run() {
		this.server.start();
		
		try{
			this.server.join();
		}catch(InterruptedException e){}
	}
}
