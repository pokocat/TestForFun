package ssh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.SocketException;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshTest {
	private static final Logger logger = Logger.getLogger(SshTest.class);
	private static Session sshSession;
	private static Channel sshChannel;

	public static void main(String[] args) throws IOException {
		JSch jsch = new JSch();
		try {
			sshSession = jsch.getSession("tsadmin", "tp204ap1.axe.k2.ericsson.se", 4422);
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			throw new SocketException(e.getMessage());
		}

		sshSession.setPassword("Sommar.2013");
		sshSession.setConfig("StrictHostKeyChecking", "no");
		try {
			sshSession.connect();

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			throw new SocketException(e.getMessage());
		}

		try {
			sshChannel = sshSession.openChannel("shell");
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			throw new SocketException(e.getMessage());
		}

		((ChannelShell) sshChannel).setPtyType("vt100");

		BufferedReader br = new BufferedReader(new InputStreamReader(sshChannel.getInputStream()));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sshChannel.getOutputStream()));

		try {
			sshChannel.connect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			throw new SocketException(e.getMessage());
		}

		StringBuffer sb = new StringBuffer();

		String str = "*";
		long timeStamp = System.currentTimeMillis();

		String[] prompts = str.split("\\|");
		char c = '>';
		char ch = '0';
		while (br.ready() || (System.currentTimeMillis() - timeStamp) < 300000) {
			
				ch = (char) br.read();
				sb.append(ch);
			

			logger.info("checking :::" + sb.toString());
			if (ch == c) {
				logger.info("found ::::" + c + " in " + ch);
			}
			logger.warn("Not found '" + c + "' in " + ch);
		}

		sshChannel.disconnect();
		sshSession.disconnect();
		br.close();
		bw.close();
	}

}
