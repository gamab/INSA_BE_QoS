package proxySide;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ListIterator;

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
	
	private ArrayList<Reservation> resRec;
	

	public ProxyServer(Controller ctrlr) throws IOException {
		sock = new ServerSocket(port);
		resRec = new ArrayList<>();
		this.ctrlr = ctrlr;
	}

	public void run() {
		int numberFails = 0;
		
		while (numberFails < 3) {
			Socket sockfd = null;
			try {
				Log.d(TAG, "Waiting for client");
				sockfd = sock.accept();
				Log.d(TAG, "Client connected");

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
				Log.d(TAG, "Received a reservation");
				Reservation reza = (Reservation) msg;
				if (!this.alreadyReceivedReza(reza)) {
					boolean couldAccept;
					Log.d(TAG, "It is a NEW reservation");
					couldAccept = this.ctrlr.processReservation(reza);
					Log.d(TAG, "Could accept reza ? " + couldAccept);
					reza.setAck(couldAccept);
					if (couldAccept) {
						Log.d(TAG, "Remember Reservation");						
						this.resRec.add(reza);						
					}
				}
				else {
					Log.d(TAG, "It is an OLD reservation => hence it was accepted");	
					reza.setAck(true);
				}

				//here send the answer to the proxy
				try {
					os.write(Message.toArray(reza));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e(TAG, "Could not send answer to reservation");
				}
			}
			else if (msg instanceof Bye) {
				Log.d(TAG, "Received a bye message");
				Reservation resa = new Reservation(((Bye) msg).getFlow1(), ((Bye) msg).getFlow2(), true);
				int position = resaPosition(resa);
				if (position != -1) {
					Log.d(TAG,"Removing reservation from the arraylist");
					this.resRec.remove(position);
					this.ctrlr.processBye((Bye) msg);
				}				
			}
			else {
				Log.d(TAG, "Received an unknown message");				
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
	
	private boolean alreadyReceivedReza(Reservation resa) {
		ListIterator<Reservation> lit = this.resRec.listIterator();
		boolean found = false;
		Reservation r;
		while (lit.hasNext() && !found) {
			r = lit.next();
			//Log.d(TAG,"Check if " + resa.getFlow1() + " " + resa.getFlow2() + " = " + r.getResa().getFlow1() + " " + r.getResa().getFlow2());
			found = resaAreSame(r,resa);
		}
		return found;
	}
	
	private boolean resaAreSame(Reservation r, Reservation resa) {
		// TODO Auto-generated method stub
		if (! resa.getFlow1().equals(r.getFlow1())) {
			return false;
		}
		if (! resa.getFlow2().equals(r.getFlow2())) {
			return false;
		}
		return true;
	}
//
//	private boolean rezaWasAccepted(Reservation resa) {
//		ListIterator<Reservation> lit = this.resRec.listIterator();
//		Reservation r;
//		while (lit.hasNext()) {
//			r = lit.next();
//			//Log.d(TAG,"Check if " + resa.getFlow1() + " " + resa.getFlow2() + " = " + r.getResa().getFlow1() + " " + r.getResa().getFlow2());
//			if (resaAreSame(r,resa)) {
//				Log.d(TAG,"Reza was accepted ?" + r.getAck());
//				return r.getAck();
//			}
//		}
//		return false;
//	}
	
	private int resaPosition(Reservation resa) {
		ListIterator<Reservation> lit = this.resRec.listIterator();
		int position = -1;
		Reservation r;
		while (lit.hasNext()) {
			position++;
			r = lit.next();
			//Log.d(TAG,"Check if " + resa.getFlow1() + " " + resa.getFlow2() + " = " + r.getResa().getFlow1() + " " + r.getResa().getFlow2());
			if (resaAreSame(r,resa)) {
				Log.d(TAG,"Reza was accepted ?" + resa.getAck());
				return position;
			}
		}
		return -1;
	}
}
