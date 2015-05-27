package ressources;
import java.net.Inet4Address;


public class FlowDescriptor {
	private Inet4Address ipSrc;
	private Inet4Address ipDst;
	private int pSrc;
	private int pDst;
	private float transmRate;
	private int proto;
		
	public FlowDescriptor(Inet4Address ipSrc, Inet4Address ipDst, int pSrc,
			int pDst, float transmRate, int proto) {
		super();
		this.ipSrc = ipSrc;
		this.ipDst = ipDst;
		this.pSrc = pSrc;
		this.pDst = pDst;
		this.transmRate = transmRate;
		this.proto = proto;
	}

	public Inet4Address getIpSrc() {
		return ipSrc;
	}
	public void setIpSrc(Inet4Address ipSrc) {
		this.ipSrc = ipSrc;
	}
	public Inet4Address getIpDst() {
		return ipDst;
	}
	public void setIpDst(Inet4Address ipDst) {
		this.ipDst = ipDst;
	}
	public int getpSrc() {
		return pSrc;
	}
	public void setpSrc(int pSrc) {
		this.pSrc = pSrc;
	}
	public int getpDst() {
		return pDst;
	}
	public void setpDst(int pDst) {
		this.pDst = pDst;
	}
	public float getTransmRate() {
		return transmRate;
	}
	public void setTransmRate(float transmRate) {
		this.transmRate = transmRate;
	}
	public int getProto() {
		return proto;
	}
	public void setProto(int proto) {
		this.proto = proto;
	}
	
	public boolean equals(FlowDescriptor fd) {
		//compare all the fields of the flow descriptor
		if (!this.ipSrc.equals(fd.getIpSrc())) {
			return false;
		}
		if (!this.ipDst.equals(fd.getIpDst())) {
			return false;
		}
		if (!(this.pSrc == fd.getpSrc())) {
			return false;
		}
		if (!(this.pDst == fd.getpDst())) {
			return false;
		}
		if (!(this.transmRate == fd.getTransmRate())) {
			return false;			
		}
		if (!(this.proto == fd.getProto())) {
			return false;
		}
		//if everything was equal then return true
		return true;
	}
	
	public String toString() {
		return "{[" + ipSrc.toString() + ":" + pSrc + "->" +
				ipDst.toString() + ":" + pDst + "]" +
				" : trsmRate=" + transmRate + " proto " + proto + "}";
	}
}
