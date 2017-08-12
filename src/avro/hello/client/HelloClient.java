package avro.hello.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.apache.avro.ipc.SaslSocketTransceiver;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import avro.hello.proto.Hello;

public class HelloClient {
	public static void main(String[] args){
		try{
			Transceiver client = new SaslSocketTransceiver(new InetSocketAddress(6789));
			Hello proxy = (Hello) SpecificRequestor.getClient(Hello.class, client);
			CharSequence response = proxy.sayHello("Bob");
			System.out.println(response);
			client.close();
		}catch(IOException e){
			System.err.println("[Error] Connecting to server");
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}
}
