package servers;

import org.apache.avro.AvroRemoteException;

import structures.Device;
import structures.User;

public class UserServer extends Server implements protocols.user.User {
	public User user;
	
	public UserServer(Device d) {
		super(d);
		this.user = (User) d;
	}

	@Override
	public boolean inHouse() throws AvroRemoteException {
		return user.inHouse;
	}
}
