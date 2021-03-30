package com.wangfajun.framework.utils.encrypt;

/**
 * 国密SM3
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public class Sm3digest {

	/**
	 * SM3值的长度
	 */
	private static final int BYTE_LENGTH = 32;

	/**
	 * SM3分组长度
	 */
	private static final int BLOCK_LENGTH = 64;

	/**
	 * 缓冲区长度
	 */
	private static final int BUFFER_LENGTH = BLOCK_LENGTH * 1;

	/**
	 * 缓冲区
	 */
	private byte[] xBuf = new byte[BUFFER_LENGTH];

	/**
	 * 缓冲区偏移量
	 */
	private int xBufOff;

	/**
	 * 初始向量
	 */
	private byte[] v = Sm3.IV.clone();

	private int cntBlock = 0;

	public Sm3digest() {
	}

	public Sm3digest(Sm3digest t) {
		System.arraycopy(t.xBuf, 0, this.xBuf, 0, t.xBuf.length);
		this.xBufOff = t.xBufOff;
		System.arraycopy(t.v, 0, this.v, 0, t.v.length);
	}

	/**
	 * SM3结果输出
	 *
	 * @param out    保存SM3结构的缓冲区
	 * @param outOff 缓冲区偏移量
	 * @return
	 */
	public int doFinal(byte[] out, int outOff) {
		byte[] tmp = doFinal();
		System.arraycopy(tmp, 0, out, 0, tmp.length);
		return BYTE_LENGTH;
	}

	public void reset() {
		xBufOff = 0;
		cntBlock = 0;
		v = Sm3.IV.clone();
	}

	/**
	 * 明文输入
	 *
	 * @param in    明文输入缓冲区
	 * @param inOff 缓冲区偏移量
	 * @param len   明文长度
	 */
	public void update(byte[] in, int inOff, int len) {
		int partLen = BUFFER_LENGTH - xBufOff;
		int inputLen = len;
		int dPos = inOff;
		if (partLen < inputLen) {
			System.arraycopy(in, dPos, xBuf, xBufOff, partLen);
			inputLen -= partLen;
			dPos += partLen;
			doUpdate();
			while (inputLen > BUFFER_LENGTH) {
				System.arraycopy(in, dPos, xBuf, 0, BUFFER_LENGTH);
				inputLen -= BUFFER_LENGTH;
				dPos += BUFFER_LENGTH;
				doUpdate();
			}
		}

		System.arraycopy(in, dPos, xBuf, xBufOff, inputLen);
		xBufOff += inputLen;
	}

	private void doUpdate() {
		byte[] b = new byte[BLOCK_LENGTH];
		for (int i = 0; i < BUFFER_LENGTH; i += BLOCK_LENGTH) {
			System.arraycopy(xBuf, i, b, 0, b.length);
			doHash(b);
		}
		xBufOff = 0;
	}

	private void doHash(byte[] b) {
		byte[] tmp = Sm3.cf(v, b);
		System.arraycopy(tmp, 0, v, 0, v.length);
		cntBlock++;
	}

	private byte[] doFinal() {
		byte[] b = new byte[BLOCK_LENGTH];
		byte[] buffer = new byte[xBufOff];
		System.arraycopy(xBuf, 0, buffer, 0, buffer.length);
		byte[] tmp = Sm3.padding(buffer, cntBlock);
		for (int i = 0; i < tmp.length; i += BLOCK_LENGTH) {
			System.arraycopy(tmp, i, b, 0, b.length);
			doHash(b);
		}
		return v;
	}

	public void update(byte in) {
		byte[] buffer = new byte[]{in};
		update(buffer, 0, 1);
	}

	public int getDigestSize() {
		return BYTE_LENGTH;
	}

//	public static void main(String[] args) {
//		byte[] md = new byte[32];
//		byte[] msg1 = "ererfeiisgod".getBytes();
//		Sm3digest sm3 = new Sm3digest();
//		sm3.update(msg1, 0, msg1.length);
//		sm3.doFinal(md, 0);
//		String s = new String(Hex.encode(md));
//		System.out.println(s.toUpperCase());
//	}

}
