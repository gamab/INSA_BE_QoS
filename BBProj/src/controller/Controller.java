package controller;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

import com.jcraft.jsch.JSchException;
import org.apache.commons.codec.digest.DigestUtils;

import log.Log;
import messages.Bye;
import messages.FlowDescriptor;
import messages.Reservation;
import routerconf.RouterDescriptor;
import routerconf.RouterRSRVTable;
import routerconf.SSHRouterClient;
import routerconf.TelnetRouterClient;

public class Controller {

	public static final String TAG = "Controller";

	public static final String LOGIN = "login";
	public static final String PASS = "mdp";
	public static final int TELNETPORT = 23;
	public static final int SSHPORT = 22;

	private ArrayList<RouterDescriptor> rtrDs;
	private HashMap<RouterDescriptor,RouterRSRVTable> rtrTable;

	private static final String nameScriptResa = "ifconfig eth2";

	public Controller() {
		rtrDs = new ArrayList<>();
		rtrTable = new HashMap<>();	
		byte [] ipRtr;
		byte [] idRtr;
		byte [] prefix;
		byte[] mask = new byte[4];
		RouterDescriptor rtrD;
		float availRess;

		try {
			//Router 1
			ipRtr = new byte[] {(byte)192,(byte)120,(byte)2,(byte)3};
			idRtr = new byte[] {(byte)101,(byte)101,(byte)101,(byte)101};
			prefix = new byte[] {(byte)192,(byte)168,(byte)1,(byte)0};
			mask[0]=(byte)255; mask[1]=(byte)255; mask[2]=(byte)255; mask[3] = 0; 
			availRess = 100.0f;

			rtrD = new RouterDescriptor( 
					(Inet4Address)Inet4Address.getByAddress(idRtr),
					(Inet4Address)Inet4Address.getByAddress(ipRtr), 
					(Inet4Address)Inet4Address.getByAddress(prefix),mask,
					availRess);

			rtrDs.add(rtrD);
			rtrTable.put(rtrD, new RouterRSRVTable(rtrD.getMaxRess()));
			
			//Router 2
			ipRtr = new byte[] {(byte)192,(byte)120,(byte)2,(byte)4};
			idRtr = new byte[] {(byte)102,(byte)102,(byte)102,(byte)102};
			prefix = new byte[] {(byte)192,(byte)168,(byte)0,(byte)0};
			mask[0]=(byte)255; mask[1]=(byte)255; mask[2]=(byte)255; mask[3] = 0; 
			availRess = 100.0f;

			rtrD = new RouterDescriptor( 
					(Inet4Address)Inet4Address.getByAddress(idRtr),
					(Inet4Address)Inet4Address.getByAddress(ipRtr), 
					(Inet4Address)Inet4Address.getByAddress(prefix),mask,
					availRess);

			rtrDs.add(rtrD);
			rtrTable.put(rtrD, new RouterRSRVTable(rtrD.getMaxRess()));
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		printRouterDescriptors();
	}

	public void printRouterDescriptors() {
		ListIterator<RouterDescriptor> lit = this.rtrDs.listIterator();

		RouterDescriptor curDescriptor = null;
		Log.d(TAG,"Printing all router descriptors");
		
		while (lit.hasNext()) {
			curDescriptor = lit.next();
			Log.d(TAG,curDescriptor.toString());
		}
	}

	public boolean processReservation(Reservation msg) {
		//Extract flow from reservation
		FlowDescriptor flow1 = msg.getFlow1();
		FlowDescriptor flow2 = msg.getFlow2();

		//Identify Router in charge of the flow
		Inet4Address ipSrc1 = flow1.getIpSrc();
		Inet4Address ipSrc2 = flow2.getIpSrc();

		Log.d(TAG, "Flow 1 of resa is " + flow1);
		Log.d(TAG, "Flow 2 of resa is " + flow2);
		
		RouterDescriptor rtrD1 = this.getRouteurForIp(ipSrc1);
		if (rtrD1 == null) {
			Log.e(TAG, "Could not find source routeur 1");
			return false;
		}
		else {
			Log.d(TAG, "Router for flow 1 is : " + rtrD1);
		}
		
		RouterDescriptor rtrD2 = this.getRouteurForIp(ipSrc2);
		if (rtrD2 == null) {
			Log.e(TAG, "Could not find source routeur 2");
			return false;
		}
		else {
			Log.d(TAG, "Router for flow 2 is : " + rtrD2);
		}

		//Check if routeur can accept the flow
		boolean flow1isAcceptable = this.rtrTable.get(rtrD1).isFlowAcceptable(flow1);
		boolean flow2isAcceptable = this.rtrTable.get(rtrD2).isFlowAcceptable(flow2);

		if (flow1isAcceptable && flow2isAcceptable) {
			this.rtrTable.get(rtrD1).acceptFlow(flow1);
			this.rtrTable.get(rtrD2).acceptFlow(flow2);
		}
		else {
			if (! flow1isAcceptable) {
				Log.w(TAG, "Could not accept flow 1");
			}
			if (! flow2isAcceptable) {
				Log.w(TAG, "Could not accept flow 2");
			}
			return false;
		}

		//Connect to routeur and exec script of reservation
//		TelnetRouterClient trc;
//		Log.d(TAG, "Connection telnet to : " + rtrD1.getRtrIp());
//		trc = new TelnetRouterClient(rtrD1.getRtrIp(), this.TELNETPORT, this.LOGGIN, this.PASS);
//		trc.sendCommand(nameScriptResa);
//		trc.disconnect();
//		Log.d(TAG, "Connection telnet to : " + rtrD2.getRtrIp());
//		trc = new TelnetRouterClient(rtrD2.getRtrIp(), this.TELNETPORT, this.LOGGIN, this.PASS);
//		trc.sendCommand(nameScriptResa);
//		trc.disconnect();
		String ipRtr;
		ipRtr = rtrD1.getRtrIp().getHostAddress();
		Log.d(TAG, "Connection ssh to : " + ipRtr);
		try {
			SSHRouterClient.sendCommand(ipRtr, this.SSHPORT, this.LOGIN, this.PASS, nameScriptResa);
		} catch (JSchException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "Could not execute commande");
		}
		ipRtr = rtrD2.getRtrIp().getHostAddress();
		Log.d(TAG, "Connection ssh to : " + ipRtr);
		try {
			SSHRouterClient.sendCommand(ipRtr, this.SSHPORT, this.LOGIN, this.PASS, nameScriptResa);
		} catch (JSchException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "Could not execute commande");
		}
		
