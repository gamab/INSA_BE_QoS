import java.net.Inet4Address;
import java.net.UnknownHostException;

import routerconf.TelnetRouterClient;

public class Main {

	public static void main(String args[]){
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



}
