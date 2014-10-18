package lbs.baidu.test;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import junit.framework.TestCase;

import org.apache.commons.codec.binary.Base64;

import cn.bmwm.common.utils.RSAUtils;

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
		
		String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdJuSU16e9PCp5ysfb3DDh3DcWpNg/dLRbWQhdjp53G4zk0DbpKTvEtQare2YxTlyO/ydbNLnFxZ2NhKepXoj3G9ZDUbjGZeglLOqTjmdskf8vb9MV3ggeUNKVyXLZcNPJ54pQGN+MDe3kiGivsqnbvbNuAFv2wD4J3BtpB03aXQIDAQAB";
		
		String text = "123456";
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		byte[] keyBytes = Base64.decodeBase64(key);
		
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		
		String enText = RSAUtils.encrypt(publicKey, text);
		
		System.out.println(enText);
		
	}
	
	public void testDecrypt1() throws Exception {
		
		String key = "eUgiRFCqYqN/PiV0PjCR2RSLiDSAgOCzEgbvfg8fH6gTerWa+aIB+30eX8V1mkqAPXbUaFlgrwwFiuimmwDFNZim2+OxlT9Hceo4txpnm7bhl2fhznK8IkvvVAKCI/wfBhG/Jmp/SBYmknWJZFIxbpNo9zjTAsbhHdNUP+9HPmJcOGdUYdUWHRcwsVhPxXilxef+pfcdvw9lXCY9gbnfDMWVsNhdV6hrkjEXFoMhyhurnqp9hRf4iCEN/RCiL9XvfVp4h7+SY4Bm33Ct/C7ojuTUuII+s5CF0hJFSOedqzwLQ65KT2qa/tMFNT2EbVeMQWtmQoXYSeR5U1OnviUnWcBhgz+IrDR+xWx/ruAJyyrRTvQJBjFRZ8RHEpU+4vtg1tJ6XkcIj+rOOrFnOkX0BJ2KvNIGGJ7QVg68K6NxpQYgXXdjNJjZY4FgY/Cz2ADbxH/2dAlW6Al+KzeiRXbZEckG0lwi1HZroR0ve/WX6wDlmUUF251eK4GmHTfkgojJutNPUyb0GhBiy/gWatALNdotWYmmc3YRbOTgdI+39iu7MOF4fRqlfth0qbtHZHMgghmomUt837iBg5gBpWxqYb9dDP+n27XuUMlJLXWqbyvRQWDLRdAYMsh6XBgxIV7jyqYghQ36l/S36ulmlQ78jyd1dU/V7MQbkaTSAHFUjBKo8HEmaiM1n4hx6fqI20Rso6leI5WoxrF507ahLIasEOSvNiFLX2zS7KH23vWogwlp2tBV1yzH9aMqfa3Zv4pZtyVVkpyyzT5vlgjIBr7mXQY+KbxN+aAS6ds+BpyRNE6ggPs6A/aqeg==";
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		byte[] keyBytes = Base64.decodeBase64(key);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		
		String enText = "1J0aNTGqdJHenPvmKf+dqaRgJBgrNjKxDbOzeJg+hPpqMEL3ndK1Tb33DCegkg2NZ0bnW7PwNLRDju99tYSJuD1kdTvWLZii+ikx2UGdUlMP0UNj9j19Q3kDjLwRkaiW9tyRrUpO+6qQPDIBGl0bAnWYRxk6J3gCEvvKsQLyL8s=";
		
		String text = RSAUtils.decrypt(privateKey, enText);
		
		System.out.println(text);
		
	}
	
}
