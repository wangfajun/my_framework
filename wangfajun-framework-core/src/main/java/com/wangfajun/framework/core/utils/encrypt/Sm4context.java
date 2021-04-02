package com.wangfajun.framework.core.utils.encrypt;

/**
 * 国密工SM4具类
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
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
