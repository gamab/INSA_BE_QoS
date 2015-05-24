package routerconf;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Inet4Address;

import org.apache.commons.net.telnet.TelnetClient;

/*
 * Grande partie de ce code est issue de 
 * http://www.developpez.net/forums/anocode.php?id=8463d8ff36ce9d84411b6b6a9446feaa
 */

public class TelnetRouterClient {
	private TelnetClient telnet;
	private InputStream is;
	private PrintStream ps;
	private static final String prompt = "$";

	public TelnetRouterClient( Inet4Address ipRtr, int port, String user, String password ) {
		telnet = new TelnetClient();
		try {
			telnet.connect(ipRtr, port);

			is = telnet.getInputStream();
			ps = new PrintStream(telnet.getOutputStream());

			// Log the user on
			readUntil( "login: " );
			write( user );
			readUntil( "Password: " );
			write( password );

			// Advance to a prompt
			readUntil( prompt + "" );

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String readUntil( String pattern ) {
		try {
			char lastChar = pattern.charAt(pattern.length() - 1);
			StringBuffer sb = new StringBuffer();
			@SuppressWarnings("unused")
			boolean found = false;
			char ch = ( char )is.read();

			while( true ) {

				System.out.print( ch );
				sb.append( ch );
				if( ch == lastChar ) {
					if( sb.toString().endsWith( pattern ) ) {
						return sb.toString();
					}
				}
				ch = ( char )is.read();
			}


		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		return null;
	}

	public void write( String value ) {
		try {
			ps.println( value );
			ps.flush();
			System.out.println( value );
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	public void sendCommand( String command ) {
		try {
			write( command );
			//String ok ="Ok !!";
			readUntil( prompt +"" );
			//return ok;
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			telnet.disconnect();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

}
