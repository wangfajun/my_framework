package com.wangfajun.framework.utils.encrypt;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * 国密工具类
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public class Cipher {
	private int ct;
	private ECPoint p2;
	private Sm3digest sm3keybase;
	private Sm3digest sm3c3;
	private byte[] key;
	private byte keyOff;

	public Cipher() {
		this.ct = 1;
		this.key = new byte[32];
		this.keyOff = 0;
	}

	private void reset() {
		this.sm3keybase = new Sm3digest();
		this.sm3c3 = new Sm3digest();

		byte[] p = SecurityUtils.byteConvert32Bytes(p2.getX().toBigInteger());
		this.sm3keybase.update(p, 0, p.length);
		this.sm3c3.update(p, 0, p.length);

		p = SecurityUtils.byteConvert32Bytes(p2.getY().toBigInteger());
		this.sm3keybase.update(p, 0, p.length);
		this.ct = 1;
		nextKey();
	}

	private void nextKey() {
		Sm3digest sm3keycur = new Sm3digest(this.sm3keybase);
		sm3keycur.update((byte) (ct >> 24 & 0xff));
		sm3keycur.update((byte) (ct >> 16 & 0xff));
		sm3keycur.update((byte) (ct >> 8 & 0xff));
		sm3keycur.update((byte) (ct & 0xff));
		sm3keycur.doFinal(key, 0);
		this.keyOff = 0;
		this.ct++;
	}

	public ECPoint initEnc(Sm2 sm2, ECPoint userKey) {
		AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
		ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
		ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
		BigInteger k = ecpriv.getD();
		ECPoint c1 = ecpub.getQ();
		this.p2 = userKey.multiply(k);
		reset();
		return c1;
	}

	public void encrypt(byte[] data) {
		this.sm3c3.update(data, 0, data.length);
		for (int i = 0; i < data.length; i++) {
			if (keyOff == key.length) {
				nextKey();
			}
			data[i] ^= key[keyOff++];
		}
	}

	public void initDec(BigInteger userD, ECPoint c1) {
		this.p2 = c1.multiply(userD);
		reset();
	}

	public void decrypt(byte[] data) {
		for (int i = 0; i < data.length; i++) {
			if (keyOff == key.length) {
				nextKey();
			}
			data[i] ^= key[keyOff++];
		}

		this.sm3c3.update(data, 0, data.length);
	}

	public void dofinal(byte[] c3) {
		byte[] p = SecurityUtils.byteConvert32Bytes(p2.getY().toBigInteger());
		this.sm3c3.update(p, 0, p.length);
		this.sm3c3.doFinal(c3, 0);
		reset();
	}

}
