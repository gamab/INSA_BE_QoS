import java.net.Inet4Address;
import java.net.UnknownHostException;

import log.Log;

import routerconf.RouterDescriptor;
import routerconf.RouterRSRVTable;
import routerconf.TelnetRouterClient;
import flow.FlowDescriptor;

public class Main {
	public static String TAG = "Main";

	public static void main(String args[]){
		testFlowRSRV();
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

	public static void testFlowRSRV() {
		try {
			byte [] ipRtr = new byte[] {(byte)127,(byte)0,(byte)0,(byte)1};
			byte [] idRtr = new byte[] {(byte)101,(byte)101,(byte)101,(byte)101};
			byte [] prefix = new byte[] {(byte)192,(byte)168,(byte)1,(byte)0};
			int mask = 24;
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
					0x80);
			FlowDescriptor fd2 = new FlowDescriptor(
					(Inet4Address)Inet4Address.getByAddress(ipSrc),
					(Inet4Address)Inet4Address.getByAddress(ipDst2), 
					1345,
					1347,
					transmRate,
					0x80);

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


}
