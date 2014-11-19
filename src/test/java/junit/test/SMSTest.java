/**
 * 
 */
package junit.test;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import junit.framework.TestCase;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import cn.bmwm.common.utils.HttpClientUtils;

/**
 * @author zhoupuyue
 * @date 2014-11-17
 */
public class SMSTest extends TestCase {
	
	/**
	 * 测试发送短信
	 */
	public void testSendSms() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Map<String,String> data = new HashMap<String,String>();
		data.put("app_id", "175870130000037424");
		data.put("access_token", "24923ffdf4378a5ea8d66931fd6c11691416188315831");
		data.put("acceptor_tel", "13816168984");
		data.put("template_id", "91003145");
		data.put("template_param", "{\"param1\":\"0241\",\"param2\":\"30\"}");
		data.put("timestamp", sdf.format(new Date()));
		
		String text = HttpClientUtils.httpPost("http://api.189.cn/v2/emp/templateSms/sendSms", data, "GBK");
		
		System.out.println(text);
		
	}
	
	/**
	 * 测试刷新Token
	 */
	public void testRefreshToken() {
		
		
		StringBuilder url = new StringBuilder();
		
		url.append("https://oauth.api.189.cn/emp/oauth2/v3/access_token")
		.append("?grant_type=refresh_token&refresh_token=24923ffdf4378a5ea8d66931fd6c11691416188315831")
		.append("&app_id=175870130000037424&app_secret=fd75058de1fe7d53432dcf7b42f3a211");
		
		/*
		Map<String,String> data = new HashMap<String,String>();
		data.put("grant_type", "refresh_token");
		data.put("refresh_token", "24923ffdf4378a5ea8d66931fd6c11691416188315831");
		data.put("app_id", "175870130000037424");
		data.put("app_secret", "fd75058de1fe7d53432dcf7b42f3a211");
		*/
		
		//JSONObject object = HttpClientUtils.httpPost("https://oauth.api.189.cn/emp/oauth2/v3/access_token", data);
		
		String text = HttpClientUtils.httpPost(url.toString(), null, "GBK");
		
		System.out.println(text);
		
	}
	
	public void testHTTPs() throws Exception {
		
		 HttpClient httpclient = new DefaultHttpClient();
		 
         //Secure Protocol implementation.  
		SSLContext ctx = SSLContext.getInstance("TLS"); 
		
		//Implementation of a trust manager for X509 certificates  
		X509TrustManager tm = new X509TrustManager() {  
		
			 public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {  
			
			 }  
			
			 public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {  
				 
			 }  
			
			 public X509Certificate[] getAcceptedIssuers() {  
			     return null;  
			 } 
			 
		};  
		
		ctx.init(null, new TrustManager[]{tm}, null);  
		
		SSLSocketFactory ssf = new SSLSocketFactory(ctx);  
		
		ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		
		ClientConnectionManager ccm = httpclient.getConnectionManager(); 
		
		//register https protocol in httpclient's scheme registry  
		SchemeRegistry sr = ccm.getSchemeRegistry(); 
		
		sr.register(new Scheme("https", 443, ssf));

		StringBuilder url = new StringBuilder();
		
		url.append("https://oauth.api.189.cn/emp/oauth2/v3/access_token")
		.append("?grant_type=refresh_token&refresh_token=24923ffdf4378a5ea8d66931fd6c11691416188315831")
		.append("&app_id=175870130000037424&app_secret=fd75058de1fe7d53432dcf7b42f3a211");
		
		HttpPost httpPost = new HttpPost(url.toString());
		
		ResponseHandler responseHandler = new BasicResponseHandler();  
		
		String responseBody = httpclient.execute(httpPost, responseHandler);  
		
		System.out.println(responseBody);  
				
	}
	
}
