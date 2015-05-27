package ressources;

import java.util.HashMap;

/*
 * This class contains all constant data we will use
 * such as the throughput of each codec
 */
public class Ressources {

	/**
	 * Bandwith of codecs on Ethernet in kbps
	 * http://www.cisco.com/c/en/us/support/docs/voice/voice-quality/7934-bwidth-consume.html
	 * Regexp pour parser un CSV avec les deux colonnes : 
	 * cat codec_rate.csv | sed 's/[.]/_/g' | sed 's/\([^,]*\)[,]\(.*\)/public static final float \1 = \2f;/g'
	 */	
	public static final float G_711 = 87.2f;
	public static final float G_729 = 31.2f;
	public static final float G_723_1a = 21.9f;
	public static final float G_723_1b = 20.8f;
	public static final float G_726_a = 55.2f;
	public static final float G_726_b = 47.2f;
	public static final float G_728 = 31.5f;
	public static final float G_722 = 87.2f;
	public static final float ilbc_mode_2 = 38.4f;
	public static final float ilbc_mode_3 = 28.8f;

	/**
	 * Mapping a codecs name to a transmission rate
	 */
	public static HashMap<String,Float> transmRate;

	/**
	 * Constructor of the ressources
	 */
	public Ressources() {
		transmRate = new HashMap<String,Float>();
		transmRate.put("G_711", G_711);
		transmRate.put("G_729", G_729);
		transmRate.put("G_723_1a", G_723_1a);
		transmRate.put("G_723_1b", G_723_1b);
		transmRate.put("G_726_a", G_726_a);
		transmRate.put("G_726_b", G_726_b);
		transmRate.put("G_728", G_728);
		transmRate.put("G_722", G_722);
		transmRate.put("ilbc_mode_2", ilbc_mode_2);
		transmRate.put("ilbc_mode_3", ilbc_mode_3);
	}
}
