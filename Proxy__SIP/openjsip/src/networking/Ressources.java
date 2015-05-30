package networking;
import java.util.HashMap;

public class Ressources {/**
	 * Bandwith of codecs on Ethernet in kbps
	 * http://www.cisco.com/c/en/us/support/docs/voice/voice-quality/7934-bwidth-consume.html
	 * Regexp pour parser un CSV avec les deux colonnes : 
	 * cat codec_rate.csv | sed 's/[.]/_/g' | sed 's/\([^,]*\)[,]\(.*\)/public static final float \1 = \2f;/g'
	 */	
	public static final float G_711 = 80.0f;
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
}
