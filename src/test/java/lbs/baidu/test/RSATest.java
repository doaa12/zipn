package lbs.baidu.test;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.Provider;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import junit.framework.TestCase;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import cn.bmwm.common.utils.RSAUtils;
import cn.bmwm.modules.sys.exception.SystemException;

/**
 * @author zby
 * 2014-10-18 下午5:26:19
 */
public class RSATest extends TestCase {
	
	public void testGenerateKey() {
		
		KeyPair keyPair = RSAUtils.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
		
		System.out.println("public key : " + Base64.encodeBase64String(publicKey.getEncoded()));
		
		System.out.println("private key : " + Base64.encodeBase64String(privateKey.getEncoded()));
		
	}
	
	public void testEncrypt() throws Exception {
		
		String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCR7Y2SqGgJERE/rSA410qgDxTIo83u9DAq3FIMbrbSYFLTPq3EUGgiJa6/owQMb3dLzyik1yPlwggYxrQqPiZwElTLxJEAAMLwaKLtzB6dkdgFtwYpLmWcAAhyRV0M4Gkjbz9fntlPI2BpBr7idRCpuoAFN1ed+DvMfMSOmgy5RQIDAQAB";
		
		String text = "123456";
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		byte[] keyBytes = Base64.decodeBase64(key);
		
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		
		String enText = RSAUtils.encrypt(publicKey, text);
		
		System.out.println(enText);
		
	}
	
	public void testDecrypt() throws Exception {
		
		String key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJHtjZKoaAkRET+tIDjXSqAPFMijze70MCrcUgxuttJgUtM+rcRQaCIlrr+jBAxvd0vPKKTXI+XCCBjGtCo+JnASVMvEkQAAwvBoou3MHp2R2AW3BikuZZwACHJFXQzgaSNvP1+e2U8jYGkGvuJ1EKm6gAU3V534O8x8xI6aDLlFAgMBAAECgYBsSeVKFLCHMeQj5ZxIsfLNzgM1VOEH1qs70x0L3sjKWvJ7kkKusKBl3qLL0PO7KqhTWFon4QLhh9gsUp/1zQMF9/BDBwsSO12q1Um6vYSQiaL83XYtww5LUFRgBDwj3AZAnUZssMJ8WGHZFbV8ZGq94B2QgCFYeKdzhUcqSEiLDQJBAO/iawlrFsJIvZhUnFXp797066R8/eJ5uxbZhtKBGhsOxajMEoT/wSIXyjLv/I2huJR/Uu98ubnsREvK9foMTrMCQQCbu0BuGCkKWBOlcw4PrySphm2PneMALxNk0DBABlPeRDpqKckXbslBn51cb3UdWH6/n10FzIn6T9NrWnxzPlQnAkBsLRy7yJEg2BC3yyAdFjvXvXcuXDdZlC+sZDgKYbHWFdlI/44yb+s2ZqgGRHcNn6pPPQO3f/V5RHSgAUzfkAFFAkEAmeDDwEVlQBBZkVEHot3H1y66UX+MDuF1Q1cnZgwakPRP2Bjj16CjgpFqFy0WxuFrlIKukw1RhSGdYWVutRzwXwJAEVWrvXMxXJUTvnWzoroN4Ww/OnGOmw9PdSrpJNaHYOXUZVN7vECOlWAqvvP66CT0vHrNrRiMTiP2QhDg1+fsJQ==";
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		byte[] keyBytes = Base64.decodeBase64(key);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		
		String enText = "Wd20A9dz2EdA4QprFYe3q3AzV02n+ZKF2SlyXYLLwxGnUU/YiYeoXl8lbltu52yGuM+1BxUBL/12t+wmYXWHZetY/tf7+7S/oWSEv0BBhYgYsV2qpIkqjr2fVqcFZQHqLMwPFYdjO0MgrIK7/mlK0iyxQrzxvv/eZxp7oGqJ3ms=";
		
		String text = RSAUtils.decrypt(privateKey, enText);
		
		System.out.println(text);
		
	}
	
	public void testEncrypt1() throws Exception {
		
		String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9fSJ1l962Qdq7rMqCx2Wr7CxMe2H07nOogxt379kyAUZeESWudbSIXnMQU22nRJGHwitkETPeTkpswY1jGk9TBECbBmyzTJRbKSq08rDEI3yxXd6RqtniK47I24y80Gnx8ptYUeAdypNdcLJ/vKTCLQnoA07TfMeZl7XqnAaJ9QIDAQAB";
		
		//String text = "123456";
		
		String text = "111111";
		
		byte[] keyBytes = Base64.decodeBase64(key);
		
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		
		String enText = RSAUtils.encrypt(publicKey, text);
		
		System.out.println(enText);
		
	}
	
	public void testDecrypt1() throws Exception {
		
		String enText = "QToI6Lc8ZMv/t9or2cb13qN5KsNezau9+z/otT2bW3Wrej/e2o+FaDSr97hsbPNX6qAJcoj0ax9mm8iN0EY2SMSMC7hzaRcFmCfqJr27BurwEKOQwiFXLGvutusi2t4Hr4kcAqSFxqtvsSqxA5McNYZl8YOePGb0oVGBRODZmpw=";
		
		String key = "MIICXgIBAAKBgQC9fSJ1l962Qdq7rMqCx2Wr7CxMe2H07nOogxt379kyAUZeESWudbSIXnMQU22nRJGHwitkETPeTkpswY1jGk9TBECbBmyzTJRbKSq08rDEI3yxXd6RqtniK47I24y80Gnx8ptYUeAdypNdcLJ/vKTCLQnoA07TfMeZl7XqnAaJ9QIDAQABAoGBAJU5ZZjL2AUaCYLAyd6B3wysehplFDiKTKUJUul6Bka+AEd2I4GnilvWXbEesn0Gn8EU5YzxizJn326UYp8ICizN85EI/jiKDk+gyt6BttDgX3Xf3v0nu15JmjTSD5Fo0m6hphuLEq7/fNDetQ1UgBeKi8jo6aLu9ySn1WTRTwWhAkEA+MbXeZSaVptzxW/B83h9c56/Pscg6FlQ9WsS0w0+7/FKluvAkQU2we5NwK6zJzRC38rlBMHconkNcU3pkUHtbQJBAML9mwhZ9l388Wng4NRyUnx/ZgT6FL1mioKq7vhFdc9FKjACAVmLE/FCKI4pd3MLHu/mGmXiJ/r6qu2WTdyk4akCQQCufkoE7UaUGNVLVugjbhAQWPirf+CFGKDAgyng/xl2EzjOQu3+yjluLUg8Lk1a4j1F23pnq9Kl42KaZpu9VxDBAkEAoccTp5wsQdKo4TWIk/q94Tk6BYsPRg0bgkobtrS6h9tUozwmroorY5GGYFybFEH3ywZYhItcrGjpA/Iea6AI8QJAbNRVCO2ImdT/fngoFKK40PLZSooFu0njRYcNUWGr7GkUxeS7gC3GNYWcZG+f8zbXaXsjyD0pSNeaHFDRmJCcTA==";
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		byte[] keyBytes = Base64.decodeBase64(key);
		
		RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(keyBytes));
		
		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent()); 
		
		RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		
		Provider PROVIDER = new BouncyCastleProvider();
		
		//Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", PROVIDER);

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", PROVIDER);
		
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		
		byte[] data = cipher.doFinal(Base64.decodeBase64(enText));
		
		System.out.println(new String(data));
		
	}
	
	public void testMD5() {
		String md5 = DigestUtils.md5Hex("admin");
		System.out.println(md5);
	}
	
}
