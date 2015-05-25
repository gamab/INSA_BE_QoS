package log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static String time() {
		long millis = System.currentTimeMillis() % 1000;
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(cal.getTime()) + ":" + millis;
	}

	public static void d(String TAG, String log) {
		System.out.println(ANSI_GREEN + "(DEBUG)%" + time() + "% - " 
				+ TAG + " : " + log + ANSI_RESET);
	}

	public static void e(String TAG, String log) {
		System.out.println(ANSI_RED + "(ERROR)%" + time() + "% - " 
				+ TAG + " : " + log + ANSI_RESET);
	}

	public static void w(String TAG, String log) {
		System.out.println(ANSI_BLUE + "(WARNING)%" + time() + "% - " 
				+ TAG + " : " + log + ANSI_RESET);
	}
}
