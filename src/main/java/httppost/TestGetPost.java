package httppost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestGetPost {
	public static String sendGet(String url, String param, String ip) {
		String result = "";
		BufferedReader in = null;
		System.out.println("Connecting at ::::" + ip);
		try {
			String urlName = url + "?" + param;
			URL realUrl = new URL(urlName);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36");
			conn.setRequestProperty("CLIENT-IP", ip);
			conn.setRequestProperty("X-FORWARDED-FOR", ip);

			conn.connect();
			Map<String, List<String>> map = conn.getHeaderFields();
			String header = "";
			for (String key : map.keySet()) {
				header += key + "\t--->" + map.get(key) + "\n";
			}
			System.out.println("===============HEADER================\n" + header);
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "/n" + line + "\t";
			}
		} catch (Exception e) {
			System.out.println("GET:\t" + e);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);

			URLConnection conn = realUrl.openConnection();

			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36");
			conn.setRequestProperty("CLIENT-IP", "202.103.229.111");
			conn.setRequestProperty("X-FORWARDED-FOR", "202.103.229.111");
			// POST
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// URLConnection
			out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			// flush
			out.flush();
			// BufferedReaderdURL
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "/n" + line;
			}
		} catch (Exception e) {
			System.out.println("POST" + e);
			e.printStackTrace();
		}
		// finally
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	private static String ipGenerator() {
		Random rand = new Random();
		int ip1 = 167;
		int ip2 = 216;
		int ip3 = rand.nextInt(255) + 1;
		int ip4 = rand.nextInt(255) + 1;
		return ip1 + "." + ip2 + "." + ip3 + "." + ip4;
	}

	public static void main(String args[]) {
		// PROXY
		System.setProperty("http.proxyHost", "www-proxy.exu.ericsson.se");
		System.setProperty("http.proxyPort", "8080");
		// GET

		for (int i = 0; i < 800; i++) {
			String s = TestGetPost.sendGet("http://ck101.com/?fromuid=1435479", null, ipGenerator());
			// System.out.println("===============RESPONSE================\n" + s);
			try {
				long time = new Random().nextInt(10000) + 200;
				System.out.println("Sleep for " + time + " now.......");
				TimeUnit.MILLISECONDS.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("===============Total connection: " + (i + 1) + "================\n");

		}

		// POST
		// String s1 = TestGetPost.sendPost("http://posttestserver.com/post.php", "user=&pass=abc");
		// System.out.println(s1);
	}
}
