package messages;
import java.io.Serializable;
import java.net.Inet4Address;

import log.Log;


public class FlowDescriptor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final String TAG = "FlowDescriptor";
	
	private Inet4Address ipSrc;
	private Inet4Address ipDst;
	private int pSrc;
	private int pDst;
	private float transmRate;
	private String proto;
		
	public FlowDescriptor(Inet4Address ipSrc, Inet4Address ipDst, int pSrc,
			int pDst, float transmRate, String proto) {
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
	public String getProto() {
		return proto;
	}
	public void setProto(String proto) {
		this.proto = proto;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof FlowDescriptor)
			return equals((FlowDescriptor) o);
		else
			return false;
	}
	
	public boolean equals(FlowDescriptor fd) {
		Log.d(TAG,"Checks if  " + this.toString() + " equals " + fd.toString());
		//compare all the fields of the flow descriptor
		if (!this.ipSrc.equals(fd.getIpSrc())) {
			Log.w(TAG,"Not equal ipsrc");
			return false;
		}
		if (!this.ipDst.equals(fd.getIpDst())) {
			Log.w(TAG,"Not equal ipdst");
			return false;
		}
		if (!(this.pSrc == fd.getpSrc())) {
			Log.w(TAG,"Not equal psrc");
			return false;
		}
		if (!(this.pDst == fd.getpDst())) {
			Log.w(TAG,"Not equal pdst");
			return false;
		}
		if (!(this.transmRate == fd.getTransmRate())) {
			Log.w(TAG,"Not equal transmR");
			return false;			
		}
		if (!(this.proto.equals(fd.getProto()))) {
			Log.w(TAG,"Not equal proto");
			return false;
		}
		Log.d(TAG,"Equal");
		//if everything was equal then return true
		return true;
	}
	
	public String toString() {
		return "{[" + ipSrc.toString() + ":" + pSrc + "->" +
				ipDst.toString() + ":" + pDst + "]" +
				" : trsmRate=" + transmRate + " proto " + proto + "}";
	}
}
