package cn.bmwm.modules.shop.service;

/**
 * Service -- 短信
 * @author zby
 * 2014-9-29 下午10:31:50
 */
public interface SmsService {
	
	/**
	 * 发送短信
	 * @param phoneNumber
	 */
	public void send(String phoneNumber);

}
