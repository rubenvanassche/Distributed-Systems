package core;

import org.apache.commons.cli.*;

import avro.hello.proto.Hello;
import avro.hello.server.HelloServer;
import structures.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.apache.avro.AvroRemoteException;
import org.apache.avro.Protocol;
import org.apache.avro.ipc.SaslSocketServer;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;

public class CLI {
	public static void main(String[] args){
        Options options = new Options();

        Option id = new Option("id", "identifier", true, "device identifier");
        id.setRequired(true);
        options.addOption(id);

        Option ip = new Option("ip", "ipadress", true, "the ip adress of this device");
        ip.setRequired(true);
        options.addOption(ip);
        
        Option port = new Option("port", "port", true, "the port of this device to start a server");
        port.setRequired(true);
        options.addOption(port);
        
        Option cip = new Option("cip", "controlleripadress", true, "the ip adress of the controller server");
        cip.setRequired(true);
        options.addOption(cip);
        
        Option cport = new Option("cport", "controllerport", true, "the port of this controller where the server runs");
        cport.setRequired(true);
        options.addOption(cport);
        
        Option type = new Option("type", "type", true, "the type of device to create(controller, fridge, light, sensor, user)");
        type.setRequired(true);
        options.addOption(type);
        
        Option amountofmeasurements = new Option("a", "amountofmeasurements", false, "[Controller-Only] amount of measurements of the temperature sensor the controller keeps");
        options.addOption(amountofmeasurements);
        
        Option startingtemperature = new Option("s", "startingtemperature", false, "[Sensor-Only] initial temperature of the temperature sensor");
        options.addOption(startingtemperature);
        
        Option updatefrequency = new Option("f", "updatefrequency", false, "[Sensor-Only] frequency when temperature senor sends a temperature to the controller");
        options.addOption(updatefrequency);
        

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
            return;
        }
        
        DeviceFactory factory = createFactory(cmd.getOptionValue("id"), 
        		cmd.getOptionValue("cip"), 
        		cmd.getOptionValue("cport"), 
        		cmd.getOptionValue("ip"), 
        		cmd.getOptionValue("port"));
        
        
        String typeValue = cmd.getOptionValue("type");
        Server server = null;
        Device device = null;
        
        if(typeValue.contentEquals("controller")){
        	int a = Integer.parseInt(cmd.getOptionValue("a"));
        	
        	Controller controller = factory.createController(a);
        	device = controller;
        	server = createServer(controller, protocols.controller.Controller.class);
        }else if(typeValue.contentEquals("fridge")){
        	Fridge fridge = factory.createFridge();
        	
        	device = fridge;
        	server = createServer(fridge, protocols.fridge.Fridge.class);
        }else if(typeValue.contentEquals("light")){
        	Light light = factory.createLight();
        	
        	device = light;
        	server = createServer(light, protocols.light.Light.class);
        }else if(typeValue.contentEquals("sensor")){
        	Double s = Double.parseDouble(cmd.getOptionValue("s"));
        	int f = Integer.parseInt(cmd.getOptionValue("f"));
        	
        	if(f == 0){
            	System.out.println("[ERROR] update frequency must be greater than zero!");
            	System.exit(1);
                return;
        	}
        	
        	Sensor sensor = factory.createSensor(s, f);
        	
        	device = sensor;
        	server = createServer(sensor, protocols.sensor.Sensor.class);
        }else if(typeValue.contentEquals("user")){
        	User user = factory.createUser();
        	
        	device = user;
        	server = createServer(user, protocols.user.User.class);
        }else{
        	System.out.println("[ERROR] not a valid type given!");
        	System.exit(1);
            return;
        }
        
        // Start the server
		server.start();
		
		try{
			server.join();
		}catch(InterruptedException e){}
        
	}
	
	public static DeviceFactory createFactory(String fid, String fcontrollerIP, String fcontrollerPort, String fipAdress, String fport){
		int id = Integer.parseInt(fid);
		String controllerIP = fcontrollerIP;
		int controllerPort = Integer.parseInt(fcontrollerPort);
		String ipAdress = fipAdress;
		int port = Integer.parseInt(fport);
		
		DeviceFactory factory = new DeviceFactory(id, controllerIP, controllerPort, ipAdress, port);
		
		return factory;
	}
	
	public static Server createServer(Device device, Class proto){
		Server server = null;
		try{
			server = new SaslSocketServer(new SpecificResponder(proto, device), new InetSocketAddress(6789));
		}catch(IOException e){
			System.err.println("[Error] Failed to start server");
			e.printStackTrace(System.err);
			System.exit(1);
		}
		
		return server;
	}
}
