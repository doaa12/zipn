/**
 * 
 */
package cn.bmwm.modules.shop.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 发送短信验证码AccessToken配置
 * @author zhoupuyue
 * @date 2014-11-17
 */
@Entity
@Table(name = "xx_sms_access_token")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_sms_access_token_sequence")
public class SmsAccessToken extends BaseEntity {

	private static final long serialVersionUID = 7323719270624717015L;
	
	/**
	 * 发送验证码短信Token
	 */
	private String accessToken;
	
	/**
	 * 过期时间
	 */
	private Date expireDate;

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the expireDate
	 */
	public Date getExpireDate() {
		return expireDate;
	}

	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
}
