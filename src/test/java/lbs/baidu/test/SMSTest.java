/**
 * 
 */
package lbs.baidu.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import cn.bmwm.common.utils.HttpClientUtils;

import com.alibaba.fastjson.JSONObject;

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
		data.put("template_param", "{\"param1\":5241,\"param2\":30}");
		data.put("timestamp", sdf.format(new Date()));
		
		JSONObject object = HttpClientUtils.httpPost("http://api.189.cn/v2/emp/templateSms/sendSms", data);
		
		System.out.println(object.toJSONString());
		
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
		
		JSONObject object = HttpClientUtils.httpPost(url.toString(), null);
		
		System.out.println(object.toJSONString());
		
	}
	
}
