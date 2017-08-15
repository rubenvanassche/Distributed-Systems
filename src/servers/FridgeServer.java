package servers;

import java.util.LinkedList;
import java.util.List;

import org.apache.avro.AvroRemoteException;

import managers.FridgeManager;
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
	public List<CharSequence> getItems() throws AvroRemoteException {
		List<CharSequence> out = new LinkedList<CharSequence>();
		
		for(String item : this.fridge.contents){
			out.add(item);
		}
		
		return out;
	}

	@Override
	public boolean removeItem(CharSequence item) throws AvroRemoteException {
		Boolean success = this.fridge.removeItem(item.toString());
		
		// Fridge is empty so send this information to the controller
		if(this.fridge.contents.size() == 0){
			this.manager.controller.fridgeIsEmpty(this.fridge.id);
		}
				
		return success;
	}

	@Override
	public boolean addItem(CharSequence item) throws AvroRemoteException {
		Boolean success = this.fridge.addItem(item.toString());
		
		return success;
	}

	@Override
	public Void openFridge(int userId) throws AvroRemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void closeFridge() throws AvroRemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
