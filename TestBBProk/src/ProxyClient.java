

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

import log.Log;
import messages.Bye;
import messages.FlowDescriptor;
import messages.Message;
import messages.Reservation;

public class ProxyClient {

	public static final int MAX_SIZE = 1024;
	public static final String TAG = "ProxyClient";
	
	public static final byte[] addServ = {(byte)192,(byte)120,(byte)2,(byte)3};

	public static final int port = 2015;

	public static void main(String[] args) {

		Socket sock = null;
		try {
			sock = new Socket(Inet4Address.getByAddress(addServ),port);
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			Log.e(TAG, "Could not connect to the server");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			Log.e(TAG, "Could not get IO streams");
		}
		OutputStream os = null;
		InputStream is = null;


		try {
			os = sock.getOutputStream();
			is = sock.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "Could not accept client");
		}

		byte[] data = new byte[MAX_SIZE];
		byte [] ipSrc = new byte[] {(byte)192,(byte)168,(byte)0,(byte)2};
		byte [] ipDst1 = new byte[] {(byte)192,(byte)168,(byte)1,(byte)3};
		byte [] ipDst2 = new byte[] {(byte)192,(byte)168,(byte)1,(byte)3};
		float transmRate = 64.0f;
		//Inet4Address ipSrc, Inet4Address ipDst, int pSrc,int pDst, float transmRate, int proto
		FlowDescriptor fd1 = null;
		try {
			fd1 = new FlowDescriptor(
					(Inet4Address)Inet4Address.getByAddress(ipSrc),
					(Inet4Address)Inet4Address.getByAddress(ipDst1), 
					1340,
					1342,
					transmRate,
					"UDP");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.e(TAG, "Could not create fd1");			
		}
		FlowDescriptor fd2 = null;
		try {
			fd2 = new FlowDescriptor(
					(Inet4Address)Inet4Address.getByAddress(ipDst1),
					(Inet4Address)Inet4Address.getByAddress(ipSrc), 
					1345,
					1347,
					transmRate,
					"UDP");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.e(TAG, "Could not create fd2");			
		}

		Log.d(TAG, "Sending resa to the BB with :");
		Log.d(TAG, fd1.toString());
		Log.d(TAG, fd2.toString());
		Reservation resa = new Reservation(fd1, fd2, false);
		try {
			data = ((Message) resa).toArray(resa);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			os.write(data);
		} catch (IOException ex) {
			ex.printStackTrace();
			Log.e(TAG, "Could not write object");
		}

		Log.d(TAG, "Sent resa now read answer");

		Message msg = null;
		try {
			is.read(data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			msg = Message.fromArray(data);
		} catch (IOException ex) {
			ex.printStackTrace();
			Log.e(TAG, "Could not extract resa from message");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			Log.e(TAG, "Could not find the type of the message");
		}

		Log.d(TAG, "Answer read.");

		if (msg instanceof Reservation) {
			Log.d(TAG, "msg is the answer to our resa : " + 
					String.valueOf(((Reservation) msg).getAck()));
		}

		try {
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "Could not close sockfd");
		}
	}
}
