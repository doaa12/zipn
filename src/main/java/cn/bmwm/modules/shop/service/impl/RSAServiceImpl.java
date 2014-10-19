/*


 * */
package cn.bmwm.modules.shop.service.impl;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.bmwm.common.utils.RSAUtils;
import cn.bmwm.modules.shop.service.RSAService;
import cn.bmwm.modules.sys.exception.BusinessException;
import cn.bmwm.modules.sys.exception.SystemException;

/**
 * Service - RSA安全
 * 
 *
 * @version 1.0
 */
@Service("rsaServiceImpl")
public class RSAServiceImpl implements RSAService {

	/** "私钥"参数名称 */
	private static final String PRIVATE_KEY_ATTRIBUTE_NAME = "privateKey";
	
	@Transactional(readOnly = true)
	public RSAPublicKey generateKey(HttpServletRequest request) {
		Assert.notNull(request);
		KeyPair keyPair = RSAUtils.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		HttpSession session = request.getSession();
		session.setAttribute(PRIVATE_KEY_ATTRIBUTE_NAME, privateKey);
		return publicKey;
	}

	@Transactional(readOnly = true)
	public void removePrivateKey(HttpServletRequest request) {
		Assert.notNull(request);
		HttpSession session = request.getSession();
		session.removeAttribute(PRIVATE_KEY_ATTRIBUTE_NAME);
	}

	@Transactional(readOnly = true)
	public String decryptParameter(String name, HttpServletRequest request) {
		Assert.notNull(request);
		if (name != null) {
			HttpSession session = request.getSession();
			RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute(PRIVATE_KEY_ATTRIBUTE_NAME);
			String parameter = request.getParameter(name);
			if (privateKey != null && StringUtils.isNotEmpty(parameter)) {
				return RSAUtils.decrypt(privateKey, parameter);
			}
		}
		return null;
	}
	
	/**
	 * 解密
	 * @param text
	 * @return
	 */
	public String decrypt(String text) {
		
		if(StringUtils.isBlank(text)) {
			throw new BusinessException("密码为空！");
		}
		
		try {
			
			String key = "MIICXAIBAAKBgQCsp3jzlP8VHN444eyE5yKuhT77eUNWJKQT1btCOQQU5DmcTNc46T9pSPM7NYgF7h5uSMhh/LUiQ/znPIRItlhykyK10OUQ+E1jaZhFxjzpELOaHpr94h2iSGcWPfISofZDrOivSjKriW4rkSatTh4Zjfi1zipzbxDfJZwVX/fzbQIDAQABAoGAKIlNeYsi9LcJabuJh46o8uav6eUHrUL/DvUgeSTf03PeHtdftKn2zGZnTSB9Zj8JAAWwnmjxsjDaQQRRXq0JSKjSOvTBs2Jk3UDEJQaIGMd+pHnyYY2ZGeLRwGZ+S6xfNFPN8/P3FEt9lqUBdMuFdnKRXizHd2jN+YdSYawbEgECQQGqeaaIKQE76iZ5Od54f/jAxjvoSbkbQlXfpjIxALzxQXW0drSsBGna3BAidT/zCZgmberp6cFt/zd/M0lJiPTtAkBno5b2ZtedxUnKB49FqrcvpMNGUzT4uYh4uz9AuTKB9QevgIpu4UVZbB8mOAK11TvgTLS6ZiBbDewstUeP/aiBAkEBnwxXgyoM9mdx3AbyuWkqCUqjoPSmvp23fzp6nHAwcccYK3JfcQ22i3YiCbb8bqYGule9CLsjMc7xDs015OfyHQJACrJLpmq+3j26e+uD0gDonzY2IU/9K56agztwL9HtcJRFksuFfiQp8CzEmkE1pma3o1LHZGWd+UfalFKxbB8WAQJBAY0WI7v4wDEnwH1z/nuCAyhgGY8f9Apo1X5GSR27p3d/zWl9YddfIVF37nYQ2wD4UuZJkkuAD4yB0JGELPU3Bqg=";
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			
			byte[] keyBytes = Base64.decodeBase64(key);
			
			RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(keyBytes));
			
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent()); 
			
			RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
			
			return RSAUtils.decrypt(privateKey, text);
			
		} catch(Exception e) {
			throw new SystemException("解密异常！", e);
		}
		
	}

}