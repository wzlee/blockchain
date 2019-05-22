package com.leoops.blockchain.utils;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RSAUtil {

	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * 生成公私钥对
	 * @return
	 */
	public static KeyPair generateKeyPair() {
		try {
			Security.addProvider(new BouncyCastleProvider());
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			keyGen.initialize(1024, random); //256
			KeyPair keyPair = keyGen.generateKeyPair();
			return keyPair;
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 公钥转换
	 * @param PublicKey
	 * @return string key
	 * @throws Exception
	 */
	public static String getPublicKey(PublicKey publicKey) {
		return Base64.getEncoder().encodeToString(publicKey.getEncoded());
	}
	
	/**
	 * 公钥转换
	 * @param PrivateKey
	 * @return string key
	 * @throws Exception
	 */
	public static String getPrivateKey(PrivateKey privateKey) {
		return Base64.getEncoder().encodeToString(privateKey.getEncoded());
	}
	
	/**
	 * 公钥转换
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String publicKey) {
		byte[] keyBytes = Base64.getDecoder().decode(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getLocalizedMessage());
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}

	/**
	 * 私钥转换
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String privateKey) {
		byte[] keyBytes = Base64.getDecoder().decode(privateKey);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getLocalizedMessage());
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}

	/**
	 * 数据签名
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] signature(byte[] data, String privateKey) {
		PrivateKey priK = getPrivateKey(privateKey);
		Signature sig;
		try {
			sig = Signature.getInstance(SIGNATURE_ALGORITHM);
			sig.initSign(priK);
			sig.update(data);
			return sig.sign();
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			throw new RuntimeException("生成签名异常，异常信息：" + e.getLocalizedMessage());
		}
	}

	/**
	 * 验证签名
	 * 
	 * @param data
	 * @param signature
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, byte[] signature, String publicKey) {
		PublicKey pubK = getPublicKey(publicKey);
		Signature sig;
		try {
			sig = Signature.getInstance(SIGNATURE_ALGORITHM);
			sig.initVerify(pubK);
			sig.update(data);
			return sig.verify(signature);
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			throw new RuntimeException("验证签名异常，异常信息：" + e.getLocalizedMessage());
		}
	}

	/**
	 * 数据加密
	 * 
	 * @param bt_plaintext
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] bt_plaintext, String publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		byte[] bt_encrypted = cipher.doFinal(bt_plaintext);
		return bt_encrypted;
	}

	/**
	 * 数据解密
	 * 
	 * @param bt_encrypted
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] bt_encrypted, String privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
		byte[] bt_original = cipher.doFinal(bt_encrypted);
		return bt_original;
	}
	
	public static String sha256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	public static void main(String[] args) throws Exception {
		KeyPair keyPair = generateKeyPair();
		String publicKey = getPublicKey(keyPair.getPublic());
		String privateKey = getPrivateKey(keyPair.getPrivate());
		System.out.println("公钥:"+publicKey);
		System.out.println("私钥:"+privateKey);
		String str_plaintext = "这是一段用来测试密钥转换的明文";
		System.err.println("明文：" + str_plaintext);
		System.out.println("HASH：" + sha256(str_plaintext));
		byte[] bt_cipher = encrypt(str_plaintext.getBytes(), publicKey);
		System.out.println("密文：" + Base64.getEncoder().encodeToString(bt_cipher));
		byte[] bt_original = decrypt(bt_cipher, privateKey);
		String str_original = new String(bt_original);
		System.out.println("解密结果:" + str_original);
		//
		String str = "被签名的内容";
		System.err.println("\n原文:" + str);
		byte[] signature = signature(str.getBytes(), privateKey);
		System.out.println("产生签名：" + Base64.getEncoder().encodeToString(signature));
		boolean status = verify(str.getBytes(), signature, publicKey);
		System.out.println("验证情况：" + status);
	}
}
