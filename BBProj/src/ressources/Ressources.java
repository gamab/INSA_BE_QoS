package ressources;

/*
 * This class contains all constant data we will use
 * such as the throughput of each codec
 */
public class Ressources {
	
	/*
	 * Bandwith of codecs on Ethernet in kbps
	 * http://www.cisco.com/c/en/us/support/docs/voice/voice-quality/7934-bwidth-consume.html
	 * Regexp pour parser un CSV avec les deux colonnes : 
	 * cat codec_rate.csv | sed 's/[.]/_/g' | sed 's/\([^,]*\)[,]\(.*\)/public static final float \1 = \2f;/g'
	 */	
	public static final float G_711 = 87_2f;
	public static final float G_729 = 31_2f;
	public static final float G_723_1a = 21_9f;
	public static final float G_723_1b = 20_8f;
	public static final float G_726_a = 55_2f;
	public static final float G_726_b = 47_2f;
	public static final float G_728 = 31_5f;
	public static final float G722 = 87_2f;
	public static final float ilbc_mode_2 = 38_4f;
	public static final float ilbc_mode_3 = 28_8f;

}
