package managers;

import org.apache.avro.ipc.Server;

import asg.cliche.Command;
import core.GUI;
import structures.Entity.Type;

public class Manager implements Runnable {
	public Server server;
	public Type type;
	private GUI thread = null;
	
	Manager(Server fserver){
		this.server = fserver;
	}
	
	@Command(description="Stops the execution of the device")
    public void shutdown() {
        this.server.close();
        this.thread.quit();
    }
	
	public void rebootCLI(){
		this.thread.quit();
		this.thread = new GUI(this);
		this.thread.start();
		
		this.server.start();
		
		try{
			this.server.join();
		}catch(InterruptedException e){}
	}
	
	@Override
	public void run() {
		thread = new GUI(this);
		thread.start();
		
		this.server.start();
		
		try{
			this.server.join();
		}catch(InterruptedException e){}
	}
}
