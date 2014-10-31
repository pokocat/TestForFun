package ssh;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;
import org.apache.commons.net.telnet.TelnetClient;

public class TelnetTest {

	public static void main(String[] args) throws IOException {
		TelnetClient tc = new TelnetClient();

		EchoOptionHandler echoopt = new EchoOptionHandler(true, true, true, true);
		try {
			tc.addOptionHandler(echoopt);
		} catch (InvalidTelnetOptionException e) {
			throw new SocketException("Invalid telnet option!");
		}

		tc.connect("127.0.0.1", 12667);
		System.out.println(tc.getTcpNoDelay());
		tc.setTcpNoDelay(true);
		try {
			long begin = System.currentTimeMillis();
			for (int i = 0; i < 100; i++) {
				long begin0 = System.currentTimeMillis();
//				tc.sendAYT(1000);
				System.out.println("Are you there:"+tc.sendAYT(1000)+", RTT : " + (System.currentTimeMillis() - begin0));
			}
			long end = System.currentTimeMillis();
			System.out.println((end - begin) + " : " + (end - begin) / 100);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// tc.setSoTimeout(new Long(MML_TIMEOUT).intValue());
	}

}
