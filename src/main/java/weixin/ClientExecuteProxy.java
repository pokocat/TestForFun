package weixin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * How to send a request via proxy.
 *
 * @since 4.0
 */
public class ClientExecuteProxy {

    public static void main(String[] args)throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpHost proxy = new HttpHost("cnbjip.mgmt.ericsson.se", 8080, "http");

            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
            HttpPost request = new HttpPost("https://mp.weixin.qq.com/cgi-bin/login");
            request.setConfig(config);
            
            request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64)"
            		+ " AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 " + "Safari/537.22");
            request.setHeader("Referer", "https://mp.weixin.qq.com/");
            
            
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("username", "eripark"));
            formparams.add(new BasicNameValuePair("pwd", DigestUtils.md5Hex("avonllet2013".getBytes())));
            formparams.add(new BasicNameValuePair("f", "json"));
            formparams.add(new BasicNameValuePair("imagecode", ""));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            request.setEntity(entity);

            System.out.println("Executing request " + request.getRequestLine() + " via " + proxy);
            
            

            CloseableHttpResponse response = httpclient.execute(request);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity()));
                EntityUtils.consume(response.getEntity());
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

}