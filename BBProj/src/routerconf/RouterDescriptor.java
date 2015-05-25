package routerconf;
import java.net.Inet4Address;

import log.Log;



public class RouterDescriptor {
	private static final String TAG = "RouterDescriptor";

	private Inet4Address rtrId; //the routeur id
	private Inet4Address rtrIp; //the routeur ip address
	private Inet4Address prefix;//the prefix of the subnet of this routeur
	private int mask; 			//the mask associated to the prefix
	private float maxRess; 		//the maximum rate the router can reserve

	public RouterDescriptor(Inet4Address rtrId, Inet4Address rtrIp,
			Inet4Address prefix, int mask, float maxRess) {
		super();
		this.rtrId = rtrId;
		this.rtrIp = rtrIp;
		this.prefix = prefix;
		this.mask = mask;
		this.maxRess = maxRess;
	}	

	public Inet4Address getRtrId() {
		return rtrId;
	}
	public void setRtrId(Inet4Address rtrId) {
		this.rtrId = rtrId;
	}
	public Inet4Address getRtrIp() {
		return rtrIp;
	}
	public void setRtrIp(Inet4Address rtrIp) {
		this.rtrIp = rtrIp;
	}
	public Inet4Address getPrefix() {
		return prefix;
	}
	public void setPrefix(Inet4Address prefix) {
		this.prefix = prefix;
	}
	public float getMaxRess() {
		return maxRess;
	}
	public int getMask() {
		return mask;
	}
	public void setMask(int mask) {
		this.mask = mask;
	}
	public String toString() {
		return "{[rtr:" + rtrId.toString() + "]" +
				"[@" + rtrIp.toString() + "]" +
				"[prefix="+prefix.toString()+"/"+mask+"]" +
				"[MaxRess=" + maxRess + "]}";		
	}


}
