import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import com.jcraft.jsch.JSchException;

import proxySide.ProxyServer;

import controller.Controller;

import log.Log;
import messages.FlowDescriptor;
import ressources.Ressources;
import routerconf.RouterDescriptor;
import routerconf.RouterRSRVTable;
import routerconf.SSHRouterClient;
import routerconf.TelnetRouterClient;

public class Main {
	public static String TAG = "Main";

	public static void main(String args[]){
		//testFlowRSRV();
		//testRessources();
		//TestRouteurDescriptorSubnetDetermination();

		Controller ctrlr = new Controller();
		ProxyServer ps = null;
		try {
			ps = new ProxyServer(ctrlr);
			ps.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		testSSH();
	}

	public static void testTelnet() {
		
		Inet4Address ipRtr;
		try {
			ipRtr = (Inet4Address) Inet4Address.getByName("localhost");

			TelnetRouterClient trc = new TelnetRouterClient(ipRtr,23, "rtr", "mdp");

			trc.sendCommand("ls");

			trc.disconnect();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testSSH() {
		try {
			Log.d(TAG, "Exec de la première fonction");
			SSHRouterClient.sendCommand("localhost", 22, "login", "mdp", "ls");
			Log.d(TAG,"Exec de la seconde fonction");
			SSHRouterClient.sendCommand("192.120.2.4", 22, "login", "mdp", "ls");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFlowRSRV() {
		try {
			byte [] ipRtr = new byte[] {(byte)127,(byte)0,(byte)0,(byte)1};
			byte [] idRtr = new byte[] {(byte)101,(byte)101,(byte)101,(byte)101};
			byte [] prefix = new byte[] {(byte)192,(byte)168,(byte)1,(byte)0};
			byte[] mask = {(byte) 255,(byte) 255,(byte) 255,0};
			float availRess = 100.0f;

			RouterDescriptor rtrD = new RouterDescriptor( 
					(Inet4Address)Inet4Address.getByAddress(idRtr),
					(Inet4Address)Inet4Address.getByAddress(ipRtr), 
					(Inet4Address)Inet4Address.getByAddress(prefix),mask,
					availRess);
			Log.d(TAG,"Created router descriptor " + rtrD);

			byte [] ipSrc = new byte[] {(byte)192,(byte)168,(byte)0,(byte)2};
			byte [] ipDst1 = new byte[] {(byte)192,(byte)168,(byte)1,(byte)3};
			byte [] ipDst2 = new byte[] {(byte)192,(byte)168,(byte)1,(byte)3};
			float transmRate = 64.0f;
			//Inet4Address ipSrc, Inet4Address ipDst, int pSrc,int pDst, float transmRate, int proto
			FlowDescriptor fd1 = new FlowDescriptor(
					(Inet4Address)Inet4Address.getByAddress(ipSrc),
					(Inet4Address)Inet4Address.getByAddress(ipDst1), 
					1340,
					1342,
					transmRate,
					"UDP");
			FlowDescriptor fd2 = new FlowDescriptor(
					(Inet4Address)Inet4Address.getByAddress(ipSrc),
					(Inet4Address)Inet4Address.getByAddress(ipDst2), 
					1345,
					1347,
					transmRate,
					"UDP");

			RouterRSRVTable rrsrvt = new RouterRSRVTable(rtrD.getMaxRess());

			boolean flow1Accepted = false;
			boolean flow2Accepted = false;
			if (rrsrvt.isFlowAcceptable(fd1)) {
				Log.d(TAG,"We can accept flow 1 " + fd1);
				if (rrsrvt.acceptFlow(fd1)) {
					Log.d(TAG,"Flow 1 " + fd1 + " accepted");
					Log.d(TAG,"Router available ressources is now " + rrsrvt.getAvailRess());
					flow1Accepted=true;
				}
				else {
					Log.e(TAG,"Flow 1 " + fd1 + " refused");					
				}
			}
			else {
				Log.e(TAG,"We cannot accept Flow 1 " + fd1);					
			}

			if (flow1Accepted && rrsrvt.isFlowAcceptable(fd2)) {
				Log.e(TAG,"We can accept flow 2 " + fd2);
				if (rrsrvt.acceptFlow(fd1)) {
					Log.e(TAG,"Flow 2 " + fd2 + " accepted");
					Log.d(TAG,"Router available ressources is now " + rrsrvt.getAvailRess());
					flow2Accepted=true;
				}
				else {
					Log.d(TAG,"Flow 2 " + fd2 + " refused");					
				}
			}
			else {
				Log.d(TAG,"We cannot accept Flow 2 " + fd2);	
			}

			if (flow1Accepted && !flow2Accepted) {
				if (rrsrvt.freeFlowRSRV(fd1)) {
					Log.d(TAG,"We could free Flow 1 " + fd1);	
					Log.d(TAG,"Router available ressources is now " + rrsrvt.getAvailRess());
					if (rrsrvt.isFlowAcceptable(fd2)) {
						Log.d(TAG,"We can accept flow 2 " + fd2);
						if (rrsrvt.acceptFlow(fd1)) {
							Log.d(TAG,"Flow 2 " + fd2 + " accepted");
							Log.d(TAG,"Router available ressources is now " + rrsrvt.getAvailRess());
							flow2Accepted=true;
						}
						else {
							Log.e(TAG,"Flow 2 " + fd2 + " refused");					
						}
					}
					else {
						Log.e(TAG,"We cannot accept Flow 2 " + fd2);	
					}
				}
				else {
					Log.e(TAG,"We could not free Flow 1 " + fd1);					
				}
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testRessources() {
		Ressources R = new Ressources();

		//regexp pour créer le test : 
		//cat ../rec/codec_rate.csv | 
		// sed 's/[.]/_/g' | 
		// sed 's/\([^,]*\)[,]\(.*\)/
		//				if (R.transmRate.get(\"\1\")==R.\1)\n
		//					Log.d(TAG,\"Retrieving \1 rate : " + R.transmRate.get(\"\1\") + " correct.");\n
		//				else\n
		//					Log.e(TAG,\"Retrieving \1 rate : incorrect.");

		if (R.transmRate.get("G_711")==R.G_711)
			Log.d(TAG,"Retrieving G_711 rate : " + R.transmRate.get("G_711") + " correct.");
		else
			Log.e(TAG,"Retrieving G_711 rate : incorrect."); 
		if (R.transmRate.get("G_729")==R.G_729)
			Log.d(TAG,"Retrieving G_729 rate : " + R.transmRate.get("G_729") + " correct.");
		else
			Log.e(TAG,"Retrieving G_729 rate : incorrect."); 
		if (R.transmRate.get("G_723_1a")==R.G_723_1a)
			Log.d(TAG,"Retrieving G_723_1a rate : " + R.transmRate.get("G_723_1a") + " correct.");
		else
			Log.e(TAG,"Retrieving G_723_1a rate : incorrect."); 
		if (R.transmRate.get("G_723_1b")==R.G_723_1b)
			Log.d(TAG,"Retrieving G_723_1b rate : " + R.transmRate.get("G_723_1b") + " correct.");
		else
			Log.e(TAG,"Retrieving G_723_1b rate : incorrect."); 
		if (R.transmRate.get("G_726_a")==R.G_726_a)
			Log.d(TAG,"Retrieving G_726_a rate : " + R.transmRate.get("G_726_a") + " correct.");
		else
			Log.e(TAG,"Retrieving G_726_a rate : incorrect."); 
		if (R.transmRate.get("G_726_b")==R.G_726_b)
			Log.d(TAG,"Retrieving G_726_b rate : " + R.transmRate.get("G_726_b") + " correct.");
		else
			Log.e(TAG,"Retrieving G_726_b rate : incorrect."); 
		if (R.transmRate.get("G_728")==R.G_728)
			Log.d(TAG,"Retrieving G_728 rate : " + R.transmRate.get("G_728") + " correct.");
		else
			Log.e(TAG,"Retrieving G_728 rate : incorrect."); 
		if (R.transmRate.get("G_722")==R.G_722)
			Log.d(TAG,"Retrieving G_722 rate : " + R.transmRate.get("G_722") + " correct.");
		else
			Log.e(TAG,"Retrieving G_722 rate : incorrect."); 
		if (R.transmRate.get("ilbc_mode_2")==R.ilbc_mode_2)
			Log.d(TAG,"Retrieving ilbc_mode_2 rate : " + R.transmRate.get("ilbc_mode_2") + " correct.");
		else
			Log.e(TAG,"Retrieving ilbc_mode_2 rate : incorrect."); 
		if (R.transmRate.get("ilbc_mode_3")==R.ilbc_mode_3)
			Log.d(TAG,"Retrieving ilbc_mode_3 rate : " + R.transmRate.get("ilbc_mode_3") + " correct.");
		else
			Log.e(TAG,"Retrieving ilbc_mode_3 rate : incorrect.");

	}

	public static void TestRouteurDescriptorSubnetDetermination() {
		try{
			byte [] ipRtr = new byte[] {(byte)192,(byte)120,(byte)2,(byte)3};
			byte [] idRtr = new byte[] {(byte)101,(byte)101,(byte)101,(byte)101};
			byte [] prefix = new byte[] {(byte)192,(byte)168,(byte)1,(byte)0};

			byte[] mask = {(byte) 255,(byte) 255,(byte) 255,0};
			float availRess = 100.0f;

			RouterDescriptor rtrD = new RouterDescriptor( 
					(Inet4Address)Inet4Address.getByAddress(idRtr),
					(Inet4Address)Inet4Address.getByAddress(ipRtr), 
					(Inet4Address)Inet4Address.getByAddress(prefix),mask,
					availRess);
			Log.d(TAG,"Created router descriptor " + rtrD);

			byte [] add1B = new byte[] {(byte)192,(byte)168,(byte)1,(byte)1};
			byte [] add2B = new byte[] {(byte)192,(byte)168,(byte)1,(byte)2};
			byte [] add3B = new byte[] {(byte)191,(byte)168,(byte)1,(byte)2};
			byte [] add4B = new byte[] {(byte)192,(byte)168,(byte)1,(byte)255};

			Inet4Address add1 = (Inet4Address)Inet4Address.getByAddress(add1B);
			Inet4Address add2 = (Inet4Address)Inet4Address.getByAddress(add2B);
			Inet4Address add3 = (Inet4Address)Inet4Address.getByAddress(add3B);
			Inet4Address add4 = (Inet4Address)Inet4Address.getByAddress(add4B);


			if (rtrD.isInRouterPrefix(add1)) {
				Log.d(TAG, "IP:" + add1 + " is in subnet " + rtrD.getPrefix());
			}
			else {
				Log.e(TAG, "IP:" + add1 + " is not in subnet " + rtrD.getPrefix());				
			}

			if (rtrD.isInRouterPrefix(add2)) {
				Log.d(TAG, "IP:" + add2 + " is in subnet " + rtrD.getPrefix());
			}
			else {
				Log.e(TAG, "IP:" + add2 + " is not in subnet " + rtrD.getPrefix());				
			}

			if (! rtrD.isInRouterPrefix(add3)) {
				Log.d(TAG, "IP:" + add3 + " is not in subnet " + rtrD.getPrefix());
			}
			else {
				Log.e(TAG, "IP:" + add3 + " is in subnet " + rtrD.getPrefix());				
			}

			if (rtrD.isInRouterPrefix(add4)) {
				Log.d(TAG, "IP:" + add4 + " is in subnet " + rtrD.getPrefix());
			}
			else {
				Log.e(TAG, "IP:" + add4 + " is not in subnet " + rtrD.getPrefix());				
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
