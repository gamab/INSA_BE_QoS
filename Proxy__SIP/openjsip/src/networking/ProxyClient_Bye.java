package networking;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

import messages.Bye;
import messages.Message;

public class ProxyClient_Bye {

	public static void resa_Flux(int port, int MAX_SIZE,Controller Cont,byte[] addrBB,Bye bye) {

		Socket sock = null;
		try {
			sock = new Socket(Inet4Address.getByAddress(addrBB),port);
		} 
		catch (UnknownHostException e2) 
		{
			e2.printStackTrace();
		} 
		catch (IOException e2)
		{
			e2.printStackTrace();
		}
		OutputStream os = null;
		InputStream is = null;


		try {
			os = sock.getOutputStream();
			is = sock.getInputStream();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	
		try {
			os.write(((Message) bye).toArray(bye));
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}

		System.out.println("Sent bye now read answer");
		
		
		try {
			sock.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
