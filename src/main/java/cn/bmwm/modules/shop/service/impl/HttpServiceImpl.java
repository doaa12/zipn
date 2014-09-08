package cn.bmwm.modules.shop.service.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.service.HttpService;
import cn.bmwm.modules.sys.exception.SystemException;

import com.alibaba.fastjson.JSONObject;

/**
 * Service -- HTTP
 * @author zby
 * 2014-9-8 下午10:55:44
 */
@Service("httpServiceImpl")
public class HttpServiceImpl implements HttpService {
	
	public static final Log log = LogFactory.getLog(HttpServiceImpl.class);

	/**
	 * 发送HTTP GET请求
	 * @param url
	 * @return
	 */
	@Override
	public Map<String,Object> executeGet(String url) {
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		
		try {
			String result = httpClient.execute(httpGet, responseHandler);
			JSONObject obj = JSONObject.parseObject(result);
			return obj;
		} catch (ClientProtocolException e) {
			log.error("HTTP请求协议错误！", e);
			throw new SystemException("HTTP请求协议错误！", e);
		} catch (IOException e) {
			log.error("IO异常！", e);
			throw new SystemException("IO异常！", e);
		}
		
	}

}
