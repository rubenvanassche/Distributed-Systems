package core;

import managers.*;
import structures.Entity.Type;
import asg.cliche.ShellFactory;
import java.io.IOException;

public class GUI implements Runnable {
	public Manager manager;
	
	public GUI(Manager m){
		this.manager = m;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		String name = this.manager.type.toString() + " ";
		// get the id of the deivce
		if(this.manager.type.equals(Type.CONTROLLER)){
			ControllerManager manager = (ControllerManager) this.manager;
			name += manager.structure.id;
		}else{
			ControlledManager manager  = (ControlledManager) this.manager;
			name += manager.structure.id;
		}
		
		
		try {
			ShellFactory.createConsoleShell(name, "", this.manager).commandLoop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
	}
}
