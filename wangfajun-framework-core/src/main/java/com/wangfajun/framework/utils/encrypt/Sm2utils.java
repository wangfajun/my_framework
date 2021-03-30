package com.wangfajun.framework.utils.encrypt;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * SM2非对称加密工具类
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public class Sm2utils {

	/**
	 * 生成随机秘钥对
	 *
	 * @return
	 */
	public static Map<String, String> generateKeyPair() {
		Sm2 sm2 = Sm2.instance();
		AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
		ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
		ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
		//解密密钥
		BigInteger privateKey = ecpriv.getD();
		//加密密钥
		ECPoint publicKey = ecpub.getQ();
		Map<String, String> resutlMap = new HashMap<>();

		System.out.println("privateKey :" + SecurityUtils.byteToHex(privateKey.toByteArray()));
		System.out.println("publicKey:" + SecurityUtils.byteToHex(publicKey.getEncoded()));
		resutlMap.put("privateKey", SecurityUtils.byteToHex(privateKey.toByteArray()));
		resutlMap.put("publicKey", SecurityUtils.byteToHex(publicKey.getEncoded()));
		return resutlMap;
	}

	/**
	 * 数据加密
	 *
	 * @param publicKey
	 * @param data
	 * @return
	 */
	public static String encrypt(byte[] publicKey, byte[] data) {
		if (publicKey == null || publicKey.length == 0) {
			return null;
		}

		if (data == null || data.length == 0) {
			return null;
		}

		byte[] source = new byte[data.length];
		System.arraycopy(data, 0, source, 0, data.length);

		Cipher cipher = new Cipher();
		Sm2 sm2 = Sm2.instance();
		ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);

		ECPoint c1 = cipher.initEnc(sm2, userKey);
		cipher.encrypt(source);
		byte[] c3 = new byte[32];
		cipher.dofinal(c3);

		//C1 C2 C3拼装成加密字串
		return SecurityUtils.byteToHex(c1.getEncoded()) + SecurityUtils.byteToHex(source) + SecurityUtils.byteToHex(c3);

	}

	/**
	 * 数据解密
	 *
	 * @param privateKey
	 * @param encryptedData
	 * @return
	 */
	public static byte[] decrypt(byte[] privateKey, byte[] encryptedData) {
		if (privateKey == null || privateKey.length == 0) {
			return null;
		}

		if (encryptedData == null || encryptedData.length == 0) {
			return null;
		}
		//加密字节数组转换为十六进制的字符串 长度变为encryptedData.length * 2
		String data = SecurityUtils.byteToHex(encryptedData);
		/***分解加密字串
		 * （C1 = C1标志位2位 + C1实体部分128位 = 130）
		 * （C3 = C3实体部分64位  = 64）
		 * （C2 = encryptedData.length * 2 - C1长度  - C2长度）
		 */
		byte[] c1Bytes = SecurityUtils.hexToByte(data.substring(0, 130));
		int c2Len = encryptedData.length - 97;
		byte[] c2 = SecurityUtils.hexToByte(data.substring(130, 130 + 2 * c2Len));
		byte[] c3 = SecurityUtils.hexToByte(data.substring(130 + 2 * c2Len, 194 + 2 * c2Len));

		Sm2 sm2 = Sm2.instance();
		BigInteger userD = new BigInteger(1, privateKey);

		//通过C1实体字节来生成ECPoint
		ECPoint c1 = sm2.ecc_curve.decodePoint(c1Bytes);
		Cipher cipher = new Cipher();
		cipher.initDec(userD, c1);
		cipher.decrypt(c2);
		cipher.dofinal(c3);

		//返回解密结果
		return c2;
	}

//	public static void main(String[] args) throws Exception {
//		//生成密钥对
//		Map<String, String> keyMap = generateKeyPair();
//
//		String plainText = "{\"name\":\"北京银行\"}";
//
//		//加密密钥
//		String publicKey = keyMap.get("publicKey");
//
//		//解密蜜月
//		String privateKey = keyMap.get("privateKey");
//
//		String encString = Sm2utils.encrypt(SecurityUtils.hexStringToBytes(publicKey), plainText.getBytes());
//		System.out.println("密文：" + encString);
//
//		byte[] plainString = Sm2utils.decrypt(SecurityUtils.hexStringToBytes(privateKey), SecurityUtils.hexStringToBytes(encString));
//		System.out.println(new String(plainString));
//
//	}

}
