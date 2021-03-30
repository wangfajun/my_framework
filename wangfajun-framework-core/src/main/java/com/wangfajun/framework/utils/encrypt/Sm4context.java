package com.wangfajun.framework.utils.encrypt;

/**
 * 国密工SM4具类
 * @author wangfajun
 * @version 1.0
 * @date 2020/10/22 11:17
 */
public class Sm4context {
	public int mode;

	public long[] sk;

	public boolean isPadding;

	public Sm4context() {
		this.mode = 1;
		this.isPadding = true;
		this.sk = new long[32];
	}

}
