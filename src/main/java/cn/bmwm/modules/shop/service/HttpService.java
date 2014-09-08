package cn.bmwm.modules.shop.service;

import java.util.Map;

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
	Map<String,Object> executeGet(String url);
	
}