		return true;
	}

	private String hashFlowForId(FlowDescriptor fd) {
        return DigestUtils.sha1Hex(fd.toString());
    }


	private RouterDescriptor getRouteurForIp(Inet4Address ipSrc) {
		// TODO Auto-generated method stub
		ListIterator<RouterDescriptor> lit = this.rtrDs.listIterator();

		RouterDescriptor curDescriptor = null;

		while (lit.hasNext()) {
			curDescriptor = lit.next();
			if (curDescriptor.isInRouterPrefix(ipSrc)) {
				return curDescriptor;
			}
		}

		return null;
	}

	public boolean processBye(Bye msg) {
		
      //Extract flow from reservation
		FlowDescriptor flow1 = msg.getFlow1();
		FlowDescriptor flow2 = msg.getFlow2();

	 //Identify Router in charge of the flow
		Inet4Address ipSrc1 = flow1.getIpSrc();
		Inet4Address ipSrc2 = flow2.getIpSrc();

		Log.d(TAG, "Flow 1 of bye is " + flow1);
	    Log.d(TAG, "Flow 2 of bye is " + flow2);
	    
	    
	    RouterDescriptor rtrD1 = this.getRouteurForIp(ipSrc1);
		if (rtrD1 == null) {
			Log.e(TAG, "Could not find source routeur 1");
			return false;
		}
		else {
			Log.d(TAG, "Router for flow 1 is : " + rtrD1);
		}
		
		RouterDescriptor rtrD2 = this.getRouteurForIp(ipSrc2);
		if (rtrD2 == null) {
			Log.e(TAG, "Could not find source routeur 2");
			return false;
		}
		else {
			Log.d(TAG, "Router for flow 2 is : " + rtrD2);
		}
		
		//Check if routeur can accept the flow
		boolean freeflow1 = this.rtrTable.get(rtrD1).freeFlowRSRV(flow1);
		boolean freeflow2 = this.rtrTable.get(rtrD2).freeFlowRSRV(flow2);
		
		if (! freeflow1) {
			Log.w(TAG, "Could not free flow 1");
		}
		if (! freeflow2) {
			Log.w(TAG, "Could not free flow 2");
		}
		
		
		return false;

	}

}
