package com.wangfajun.framework.core.utils.encrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SM4对称加密工具类
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public class Sm4utils {

	public boolean isHexString() {
		return hexString;
	}

	public void setHexString(boolean hexString) {
		Sm4utils.hexString = hexString;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		Sm4utils.iv = iv;
	}

	private static String iv = "";

	private static boolean hexString = false;

	public Sm4utils() {
	}

	private static final Pattern CHECK_SM4 = Pattern.compile(RegexUtil.CHECK_SM4);

	public static String encryptDataEcb(String plainText, String secretKey) {
		try {
			Sm4context ctx = new Sm4context();
			ctx.isPadding = true;
			ctx.mode = Sm4.SM4_ENCRYPT;

			byte[] keyBytes;
			if (hexString) {
				keyBytes = SecurityUtils.hexStringToBytes(secretKey);
			} else {
				keyBytes = secretKey.getBytes();
			}

			Sm4 sm4 = new Sm4();
			sm4.sm4setkeyEnc(ctx, keyBytes);
			byte[] encrypted = sm4.sm4cryptEcb(ctx, plainText.getBytes("GBK"));
			String cipherText = new BASE64Encoder().encode(encrypted);
			if (cipherText != null && cipherText.trim().length() > 0) {
				Matcher m = CHECK_SM4.matcher(cipherText);
				cipherText = m.replaceAll("");
			}
			return cipherText;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decryptDataEcb(String cipherText, String secretKey) {
		try {
			Sm4context ctx = new Sm4context();
			ctx.isPadding = true;
			ctx.mode = Sm4.SM4_DECRYPT;

			byte[] keyBytes;
			if (hexString) {
				keyBytes = SecurityUtils.hexStringToBytes(secretKey);
			} else {
				keyBytes = secretKey.getBytes();
			}

			Sm4 sm4 = new Sm4();
			sm4.sm4setkeyDec(ctx, keyBytes);
			byte[] decrypted = sm4.sm4cryptEcb(ctx, new BASE64Decoder().decodeBuffer(cipherText));
			return new String(decrypted, "GBK");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String encryptDataCbc(String plainText, String secretKey) {
		try {
			Sm4context ctx = new Sm4context();
			ctx.isPadding = true;
			ctx.mode = Sm4.SM4_ENCRYPT;

			byte[] keyBytes;
			byte[] ivBytes;
			if (hexString) {
				keyBytes = SecurityUtils.hexStringToBytes(secretKey);
				ivBytes = SecurityUtils.hexStringToBytes(iv);
			} else {
				keyBytes = secretKey.getBytes();
				ivBytes = iv.getBytes();
			}

			Sm4 sm4 = new Sm4();
			sm4.sm4setkeyEnc(ctx, keyBytes);
			byte[] encrypted = sm4.sm4cryptCbc(ctx, ivBytes, plainText.getBytes("GBK"));
			String cipherText = new BASE64Encoder().encode(encrypted);
			if (cipherText != null && cipherText.trim().length() > 0) {
				Matcher m = CHECK_SM4.matcher(cipherText);
				cipherText = m.replaceAll("");
			}
			return cipherText;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decryptDataCbc(String cipherText, String secretKey) {
		try {
			Sm4context ctx = new Sm4context();
			ctx.isPadding = true;
			ctx.mode = Sm4.SM4_DECRYPT;

			byte[] keyBytes;
			byte[] ivBytes;
			if (hexString) {
				keyBytes = SecurityUtils.hexStringToBytes(secretKey);
				ivBytes = SecurityUtils.hexStringToBytes(iv);
			} else {
				keyBytes = secretKey.getBytes();
				ivBytes = iv.getBytes();
			}

			Sm4 sm4 = new Sm4();
			sm4.sm4setkeyDec(ctx, keyBytes);
			byte[] decrypted = sm4.sm4cryptCbc(ctx, ivBytes, new BASE64Decoder().decodeBuffer(cipherText));
			return new String(decrypted, "GBK");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

//	public static void main(String[] args) throws Exception {
//		String ticket = Sm4utils.encryptDataEcb(String.valueOf("1"), MemberConstants.MEMBER_TENANT_ID_SM4_SECRET);
//		System.out.println(ticket);
//	}

}
