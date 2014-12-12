package ssh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.SocketException;

import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.log4j.Logger;

public class TelnetTest {
	private static Logger logger = Logger.getLogger(TelnetTest.class);
	private static final int TIMEOUT_SECONDS = 30;
	private static final String DEFAULT_PROMPT_PREFIX = "\03";
	private static String newLine = "\n";

	public static void main(String[] args) throws IOException {

		TelnetClient tc = new TelnetClient();

		EchoOptionHandler echoopt = new EchoOptionHandler(true, true, true, true);
		try {
			tc.addOptionHandler(echoopt);
		} catch (InvalidTelnetOptionException e) {
			throw new SocketException("Invalid telnet option!");
		}

//		tc.connect("tp201ap1.axe.k2.ericsson.se", 23);
		 tc.connect("192.168.217.129", 23);

		tc.setTcpNoDelay(false);
		System.out.println("tcp no delay: " + tc.getTcpNoDelay());
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(tc.getOutputStream()));
		BufferedReader br = new BufferedReader(new InputStreamReader(tc.getInputStream()));



		// bw.write("status");
		String output = expect("ogin:", br);
		logger.info("login:" + output);
		bw.write("donis" + newLine);
		bw.flush();
		output = expect("assword:", br);
		logger.info("pwd:" + output);
		bw.write("bailey" + newLine);
		bw.flush();
		logger.info(expect("$", br));
		try {
			long begin = System.currentTimeMillis();
			int times = 100; 
			for (int i = 0; i < times; i++) {
				long begin0 = System.currentTimeMillis();
				// System.out.println("Are you there:" + tc.sendAYT(1000) + "RTT : "
				// + (System.currentTimeMillis() - begin0));
				bw.write("ifconfig" + newLine);
				bw.flush();
				output =expect("$", br);
				logger.info("Are you there:" +output+ "RTT : " + (System.currentTimeMillis() - begin0));
			}
			long end = System.currentTimeMillis();
			logger.info("total: "+ (end - begin)  + " / " + (end - begin) / times);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} 
	}

	protected static String expect(String str, BufferedReader br) throws IOException {
		StringBuffer sb = new StringBuffer();

		String[] prompts = str.split("\\|");
		long timeStamp = System.currentTimeMillis();

		while (br.ready() || !checkTimeout(timeStamp, TIMEOUT_SECONDS)) {
			if (br.ready()) {
				sb.append((char) br.read());
//				for (String prompt : prompts) {
//					if (sb.toString().endsWith(str.contains("$") ? prompt : prompt)) {
//						if (logger.isTraceEnabled()) {
//							logger.trace("Found '" + str + "' in [" + sb.toString() + "]");
//						}
//						return sb.toString();
//					}
//				}
				if (sb.toString().endsWith(prompts[0])) {
						if (logger.isTraceEnabled()) {
							logger.trace("Found '" + str + "' in [" + sb.toString() + "]");
						}
						return sb.toString();
					}
				
			}
		}
		logger.warn("Not found '" + str + "' in " + sb.toString() + " after " + TIMEOUT_SECONDS
				+ " seconds. Return raw output which may be uncorrect, please check!");
		return sb.toString();
	}

	protected static boolean checkTimeout(long beginTime, int timeout) {
		long timeoutInMilliseconds = timeout * 1000;
		if (System.currentTimeMillis() - beginTime < timeoutInMilliseconds) {
			return false;
		}
		return true;
	}

}
