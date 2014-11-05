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
		
		String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYQeIK9t/WqpbdQihV6uhkGEfO2X/DWHj6R6cZbVSuPG1b4l0Cja+xzrsecEUZb3d+bNTy2vECnDAf3kLCAJobZtNfjkdo5SlJMOKl46pMkX1bJvz5UYYRyc1/ErOc/6Ydc1NWn27HuK0ehjmSnx00d6J+h5boeK0tQjkCnOgn8wIDAQAB";
		
		String text = "123456";
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		byte[] keyBytes = Base64.decodeBase64(key);
		
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		
		String enText = RSAUtils.encrypt(publicKey, text);
		
		System.out.println(enText);
		
	}
	
	public void testDecrypt1() throws Exception {
		
		String key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANhB4gr239aqlt1CKFXq6GQYR87Zf8NYePpHpxltVK48bVviXQKNr7HOux5wRRlvd35s1PLa8QKcMB/eQsIAmhtm01+OR2jlKUkw4qXjqkyRfVsm/PlRhhHJzX8Ss5z/ph1zU1afbse4rR6GOZKfHTR3on6Hluh4rS1COQKc6CfzAgMBAAECgYAC1Mn6zsSbcNxMxUL17P9h4BeAk8RicFQVIr8DTDUhmtTrsQj6sZQtRcGZYmrlLFpqI06eZ1Nkb1sJ78T7+ToLP6GuwpBLqnJsjoeSQsPXOoWXJOn0DMFz67E5JcufOxCFBV9MIBSyGtMmypPr2gpaP2VIVAbItt1/mkj08e6PIQJBAPFaCjArC/XeoczaQTr1rqwlCqrzgH0BxP5Iwrw/RnPqOve1p5615sFkqPuYGTKtvjjwGhccoP+y4V6BjmwGH4kCQQDlYfGjnTCN/ypjZdhTV4ccmNyRMSpyNCKuiKmI8opB0CXyRYQ5KszR6RiIB/wezHeDdjRPU0UwtYg5etEERpCbAkEAhthPqOnkHNLug43NtCtgFD3939V8jFtYvNnaM2MG8eyEVbHgnQP0kUi0QR2RtobsKNO9P/x212YSFMM914z0QQJAe3/JTtLx8nHAi7Woy+5jM1Vl49I5zTUeuyOfS7lyEM8VryQYdYMCtb2vVIYgCyEA88mvjkoCBaCnUHM8l/W6IwJBAKbKBKmh1vvXJyMBtstTI/0+hg+n4PpFK9FVHr5I/gJEj/OcJg4w07qaEIKLhMA30LZbDnuE+r64Tyq4ylRq5PE=";
		
		String enText = "UISAZvJiJCQag1/KM4PEmfBfq5Uw+VX1n36+Yi97BL9tRWJiP6+GuRrHfesiKnslX9OksGgO1sfAlCKAzKbb3t+jbDQwhZO+h6wAe8z1LWxkDyHCuZhgSQTSQiXKfjaCya2q2JRHAbDXFfKt/rSPsOf8hw7CRlep6IwIxurUR3Q=";
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		byte[] keyBytes = Base64.decodeBase64(key);
		
		RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(keyBytes));
		
		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent());
		
		//PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		
		String text = RSAUtils.decrypt(privateKey, enText);
		
		System.out.println(text);
		
	}
	
	public void testMD5() {
		String md5 = DigestUtils.md5Hex("admin");
		System.out.println(md5);
	}
	
}
