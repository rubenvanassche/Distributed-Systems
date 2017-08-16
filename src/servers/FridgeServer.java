package servers;

import java.util.LinkedList;
import java.util.List;

import org.apache.avro.AvroRemoteException;

import managers.FridgeManager;
import protocols.fridge.Failure;
import structures.Device;
import structures.Fridge;

public class FridgeServer extends Server implements protocols.fridge.Fridge  {
	public Fridge fridge;
	public FridgeManager manager;
	
	public FridgeServer(Device d) {
		super(d);
		this.fridge = (Fridge) d;
	}

	@Override
	public boolean ping() throws AvroRemoteException {
		return true;
	}

	@Override
	public List<CharSequence> getItems() throws AvroRemoteException, Failure {
		List<CharSequence> out = new LinkedList<CharSequence>();
		
		for(String item : this.fridge.contents){
			out.add(item);
		}
		
		return out;
	}

	@Override
	public Void removeItem(CharSequence item) throws AvroRemoteException, Failure {
		this.fridge.removeItem(item.toString());
		
		// Fridge is empty so send this information to the controller
		if(this.fridge.contents.size() == 0){
			this.manager.controller.fridgeIsEmpty(this.fridge.id);
		}
		
		return null;
	}

	@Override
	public Void addItem(CharSequence item) throws AvroRemoteException, Failure {
		this.fridge.addItem(item.toString());
		
		return null;
	}


}
