import java.net.Inet4Address;


public class RouterDescription {
	private Inet4Address rtrId;
	private Inet4Address rtrIp;
	private Inet4Address prefix;
	private float bpGold;
		
	public RouterDescription() {
		super();
	}

	public RouterDescription(Inet4Address rtrId, Inet4Address rtrIp,
			Inet4Address prefix, float bpGold) {
		super();
		this.rtrId = rtrId;
		this.rtrIp = rtrIp;
		this.prefix = prefix;
		this.bpGold = bpGold;
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
	public float getBpGold() {
		return bpGold;
	}
	public void setBpGold(float bpGold) {
		this.bpGold = bpGold;
	}
	
	
}
