package core;

import org.apache.commons.cli.*;

import managers.Manager;


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
        options.addOption(cip);
        
        Option cport = new Option("cport", "controllerport", true, "the port of this controller where the server runs");
        options.addOption(cport);
        
        Option type = new Option("type", "type", true, "the type of device to create(controller, fridge, light, sensor, user)");
        type.setRequired(true);
        options.addOption(type);
        
        Option amountofmeasurements = new Option("a", "amountofmeasurements", true, "[Controller-Only] amount of measurements of the temperature sensor the controller keeps");
        options.addOption(amountofmeasurements);
        
        Option startingtemperature = new Option("s", "startingtemperature", true, "[Sensor-Only] initial temperature of the temperature sensor");
        options.addOption(startingtemperature);
        
        Option updatefrequency = new Option("f", "updatefrequency", true, "[Sensor-Only] frequency when temperature senor sends a temperature to the controller");
        options.addOption(updatefrequency);
        
        Option driftvalue = new Option("d", "driftvalue", true, "[Sensor-Only] the value by which the clock drifts every second");
        options.addOption(driftvalue);
        

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
        
        createManagers(cmd);
        
	}
	
	public static void createManagers(CommandLine cmd){        
        String type = cmd.getOptionValue("type");
        type = type.toUpperCase(); // So it matches the type enum in structures.entity
    
        DeviceFactory deviceFactory = null;
        
        if(type.contentEquals("FRIDGE") || type.contentEquals("LIGHT") || type.contentEquals("SENSOR") || type.contentEquals("USER")){
        	try{
        		Integer.parseInt(cmd.getOptionValue("cport"));
        		if(cmd.getOptionValue("cip").equals("")){
        			throw new Exception();
        		}
        	}catch (Exception e) {
        		System.out.println("[ERROR] when building a controlled device, a cip and cport are required!");
            	System.exit(1);
                return;
			}
        	
        	deviceFactory = createFactory(cmd.getOptionValue("id"), 
            		cmd.getOptionValue("cip"), 
            		cmd.getOptionValue("cport"), 
            		cmd.getOptionValue("ip"), 
            		cmd.getOptionValue("port"));
        	
        }else{
        	deviceFactory = createFactory(cmd.getOptionValue("id"),  
            		cmd.getOptionValue("ip"), 
            		cmd.getOptionValue("port"));
        }
        
        
        ManagerFactory factory = new ManagerFactory(deviceFactory);
        Manager manager = null;
        
        if(type.contentEquals("CONTROLLER")){
        	int amountOfMeasurements = 0;
        	
        	try{
        		amountOfMeasurements = Integer.parseInt(cmd.getOptionValue("a"));
        	}catch (Exception e) {
        		System.out.println("[ERROR] when building an controller, an amount of measurements is required!");
            	System.exit(1);
                return;
			}
        	
        	
        	manager = factory.createControllerManager(amountOfMeasurements);
        }else if(type.contentEquals("FRIDGE")){
        	manager = factory.createFridgeManager();
        }else if(type.contentEquals("LIGHT")){
        	manager = factory.createLightManager();
        }else if(type.contentEquals("SENSOR")){
        	Double startingTemperature = 0.0;
        	int updateFrequency = 0;
        	Double driftValue = 0.0;
        	
        	try{
        		startingTemperature = Double.parseDouble(cmd.getOptionValue("s"));
        		updateFrequency = Integer.parseInt(cmd.getOptionValue("f"));
        		driftValue = Double.parseDouble(cmd.getOptionValue("d"));
        	}catch (Exception e) {
        		System.out.println("[ERROR] when building an controller, an starting temperature,update frequency and drift value is required!");
            	System.exit(1);
                return;
			}
        	
        	if(updateFrequency <= 0){
            	System.out.println("[ERROR] update frequency must be greater than zero!");
            	System.exit(1);
                return;
        	}
        	
        	if(driftValue <= 0){
            	System.out.println("[ERROR] drift value must be greater than zero!");
            	System.exit(1);
                return;
        	}
        	
        	manager = factory.createSensorManager(startingTemperature, updateFrequency, driftValue);
        }else if(type.contentEquals("USER")){
        	manager = factory.createUserManager();
        }else{
        	System.out.println("[ERROR] not a valid type given!");
        	System.exit(1);
            return;
        }
        
        // Start the server
        manager.run();
        
        System.out.println("test");
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
	
	public static DeviceFactory createFactory(String fid, String fipAdress, String fport){
		int id = Integer.parseInt(fid);
		String controllerIP = "0.0.0.0";
		int controllerPort = 5649;
		String ipAdress = fipAdress;
		int port = Integer.parseInt(fport);
		
		DeviceFactory factory = new DeviceFactory(id, controllerIP, controllerPort, ipAdress, port);
		
		return factory;
	}
	
}
