import java.net.Inet4Address;


public class FlowDescription {
	private Inet4Address ipSrc;
	private Inet4Address ipDst;
	private int pSrc;
	private int pDst;
	private float debit;
	private int proto;
		
	public FlowDescription(Inet4Address ipSrc, Inet4Address ipDst, int pSrc,
			int pDst, float debit, int proto) {
		super();
		this.ipSrc = ipSrc;
		this.ipDst = ipDst;
		this.pSrc = pSrc;
		this.pDst = pDst;
		this.debit = debit;
		this.proto = proto;
	}
	
	public FlowDescription() {
		super();
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
	public float getDebit() {
		return debit;
	}
	public void setDebit(float debit) {
		this.debit = debit;
	}
	public int getProto() {
		return proto;
	}
	public void setProto(int proto) {
		this.proto = proto;
	}
	
	
	
}
