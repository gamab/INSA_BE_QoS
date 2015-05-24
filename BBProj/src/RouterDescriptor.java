import java.net.Inet4Address;


public class RouterDescriptor {
	private Inet4Address rtrId;
	private Inet4Address rtrIp;
	private Inet4Address prefix;
	private int mask;
	private float availRess;
		
	public RouterDescriptor() {
		super();
	}

	public RouterDescriptor(Inet4Address rtrId, Inet4Address rtrIp,
			Inet4Address prefix, int mask, float availRess) {
		super();
		this.rtrId = rtrId;
		this.rtrIp = rtrIp;
		this.prefix = prefix;
		this.mask = mask;
		this.availRess = availRess;
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
	public float getAvailRess() {
		return availRess;
	}
	public void setAvailRess(float availRess) {
		this.availRess = availRess;
	}
	public int getMask() {
		return mask;
	}
	public void setMask(int mask) {
		this.mask = mask;
	}
}
