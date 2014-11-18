/**
 * 
 */
package cn.bmwm.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.bmwm.modules.sys.exception.SystemException;

/**
 * HTTP工具类
 * @author zhoupuyue
 * @date 2014-3-29
 */
public class HttpClientUtils {
	
	public static final Log log = LogFactory.getLog(HttpClientUtils.class);
	
	private static final CloseableHttpClient httpClient;
	
	static{
		RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}
	
	/**
	 * 发送GET请求
	 * @param request
	 * @return
	 */
	public static String httpGet(String url, String encoding) {
		
		if(StringUtils.isBlank(url)) return null;
		
		HttpGet httpGet = new HttpGet(url);
		
		CloseableHttpResponse httpResponse = null;
		
		try {
			
			httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			
			if(statusCode != HttpStatus.SC_OK){
				httpGet.abort();
				httpResponse.close();
				throw new SystemException("发送请求失败！statusCode: " + statusCode + " ,URL: " + url);
			}
			
			HttpEntity entity = httpResponse.getEntity();
			String result = null;
			
			if(entity != null){
				result = EntityUtils.toString(entity, encoding);
				EntityUtils.consume(entity);
			}
			
			return result;
			
		} catch (ClientProtocolException e) {
			log.error("协议错误！URL: " + url, e);
			throw new SystemException("协议错误！URL: " + url, e);
		} catch (IOException e) {
			log.error("IO异常！URL: " + url, e);
			throw new SystemException("IO异常！URL: " + url, e);
		} finally {
			if(httpResponse != null){
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 发送POST请求
	 * @param request
	 * @return
	 */
	public static String httpPost(String url, Map<String,String> data, String encoding) {
		
		if(StringUtils.isBlank(url)) return null;
		
		HttpPost httpPost = new HttpPost(url);
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		if(data != null && data.size() > 0) {
			for(String key : data.keySet()){
				nvps.add(new BasicNameValuePair(key, data.get(key)));
			}
		}
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error("编码不支持: UTF-8", e);
			throw new SystemException("编码不支持: UTF-8 ", e);
		}
		
		CloseableHttpResponse httpResponse = null;
		
		try {
			
			httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			
			if(statusCode < 200 || statusCode >= 400){
				httpPost.abort();
				httpResponse.close();
				throw new SystemException("发送请求失败！statusCode: " + statusCode + " ,URL: " + url);
			}
			
			if(statusCode > 300 && statusCode < 400){
				Header header = httpResponse.getFirstHeader("location");
				String redirectUrl = header.getValue();
				System.out.println("重定向URL：" + redirectUrl);
				return httpPost(redirectUrl, data, encoding);
			}
			
			HttpEntity entity = httpResponse.getEntity();
			String result = null;
			
			if(entity != null){
				result = EntityUtils.toString(entity, encoding);
				EntityUtils.consume(entity);
			}
			
			return result;
			
		} catch (ClientProtocolException e) {
			log.error("协议错误！URL: " + url, e);
			throw new SystemException("协议错误！URL: " + url, e);
		} catch (IOException e) {
			log.error("IO异常！URL: " + url, e);
			throw new SystemException("IO异常！URL: " + url, e);
		} finally {
			if(httpResponse != null){
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 关闭HttpClient
	 */
	public static void close(){
		if(httpClient != null){
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
