package com.wangfajun.framework.utils.encrypt;

/**
 * 国密SM3
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public class Sm3 {

	public static final byte[] IV = {0x73, (byte) 0x80, 0x16, 0x6f, 0x49,
			0x14, (byte) 0xb2, (byte) 0xb9, 0x17, 0x24, 0x42, (byte) 0xd7,
			(byte) 0xda, (byte) 0x8a, 0x06, 0x00, (byte) 0xa9, 0x6f, 0x30,
			(byte) 0xbc, (byte) 0x16, 0x31, 0x38, (byte) 0xaa, (byte) 0xe3,
			(byte) 0x8d, (byte) 0xee, 0x4d, (byte) 0xb0, (byte) 0xfb, 0x0e,
			0x4e};

	public static int[] Tj = new int[64];

	private static int four = 4;

	private static int sixteen = 16;

	private static int fifteen = 15;

	private static int sixtyFour = 64;

	private static int sixtyEight = 68;

	static {
		for (int i = 0; i < sixteen; i++) {
			Tj[i] = 0x79cc4519;
		}

		for (int i = sixteen; i < sixtyFour; i++) {
			Tj[i] = 0x7a879d8a;
		}
	}

	public static byte[] cf(byte[] one, byte[] two) {
		int[] v, b;
		v = convert(one);
		b = convert(two);
		return convert(cf(v, b));
	}

	private static int[] convert(byte[] arr) {
		int[] out = new int[arr.length / 4];
		byte[] tmp = new byte[4];
		for (int i = 0; i < arr.length; i += four) {
			System.arraycopy(arr, i, tmp, 0, 4);
			out[i / 4] = bigEndianByteToInt(tmp);
		}
		return out;
	}

	private static byte[] convert(int[] arr) {
		byte[] out = new byte[arr.length * 4];
		byte[] tmp = null;
		for (int i = 0; i < arr.length; i++) {
			tmp = bigEndianIntToByte(arr[i]);
			System.arraycopy(tmp, 0, out, i * 4, 4);
		}
		return out;
	}

	public static int[] cf(int[] one, int[] two) {
		int a, b, c, d, e, f, g, h;
		int ss1, ss2, tt1, tt2;
		a = one[0];
		b = one[1];
		c = one[2];
		d = one[3];
		e = one[4];
		f = one[5];
		g = one[6];
		h = one[7];

		int[][] arr = expand(two);
		int[] w = arr[0];
		int[] w1 = arr[1];

		for (int j = 0; j < sixtyFour; j++) {
			ss1 = (bitCycleLeft(a, 12) + e + bitCycleLeft(Tj[j], j));
			ss1 = bitCycleLeft(ss1, 7);
			ss2 = ss1 ^ bitCycleLeft(a, 12);
			tt1 = ffj(a, b, c, j) + d + ss2 + w1[j];
			tt2 = ggj(e, f, g, j) + h + ss1 + w[j];
			d = c;
			c = bitCycleLeft(b, 9);
			b = a;
			a = tt1;
			h = g;
			g = bitCycleLeft(f, 19);
			f = e;
			e = p0(tt2);

		}

		int[] out = new int[8];
		out[0] = a ^ one[0];
		out[1] = b ^ one[1];
		out[2] = c ^ one[2];
		out[3] = d ^ one[3];
		out[4] = e ^ one[4];
		out[5] = f ^ one[5];
		out[6] = g ^ one[6];
		out[7] = h ^ one[7];

		return out;
	}

	private static int[][] expand(int[] one) {
		int[] w = new int[68];
		int[] w1 = new int[64];
		for (int i = 0; i < one.length; i++) {
			w[i] = one[i];
		}

		for (int i = sixteen; i < sixtyEight; i++) {
			w[i] = p1(w[i - 16] ^ w[i - 9] ^ bitCycleLeft(w[i - 3], 15))
					^ bitCycleLeft(w[i - 13], 7) ^ w[i - 6];
		}

		for (int i = 0; i < sixtyFour; i++) {
			w1[i] = w[i] ^ w[i + 4];
		}

		int[][] arr = new int[][]{w, w1};
		return arr;
	}

	private static byte[] bigEndianIntToByte(int num) {
		return back(SecurityUtils.intToBytes(num));
	}

	private static int bigEndianByteToInt(byte[] bytes) {
		return SecurityUtils.byteToInt(back(bytes));
	}

	private static int ffj(int x, int y, int z, int j) {
		if (j >= 0 && j <= fifteen) {
			return ff1j(x, y, z);
		} else {
			return ff2j(x, y, z);
		}
	}

	private static int ggj(int x, int y, int z, int j) {
		if (j >= 0 && j <= fifteen) {
			return gg1j(x, y, z);
		} else {
			return gg2j(x, y, z);
		}
	}

	/**
	 * 逻辑位运算函数
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	private static int ff1j(int x, int y, int z) {
		int tmp = x ^ y ^ z;
		return tmp;
	}

	private static int ff2j(int x, int y, int z) {
		int tmp = ((x & y) | (x & z) | (y & z));
		return tmp;
	}

	private static int gg1j(int x, int y, int z) {
		int tmp = x ^ y ^ z;
		return tmp;
	}

	private static int gg2j(int x, int y, int z) {
		int tmp = (x & y) | (~x & z);
		return tmp;
	}

	private static int p0(int x) {
		int y = rotateLeft(x, 9);
		y = bitCycleLeft(x, 9);
		int z = rotateLeft(x, 17);
		z = bitCycleLeft(x, 17);
		int t = x ^ y ^ z;
		return t;
	}

	private static int p1(int x) {
		int t = x ^ bitCycleLeft(x, 15) ^ bitCycleLeft(x, 23);
		return t;
	}

	/**
	 * 对最后一个分组字节数据padding
	 *
	 * @param in
	 * @param bLen 分组个数
	 * @return
	 */
	public static byte[] padding(byte[] in, int bLen) {
		int k = 448 - (8 * in.length + 1) % 512;
		if (k < 0) {
			k = 960 - (8 * in.length + 1) % 512;
		}
		k += 1;
		byte[] padd = new byte[k / 8];
		padd[0] = (byte) 0x80;
		long n = in.length * 8 + bLen * 512;
		byte[] out = new byte[in.length + k / 8 + 64 / 8];
		int pos = 0;
		System.arraycopy(in, 0, out, 0, in.length);
		pos += in.length;
		System.arraycopy(padd, 0, out, pos, padd.length);
		pos += padd.length;
		byte[] tmp = back(SecurityUtils.longToBytes(n));
		System.arraycopy(tmp, 0, out, pos, tmp.length);
		return out;
	}

	/**
	 * 字节数组逆序
	 *
	 * @param in
	 * @return
	 */
	private static byte[] back(byte[] in) {
		byte[] out = new byte[in.length];
		for (int i = 0; i < out.length; i++) {
			out[i] = in[out.length - i - 1];
		}

		return out;
	}

	public static int rotateLeft(int x, int n) {
		return (x << n) | (x >> (32 - n));
	}

	private static int bitCycleLeft(int n, int bitLen) {
		bitLen %= 32;
		byte[] tmp = bigEndianIntToByte(n);
		int byteLen = bitLen / 8;
		int len = bitLen % 8;
		if (byteLen > 0) {
			tmp = byteCycleLeft(tmp, byteLen);
		}

		if (len > 0) {
			tmp = bitSmall8CycleLeft(tmp, len);
		}

		return bigEndianByteToInt(tmp);
	}

	private static byte[] bitSmall8CycleLeft(byte[] in, int len) {
		byte[] tmp = new byte[in.length];
		int t1, t2, t3;
		for (int i = 0; i < tmp.length; i++) {
			t1 = (byte) ((in[i] & 0x000000ff) << len);
			t2 = (byte) ((in[(i + 1) % tmp.length] & 0x000000ff) >> (8 - len));
			t3 = (byte) (t1 | t2);
			tmp[i] = (byte) t3;
		}

		return tmp;
	}

	private static byte[] byteCycleLeft(byte[] in, int byteLen) {
		byte[] tmp = new byte[in.length];
		System.arraycopy(in, byteLen, tmp, 0, in.length - byteLen);
		System.arraycopy(in, 0, tmp, in.length - byteLen, byteLen);
		return tmp;
	}

}
