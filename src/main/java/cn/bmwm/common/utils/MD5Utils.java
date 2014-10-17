/**
 * 
 */
package cn.bmwm.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;
import cn.bmwm.modules.sys.exception.SystemException;

/**
 * MD5加密
 * @author zhoupuyue
 * @date 2014-10-17
 */
public class MD5Utils {
	
	/**
	 * 加密
	 * @param text
	 * @return
	 */
	public static String encode(String text) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(text.getBytes());
			BASE64Encoder base64en = new BASE64Encoder();
			return base64en.encode(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new SystemException(" md5 encode exception ! ", e);
		}
	}
	
	public static void main(String[] args) {
		String text = "111";
		System.out.println(encode(text));
	}
	
}
