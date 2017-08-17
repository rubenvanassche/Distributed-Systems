package servers;

import org.apache.avro.AvroRemoteException;

import managers.UserManager;
import structures.Device;
import structures.User;

public class UserServer extends Server implements protocols.user.User {
	public User user;
	public UserManager manager;
	
	public UserServer(Device d) {
		super(d);
		this.user = (User) d;
	}

	@Override
	public boolean inHouse() throws AvroRemoteException {
		return user.inHouse;
	}

	@Override
	public Void message(CharSequence contents) throws AvroRemoteException {
		System.out.println(contents.toString());
		return null;
	}
	
	@Override
	public Void reRegister(CharSequence ipadress, int port) throws AvroRemoteException {
		// Change the connection to the controller
		this.manager.reRegister(ipadress.toString(), port);
		
		return null;
	}

}
