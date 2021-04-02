package com.wangfajun.framework.api.aes;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 加密
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public class FrameWorkAESCipher {

	private static String ENCRYP_PREFIX = "Encrypt";

	private byte[] keyBytes;

	public FrameWorkAESCipher(String key) throws UnsupportedEncodingException {
		keyBytes = Arrays.copyOf(key.getBytes("ASCII"), 16);
	}

	public String encrypt(String data) {
		if (data != null && !data.trim().equals("")) {
			if (!data.startsWith(ENCRYP_PREFIX)) {
				byte[] encryptBytes = encrypt(data, keyBytes);
				return encryptBytes == null ? data : ENCRYP_PREFIX + Base64.encodeBase64String(encryptBytes);
			} else {
				return data;
			}
		} else {
			return data;
		}
	}

	public String decrypt(String encryptData) {
		if (encryptData != null && !encryptData.trim().equals("")) {
			if (encryptData.startsWith(ENCRYP_PREFIX)) {
				String mobile = decrypt(Base64.decodeBase64(encryptData.substring(ENCRYP_PREFIX.length())), keyBytes);
				return mobile == null ? encryptData : mobile;
			} else {
				return encryptData;
			}
		} else {
			return encryptData;
		}
	}

	private static byte[] encrypt(String clearText, byte[] keyBytes) {
		try {
			SecretKeySpec e = new SecretKeySpec(keyBytes, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(1, e);
			byte[] cleartext = clearText.getBytes("UTF-8");
			byte[] ciphertextBytes = cipher.doFinal(cleartext);
			return ciphertextBytes;
		} catch (Exception var6) {
			throw new RuntimeException("Error encrypting data", var6);
		}
	}

	private static String decrypt(byte[] encryptBytes, byte[] keyBytes) {
		try {
			SecretKeySpec e = new SecretKeySpec(keyBytes, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(2, e);
			byte[] ciphertextBytes = cipher.doFinal(encryptBytes);
			return new String(ciphertextBytes, "UTF-8");
		} catch (Exception var5) {
			throw new RuntimeException("Error decrypting data", var5);
		}
	}

	public static void main(String[] args) {
		String key = "zhundian666";
		try {
			FrameWorkAESCipher salaryAesCipher = new FrameWorkAESCipher(key);
			String mobile = salaryAesCipher.encrypt("15900415702");
			System.out.println("加密：" + mobile);

			String decrypt = salaryAesCipher.decrypt(mobile);
			System.out.println("解密：" + decrypt);

		} catch (Exception ex) {

		}
	}

}
