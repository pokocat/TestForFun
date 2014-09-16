package weixin;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpHost;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.http.conn.params.ConnRoutePNames;

public class Weixin {
	static {
		Protocol myhttps = new Protocol("https", new MySecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
	}

	public static void main(String[] args) throws Exception {
		System.setProperty("http.proxyHost", "www-proxy.exu.ericsson.se");
		System.setProperty("http.proxyPort", "8080");
		
		HttpHost proxy = new HttpHost("cnbjip.mgmt.ericsson.se", 8080);

		// 创造httpclient实例
		HttpClient client = new HttpClient();
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY); // 设置cookie管理策略
		client.getParams().setParameter("http.protocol.single-cookie-header", true);
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

		PostMethod post = new PostMethod();
		// 模拟浏览器
		post.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64)"
				+ " AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 " + "Safari/537.22");
		post.setRequestHeader("Referer", "https://mp.weixin.qq.com/");

		// 登录请求提交地址
		post.setURI(new URI("https://mp.weixin.qq.com/cgi-bin/login?lang=zh_CN"));

		// 构造请求参数
		NameValuePair[] params = new NameValuePair[] { new NameValuePair("username", "eripark"),
				new NameValuePair("pwd", DigestUtils.md5Hex("avonllet2013".getBytes())),
				new NameValuePair("f", "json"), new NameValuePair("imagecode", "") };
		post.setQueryString(params);
		client.executeMethod(post);
		System.out.println(post.getResponseBodyAsString());
	}
}
