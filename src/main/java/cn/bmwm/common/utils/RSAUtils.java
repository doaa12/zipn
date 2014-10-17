package cn.bmwm.common.utils;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.Assert;

/**
 * Utils - RSA加密解密
 * 
 * @version 1.0
 */
public final class RSAUtils {

	/** 安全服务提供者 */
	private static final Provider PROVIDER = new BouncyCastleProvider();

	/** 密钥大小 */
	private static final int KEY_SIZE = 1024;

	/**
	 * 不可实例化
	 */
	private RSAUtils() {
	}

	/**
	 * 生成密钥对
	 * 
	 * @return 密钥对
	 */
	public static KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", PROVIDER);
			keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
			return keyPairGenerator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 加密
	 * 
	 * @param publicKey
	 *            公钥
	 * @param data
	 *            数据
	 * @return 加密后的数据
	 */
	public static byte[] encrypt(PublicKey publicKey, byte[] data) {
		Assert.notNull(publicKey);
		Assert.notNull(data);
		try {
			Cipher cipher = Cipher.getInstance("RSA", PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 加密
	 * 
	 * @param publicKey
	 *            公钥
	 * @param text
	 *            字符串
	 * 
	 * @return Base64编码字符串
	 */
	public static String encrypt(PublicKey publicKey, String text) {
		Assert.notNull(publicKey);
		Assert.notNull(text);
		byte[] data = encrypt(publicKey, text.getBytes());
		return data != null ? Base64.encodeBase64String(data) : null;
	}

	/**
	 * 解密
	 * 
	 * @param privateKey
	 *            私钥
	 * @param data
	 *            数据
	 * @return 解密后的数据
	 */
	public static byte[] decrypt(PrivateKey privateKey, byte[] data) {
		Assert.notNull(privateKey);
		Assert.notNull(data);
		try {
			Cipher cipher = Cipher.getInstance("RSA", PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解密
	 * 
	 * @param privateKey
	 *            私钥
	 * @param text
	 *            Base64编码字符串
	 * @return 解密后的数据
	 */
	public static String decrypt(PrivateKey privateKey, String text) {
		Assert.notNull(privateKey);
		Assert.notNull(text);
		byte[] data = decrypt(privateKey, Base64.decodeBase64(text));
		return data != null ? new String(data) : null;
	}

	public static void main(String[] args) throws Exception {
		
		/*
		 
		KeyPair keyPair = generateKeyPair();
		
		RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
		
		String text = "123456";
		
		String encryptText = encrypt(publicKey, text);
		
		System.out.println(encryptText);
		
		String decryptText = decrypt(privateKey, encryptText);
		
		System.out.println(decryptText);
		
		*/
		
		/*
		String password = "123456";
		
		String n = "MIICXAIBAAKBgQCsp3jzlP8VHN444eyE5yKuhT77eUNWJKQT1btCOQQU5DmcTNc46T9pSPM7NYgF7h5uSMhh/LUiQ/znPIRItlhykyK10OUQ+E1jaZhFxjzpELOaHpr94h2iSGcWPfISofZDrOivSjKriW4rkSatTh4Zjfi1zipzbxDfJZwVX/fzbQIDAQABAoGAKIlNeYsi9LcJabuJh46o8uav6eUHrUL/DvUgeSTf03PeHtdftKn2zGZnTSB9Zj8JAAWwnmjxsjDaQQRRXq0JSKjSOvTBs2Jk3UDEJQaIGMd+pHnyYY2ZGeLRwGZ+S6xfNFPN8/P3FEt9lqUBdMuFdnKRXizHd2jN+YdSYawbEgECQQGqeaaIKQE76iZ5Od54f/jAxjvoSbkbQlXfpjIxALzxQXW0drSsBGna3BAidT/zCZgmberp6cFt/zd/M0lJiPTtAkBno5b2ZtedxUnKB49FqrcvpMNGUzT4uYh4uz9AuTKB9QevgIpu4UVZbB8mOAK11TvgTLS6ZiBbDewstUeP/aiBAkEBnwxXgyoM9mdx3AbyuWkqCUqjoPSmvp23fzp6nHAwcccYK3JfcQ22i3YiCbb8bqYGule9CLsjMc7xDs015OfyHQJACrJLpmq+3j26e+uD0gDonzY2IU/9K56agztwL9HtcJRFksuFfiQp8CzEmkE1pma3o1LHZGWd+UfalFKxbB8WAQJBAY0WI7v4wDEnwH1z/nuCAyhgGY8f9Apo1X5GSR27p3d/zWl9YddfIVF37nYQ2wD4UuZJkkuAD4yB0JGELPU3Bqg=";
		String e = "AQAB";
		
		sun.security.rsa.RSAPublicKeyImpl publicKey = new sun.security.rsa.RSAPublicKeyImpl(
				new BigInteger(Base64.decodeBase64(n)), new BigInteger(Base64.decodeBase64(e)));
		
		String enpassword = encrypt(publicKey, password);
		System.out.println(enpassword);
		*/
		
		String prikey = "eUgiRFCqYqN/PiV0PjCR2RSLiDSAgOCzEgbvfg8fH6gTerWa+aIB+30eX8V1mkqA"
					  + "PXbUaFlgrwwFiuimmwDFNZim2+OxlT9Hceo4txpnm7bhl2fhznK8IkvvVAKCI/wf"
					  + "BhG/Jmp/SBYmknWJZFIxbpNo9zjTAsbhHdNUP+9HPmJcOGdUYdUWHRcwsVhPxXil"
					  + "xef+pfcdvw9lXCY9gbnfDMWVsNhdV6hrkjEXFoMhyhurnqp9hRf4iCEN/RCiL9Xv"
					  + "fVp4h7+SY4Bm33Ct/C7ojuTUuII+s5CF0hJFSOedqzwLQ65KT2qa/tMFNT2EbVeM"
					  + "QWtmQoXYSeR5U1OnviUnWcBhgz+IrDR+xWx/ruAJyyrRTvQJBjFRZ8RHEpU+4vtg"
					  + "1tJ6XkcIj+rOOrFnOkX0BJ2KvNIGGJ7QVg68K6NxpQYgXXdjNJjZY4FgY/Cz2ADb"
					  + "xH/2dAlW6Al+KzeiRXbZEckG0lwi1HZroR0ve/WX6wDlmUUF251eK4GmHTfkgojJ"
					  + "utNPUyb0GhBiy/gWatALNdotWYmmc3YRbOTgdI+39iu7MOF4fRqlfth0qbtHZHMg"
					  + "ghmomUt837iBg5gBpWxqYb9dDP+n27XuUMlJLXWqbyvRQWDLRdAYMsh6XBgxIV7j"
					  + "yqYghQ36l/S36ulmlQ78jyd1dU/V7MQbkaTSAHFUjBKo8HEmaiM1n4hx6fqI20Rs"
					  + "o6leI5WoxrF507ahLIasEOSvNiFLX2zS7KH23vWogwlp2tBV1yzH9aMqfa3Zv4pZ"
					  + "tyVVkpyyzT5vlgjIBr7mXQY+KbxN+aAS6ds+BpyRNE6ggPs6A/aqeg==";
		
		String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsp3jzlP8VHN444eyE5yKuhT77"
					  + "eUNWJKQT1btCOQQU5DmcTNc46T9pSPM7NYgF7h5uSMhh/LUiQ/znPIRItlhykyK1"
					  + "0OUQ+E1jaZhFxjzpELOaHpr94h2iSGcWPfISofZDrOivSjKriW4rkSatTh4Zjfi1"
					  + "zipzbxDfJZwVX/fzbQIDAQAB";
		
		String e = "key_pair_tag";
		
		String text = "123456";
		
		/*
		byte[] keyBytes = new BASE64Decoder().decodeBuffer(key);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		
		String text = "GM2LNyVY7Cw+hwwHmlxtH4ND8vKD5UaQ3b6P9xOyaGYgZ2pAEZ4+2ngzfYbJtgxeoybSsdJUo0x80S9POh5hJzRHV70Y1+GiQ5A4tTQiI3srmipw+0f5k4pHM0bqpoF1Acpb/XKIDSoyQUlGz3QVp9zDnmFD43XX+tbrr3HjkqM=";
		
		System.out.println(decrypt(privateKey, text));
		*/
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(Base64.decodeBase64(pubkey)), new BigInteger(Base64.decodeBase64(e)));
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		
		String enText = encrypt(publicKey, text);
		System.out.println(enText);
		
		RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(Base64.decodeBase64(prikey)), new BigInteger(Base64.decodeBase64(e)));
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
		
		//String text = "GM2LNyVY7Cw+hwwHmlxtH4ND8vKD5UaQ3b6P9xOyaGYgZ2pAEZ4+2ngzfYbJtgxeoybSsdJUo0x80S9POh5hJzRHV70Y1+GiQ5A4tTQiI3srmipw+0f5k4pHM0bqpoF1Acpb/XKIDSoyQUlGz3QVp9zDnmFD43XX+tbrr3HjkqM=";
		System.out.println(decrypt(privateKey, enText));
		
	}
	
}