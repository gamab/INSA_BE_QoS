package networking;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

import messages.Message;
import messages.Reservation;


public class ProxyClient {
    
	public static void resa_Flux(int port, int MAX_SIZE,Controller Cont,byte[] addrBB,Reservation resa) {

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
			os.write(((Message) resa).toArray(resa));
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}

		System.out.println("Sent resa now read answer");

		byte[] data = new byte[MAX_SIZE];
		Message msg = null;
		try {
			is.read(data);
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			msg = Message.fromArray(data);
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		System.out.println("Answer read.");
		
		/////////////////////////////
		if(msg instanceof Reservation)
        {
              Reservation reserv=(Reservation)msg;
              if(reserv.getAck())
              { 
                    System.out.println("Data received : Reservation_[ok]");
                    Cont.responseMessage_Ack();                                       
              }
              else
              {
                    System.out.println("Data received : Reservation_[NOK]");
                    Cont.responseMessage_NoAck();
              }
         }    
		////////////////////////////
		
		try {
			sock.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
