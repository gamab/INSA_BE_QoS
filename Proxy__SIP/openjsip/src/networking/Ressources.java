package networking;
import java.util.HashMap;

public class Ressources {/**
	 * Bandwith of codecs on Ethernet in kbps
	 * http://www.cisco.com/c/en/us/support/docs/voice/voice-quality/7934-bwidth-consume.html
	 * Regexp pour parser un CSV avec les deux colonnes : 
	 * cat codec_rate.csv | sed 's/[.]/_/g' | sed 's/\([^,]*\)[,]\(.*\)/public static final float \1 = \2f;/g'
	 */	
	public static final float G_711 = 80.0f;
	public static final float G_722 = 87.2f;
	public static final float G_728 = 31.5f;
	public static final float G_729 = 32.0f;
	public static final float G_723_1a = 21.0f;
	public static final float G_723_1b = 15.0f;
	public static final float G_726_a = 55.0f;	
	public static final float ilbc = 32.0f;
	public static final float gsm = 13.0f;
	public static final float speex = 8.0f;

	/**
	 * Mapping a codecs name to a transmission rate
	 */
	public static HashMap<String,Float> transmRate;

	/**
	 * Constructor of the ressources
	 */
	public Ressources() {
		transmRate = new HashMap<String,Float>();
		transmRate.put("PCMU", G_711);
		transmRate.put("PCMA", G_711);
		transmRate.put("CSA-CELP", G_729);
		transmRate.put("MP-MLQ", G_723_1a);
		transmRate.put("A-CELP", G_723_1b);
		transmRate.put("AD-PCM", G_726_a);
		transmRate.put("iLBC", ilbc);
		transmRate.put("GSM", gsm);
		transmRate.put("SpeeX", speex);
	}
	
	public float getTransmission (String codec) {
		if (codec.equals("PCMU")) {
			return G_711;
		}
		else if (codec.equals("PCMA")) {
			return G_711;
		}
		else if (codec.equals("CSA-CELP")) {
			return G_729;
		}
		else if (codec.equals("MP-MLQ")) {
			return G_723_1a;
		}
		else if (codec.equals("A-CELP")) {
			return G_723_1b;
		}
		else if (codec.equals("AD-PCM")) {
			return G_726_a;
		}
		else if (codec.equals("iLBC")) {
			return ilbc;
		}
		else if (codec.equals("GSM")) {
			return gsm;
		}
		else if (codec.equals("SpeeX")) {
			return speex;
		}
		else if (codec.equals("G722")) {
			return G_722;
		}
		else if (codec.equals("G728")) {
			return G_728;
		}
		else if (codec.equals("G711")) {
			return G_711;
		}
		else if (codec.equals("G729")) {
			return G_729;
		}
		else if (codec.equals("G726")) {
			return G_726_a;
		}
		else if (codec.equals("G723")) {
			return G_723_1a;
		}
		else {
			System.out.println("CAREFULL DID NOT FIND THE CODEC");
			return G_711;
		}
		
	}
}