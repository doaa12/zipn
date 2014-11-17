/**
 * 
 */
package cn.bmwm.modules.shop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bmwm.modules.shop.dao.SmsAccessTokenDao;
import cn.bmwm.modules.shop.entity.SmsAccessToken;
import cn.bmwm.modules.shop.service.SmsAccessTokenService;

/**
 * Service - 验证码短信Token
 * @author zhoupuyue
 * @date 2014-11-17
 */
@Service("smsAccessTokenServiceImpl")
public class SmsAccessTokenServiceImpl extends BaseServiceImpl<SmsAccessToken,Long> implements SmsAccessTokenService {
	
	@Resource(name = "smsAccessTokenDaoImpl")
	public void setBaseDao(SmsAccessTokenDao smsAccessTokenDao){
		super.setBaseDao(smsAccessTokenDao);
	}
	
}
