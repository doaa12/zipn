package cn.bmwm.modules.shop.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Service -- HTTP
 * @author zby
 * 2014-9-8 下午10:44:36
 */
public interface HttpService {
	
	/**
	 * 发送HTTP GET请求
	 * @param url
	 * @return
	 */
	JSONObject executeGet(String url);
	
}
