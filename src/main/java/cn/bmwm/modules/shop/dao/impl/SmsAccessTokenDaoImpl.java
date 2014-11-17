/**
 * 
 */
package cn.bmwm.modules.shop.dao.impl;

import org.springframework.stereotype.Repository;

import cn.bmwm.modules.shop.dao.SmsAccessTokenDao;
import cn.bmwm.modules.shop.entity.SmsAccessToken;

/**
 * Dao - 验证码短信Token
 * @author zhoupuyue
 * @date 2014-11-17
 */
@Repository("smsAccessTokenDaoImpl")
public class SmsAccessTokenDaoImpl extends BaseDaoImpl<SmsAccessToken,Long> implements SmsAccessTokenDao {

}
