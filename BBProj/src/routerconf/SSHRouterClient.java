package routerconf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


/*
 * This code is basically from :
 * http://stackoverflow.com/questions/16468475/sending-commands-to-remote-server-through-ssh-by-java-with-jsch
 */

public class SSHRouterClient {

	public static void sendCommand( String ipRtr, int port, String user, String password, String command ) throws JSchException, IOException {
		JSch jsch=new JSch();
		Session session=jsch.getSession(user, ipRtr, port);
		session.setPassword(password);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();

		ChannelExec channel=(ChannelExec) session.openChannel("exec");
		BufferedReader in=new BufferedReader(new InputStreamReader(channel.getInputStream()));
		channel.setCommand(command);
		channel.connect();

		String msg=null;
		while((msg=in.readLine())!=null){
		  System.out.println(msg);
		}

		channel.disconnect();
		session.disconnect();
	}

}
