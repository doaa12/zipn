package lbs.baidu.test;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;

import junit.framework.TestCase;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;

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
		
		String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsp3jzlP8VHN444eyE5yKuhT77eUNWJKQT1btCOQQU5DmcTNc46T9pSPM7NYgF7h5uSMhh/LUiQ/znPIRItlhykyK10OUQ+E1jaZhFxjzpELOaHpr94h2iSGcWPfISofZDrOivSjKriW4rkSatTh4Zjfi1zipzbxDfJZwVX/fzbQIDAQAB";
		
		String text = "123456";
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		byte[] keyBytes = Base64.decodeBase64(key);
		
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		
		String enText = RSAUtils.encrypt(publicKey, text);
		
		System.out.println(enText);
		
	}
	
	public void testDecrypt1() throws Exception {
		
		String key = "MIICXAIBAAKBgQCsp3jzlP8VHN444eyE5yKuhT77eUNWJKQT1btCOQQU5DmcTNc46T9pSPM7NYgF7h5uSMhh/LUiQ/znPIRItlhykyK10OUQ+E1jaZhFxjzpELOaHpr94h2iSGcWPfISofZDrOivSjKriW4rkSatTh4Zjfi1zipzbxDfJZwVX/fzbQIDAQABAoGAKIlNeYsi9LcJabuJh46o8uav6eUHrUL/DvUgeSTf03PeHtdftKn2zGZnTSB9Zj8JAAWwnmjxsjDaQQRRXq0JSKjSOvTBs2Jk3UDEJQaIGMd+pHnyYY2ZGeLRwGZ+S6xfNFPN8/P3FEt9lqUBdMuFdnKRXizHd2jN+YdSYawbEgECQQGqeaaIKQE76iZ5Od54f/jAxjvoSbkbQlXfpjIxALzxQXW0drSsBGna3BAidT/zCZgmberp6cFt/zd/M0lJiPTtAkBno5b2ZtedxUnKB49FqrcvpMNGUzT4uYh4uz9AuTKB9QevgIpu4UVZbB8mOAK11TvgTLS6ZiBbDewstUeP/aiBAkEBnwxXgyoM9mdx3AbyuWkqCUqjoPSmvp23fzp6nHAwcccYK3JfcQ22i3YiCbb8bqYGule9CLsjMc7xDs015OfyHQJACrJLpmq+3j26e+uD0gDonzY2IU/9K56agztwL9HtcJRFksuFfiQp8CzEmkE1pma3o1LHZGWd+UfalFKxbB8WAQJBAY0WI7v4wDEnwH1z/nuCAyhgGY8f9Apo1X5GSR27p3d/zWl9YddfIVF37nYQ2wD4UuZJkkuAD4yB0JGELPU3Bqg=";
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		byte[] keyBytes = Base64.decodeBase64(key);
		
		RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(keyBytes));
		
		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent()); 
		
		RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		
		String enText = "TihlQ+1120JEUgRdoLrcc1s16P1nL68A1Id5U5Wz7wlhOcirVEQvP1lnHu+MGjwb0hqMpGT4NPb4Ad/niLbQEeiIro2EBhpTRdKA9+UM1nr/8rjBoV0UuCmJTxXJyWTinXuLVhipgr4N7TRweOeSN+Hw2DyYLOnpOovyUnPvdHQ=";
		
		String text = RSAUtils.appDecrypt(privateKey, enText);
		
		System.out.println(text);
		
	}
	
	public void testMD5() {
		String md5 = DigestUtils.md5Hex("admin");
		System.out.println(md5);
	}
	
}
