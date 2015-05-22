
public class Log {

	public static void d(String TAG, String log) {
		System.out.println("(DEBUG) " + TAG + " : " + log);
	}
	
	public static void e(String TAG, String log) {
		System.out.println("(ERROR) " + TAG + " : " + log);
	}
}
