package lbs.baidu.test;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import junit.framework.TestCase;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.bouncycastle.asn1.x509.RSAPublicKeyStructure;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

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
		
		String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9TzXH9qgBNXTfHrTIVKO9v04cPCAm/z1iaE65TfdC9lsfnnrCKInB+8SEdc05y8SCSSyrX0kefho4j7ER+OlfW9P2b47lTLW3TKyt8FORZ76y7CfOZzj1bLUg9pcI/Z0rKq7JE+gRnGCj7SQbU5+ApC2/xO4IkE+QpmTLD36yaQIDAQAB";
		
		String text = "123456";
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		byte[] keyBytes = Base64.decodeBase64(key);
		
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		
		String enText = RSAUtils.encrypt(publicKey, text);
		
		System.out.println(enText);
		
	}
	
	public void testDecrypt() throws Exception {
		
		String key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAL1PNcf2qAE1dN8etMhUo72/Thw8ICb/PWJoTrlN90L2Wx+eesIoicH7xIR1zTnLxIJJLKtfSR5+GjiPsRH46V9b0/ZvjuVMtbdMrK3wU5FnvrLsJ85nOPVstSD2lwj9nSsqrskT6BGcYKPtJBtTn4CkLb/E7giQT5CmZMsPfrJpAgMBAAECgYAVDsySs7M4fzPC5e2CIzgbkT4lEeMZs2amnae0ISISBslYGQ7arhu0wka33Uq41RvEylL3tR+zDp/oDsPJlgCPc3Y/W6y20+elvQoJYgV4YL4bXaVW2889xulQtzR30jliSQlQ6RbiSeg49kEZEGX+FY3ggge5KYpxRjSjxzHxAQJBAP2OVM7zeq9eKVei5bHDxQ9tyLM5yXpdcHwBul3qTo0YL85yDKfZfFbgjo20vk6AOVhIk+BMRUbOBZn411Q47jECQQC/Ilhydhrg0cXWtJMTaJgVgd0Y8ur4BWSOr+b24teP3xK7srrK4QYJyL6MPd918ye1Gxu+Is9Aq6eKWlL5OmG5AkEAuibr5sBk+ylDav7Cah9Tpv8v0T2EyRyev7w5CkhCgV4XgxbCadzHZ7UXk7v12AZVk5Y9UvhMCC6reB50YHYnkQJBALgzqXnmSNESWdAqBvRVgPx5Q1OgCMMur1K0Q6r7wzNskOlDvrF3XBJh3QHbP1i1Fjd8AKilc4en5rd3hS+cDEECQQDo17L4L4mxcnR3QObFGQEckLt6ENCpVQM91z9/ts4PWfKt/AI60hot/yv9icwL2k1EzXa0n2d4qMGtb+5qqAPa";
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		byte[] keyBytes = Base64.decodeBase64(key);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		
		String enText = "ZR8b/lbM64q2YUVwo7UIrc1sTkhcgD5CB6Zyh7JRfrOF540wYXiKgWoBsnWKQJgPWKzPZplp2lusA4ygycMDxMj2ho/0tfFGutHyZItwQJm0fDibo0/PS06aHdVjQTKSmDRKQhvnprEqsSDc7lZlcVqbuf6MvbjD6QfGUOxq3yQ=";
		
		Provider PROVIDER = new BouncyCastleProvider();
		
		Cipher cipher = Cipher.getInstance("RSA", PROVIDER);
		
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		
		byte[] bytes = cipher.doFinal(Base64.decodeBase64(enText));
		
		System.out.println(new String(bytes));
		
	}
	
	public void testEncrypt1() throws Exception {
		
		String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9fSJ1l962Qdq7rMqCx2Wr7CxMe2H07nOogxt379kyAUZeESWudbSIXnMQU22nRJGHwitkETPeTkpswY1jGk9TBECbBmyzTJRbKSq08rDEI3yxXd6RqtniK47I24y80Gnx8ptYUeAdypNdcLJ/vKTCLQnoA07TfMeZl7XqnAaJ9QIDAQAB";
		
		//String text = "123456";
		
		String text = "111111";
		
		byte[] keyBytes = Base64.decodeBase64(key);
		
		//X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		RSAPublicKeyStructure asn1PubKey = new RSAPublicKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(keyBytes));
		
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(asn1PubKey.getModulus(), asn1PubKey.getPublicExponent()); 
		
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		
		Provider PROVIDER = new BouncyCastleProvider();
		
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", PROVIDER);
		
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		
		byte[] data = cipher.doFinal(text.getBytes());
		
		System.out.println(new String(data));
		
	}
	
	public void testDecrypt1() throws Exception {
		
		String enText = "Rkqdz0POfgH/HRdoUxRiWXY9L+rTALpK4tLaj31bJpO/nWlq2cQmuuS5Zk1XzvBrvoSOUSgLgWHISGnocAWZVwmZn8M24xS6Wr+pECe7azCKRcbCidhbXoruBzmS1aumqhtWuhcZguci4RNvN4zm3LN2jUZliowL/LDWmR4kUh8=";
		
		String key = "MIICXgIBAAKBgQC9fSJ1l962Qdq7rMqCx2Wr7CxMe2H07nOogxt379kyAUZeESWudbSIXnMQU22nRJGHwitkETPeTkpswY1jGk9TBECbBmyzTJRbKSq08rDEI3yxXd6RqtniK47I24y80Gnx8ptYUeAdypNdcLJ/vKTCLQnoA07TfMeZl7XqnAaJ9QIDAQABAoGBAJU5ZZjL2AUaCYLAyd6B3wysehplFDiKTKUJUul6Bka+AEd2I4GnilvWXbEesn0Gn8EU5YzxizJn326UYp8ICizN85EI/jiKDk+gyt6BttDgX3Xf3v0nu15JmjTSD5Fo0m6hphuLEq7/fNDetQ1UgBeKi8jo6aLu9ySn1WTRTwWhAkEA+MbXeZSaVptzxW/B83h9c56/Pscg6FlQ9WsS0w0+7/FKluvAkQU2we5NwK6zJzRC38rlBMHconkNcU3pkUHtbQJBAML9mwhZ9l388Wng4NRyUnx/ZgT6FL1mioKq7vhFdc9FKjACAVmLE/FCKI4pd3MLHu/mGmXiJ/r6qu2WTdyk4akCQQCufkoE7UaUGNVLVugjbhAQWPirf+CFGKDAgyng/xl2EzjOQu3+yjluLUg8Lk1a4j1F23pnq9Kl42KaZpu9VxDBAkEAoccTp5wsQdKo4TWIk/q94Tk6BYsPRg0bgkobtrS6h9tUozwmroorY5GGYFybFEH3ywZYhItcrGjpA/Iea6AI8QJAbNRVCO2ImdT/fngoFKK40PLZSooFu0njRYcNUWGr7GkUxeS7gC3GNYWcZG+f8zbXaXsjyD0pSNeaHFDRmJCcTA==";
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		byte[] keyBytes = Base64.decodeBase64(key);
		
		RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(keyBytes));
		
		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent()); 
		
		RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		
		Provider PROVIDER = new BouncyCastleProvider();
		
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", PROVIDER);
		
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		
		byte[] data = cipher.doFinal(Base64.decodeBase64(enText));
		
		for(byte b : data) {
			System.out.print(b + ",");
		}
		
		System.out.println();
		
		byte[] result = new byte[data.length - 1];
		
		System.arraycopy(data, 0, result, 0, data.length - 1);
		
		String text = new String(result, "UTF-8");
		
		System.out.println(text);
		
		System.out.println(text.length());
		
	}
	
	public void testMD5() {
		String md5 = DigestUtils.md5Hex("admin");
		System.out.println(md5);
	}
	
	public void testGenerateKeyPair() throws Exception {
		
		Provider PROVIDER = new BouncyCastleProvider();
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", PROVIDER);
		keyPairGenerator.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		
		byte[] publicBytes = Base64.encodeBase64(publicKey.getEncoded());
		byte[] privateBytes = Base64.encodeBase64(privateKey.getEncoded());
		
		System.out.println(new String(publicBytes));
		
		System.out.println(new String(privateBytes));
		
	}
	
}
