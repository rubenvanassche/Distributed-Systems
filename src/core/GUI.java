package core;

import managers.*;
import structures.Entity.Type;
import asg.cliche.CLIException;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import java.util.Scanner;

public class GUI extends Thread {
	public Manager manager;
	private Boolean keepRunning = true;
	
	public GUI(Manager m){
		this.manager = m;
	}

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
		
		
		Shell s = ShellFactory.createConsoleShell(name, "", this.manager);
		Scanner scanner = new Scanner(System.in);
		while(this.keepRunning){
			try{
				System.out.print(name + ">" );
				String input = scanner.nextLine();
				s.processLine(input);
			}catch (CLIException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void quit(){
		this.keepRunning = false;
	}
	
}
