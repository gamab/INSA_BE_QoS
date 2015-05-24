package routerconf;
import java.net.Inet4Address;

import Log.Log;


public class RouterDescriptor {
	private static final String TAG = "RouterDescriptor";

	private Inet4Address rtrId; //the routeur id
	private Inet4Address rtrIp; //the routeur ip address
	private Inet4Address prefix;//the prefix of the subnet of this routeur
	private int mask; 			//the mask associated to the prefix
	private float maxRess; 		//the maximum rate the router can reserve
	private float availRess; 	//the bandwith that is currently unused and could be reserved

	public RouterDescriptor(Inet4Address rtrId, Inet4Address rtrIp,
			Inet4Address prefix, int mask, float maxRess) {
		super();
		this.rtrId = rtrId;
		this.rtrIp = rtrIp;
		this.prefix = prefix;
		this.mask = mask;
		this.maxRess = maxRess;
		this.availRess = maxRess;
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

	public float getAvailRess() {
		return availRess;
	}

	public boolean addToAvailRess(float freedRess) {
		if (availRess + freedRess <= maxRess) {
			this.availRess += freedRess;
			return true;
		}
		else {
			Log.e(TAG,"Could not add " + freedRess 
					+ " to available ressources it is greater than the maximum !");
			return false;
		}
	}

	public boolean subFromAvailRess(float neededRess) {
		if (availRess - neededRess >= 0) {
			this.availRess -= neededRess;
			return true;
		}
		else {
			Log.e(TAG,"Could not sub " + neededRess 
					+ " from available ressources it is lower than 0 !");
			return false;
		}
	}

	public boolean isAvailRessEnough(float neededRess) {
		return availRess - neededRess >= 0;
	}

	public String toString() {
		return "{[rtr:" + rtrId.toString() + "]" +
				"[@" + rtrIp.toString() + "]" +
				"[prefix="+prefix.toString()+"/"+mask+"]" +
				"[MaxRess=" + maxRess + "|AvailRess=" + availRess +"]}";		
	}


}
