package proxySide;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import log.Log;
import messages.Bye;
import messages.Message;
import messages.Reservation;
import controller.Controller;

public class ProxyServer {

	public static final int MAX_SIZE = 1024;
	public static final String TAG = "ProxyServer";

	private ServerSocket sock;
	public static final int port = 2015;
	private OutputStream os;
	private InputStream is;
	private Controller ctrlr;

	public ProxyServer(Controller ctrlr) throws IOException {
		sock = new ServerSocket(port);
		this.ctrlr = ctrlr;
	}

	public void run() {
		int numberFails = 0;
		
		while (numberFails < 3) {
			Socket sockfd = null;
			try {
				sockfd = sock.accept();

				os = sockfd.getOutputStream();
				is = sockfd.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "Could not accept client");
				numberFails ++;
				continue;
			}
			
			numberFails = 0;
			
			byte[] data = new byte[MAX_SIZE];

			try {
				is.read(data);
			} catch (IOException ex) {
				ex.printStackTrace();
				Log.e(TAG, "Could not read object");
			}
			Message msg = null;
			try {
				msg = Message.fromArray(data);
			} catch (IOException ex) {
				ex.printStackTrace();
				Log.e(TAG, "Could not extract resa from message");
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
				Log.e(TAG, "Could not find the type of the message");
			}

			if (msg instanceof Reservation) {
				boolean couldAccept = this.ctrlr.processReservation((Reservation) msg);

				//here send the answer to the proxy
				((Reservation) msg).setAck(couldAccept);
				try {
					os.write(msg.toArray(msg));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e(TAG, "Could not send answer to reservation");
				}
			}
			else if (msg instanceof Bye) {
				this.ctrlr.processBye((Bye) msg);		        	
			}
			
			try {
				sockfd.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "Could not close sockfd");
			}
		}
		try {
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "Could not close sock");
		}
	}
}
