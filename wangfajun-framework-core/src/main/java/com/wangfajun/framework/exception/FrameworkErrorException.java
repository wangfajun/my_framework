package com.wangfajun.framework.exception;

import lombok.Getter;

/**
 * 服务自定义异常
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Getter
public class FrameworkErrorException extends RuntimeException {

	/**
	 * 业务异常返回码
	 */
	private String bizCode;

	/**
	 * 异常信息描述
	 */
	private String msg;

	public FrameworkErrorException(FrameWorkErrorCode serviceErrorCode) {
		super(serviceErrorCode.getMsg());
		this.bizCode = serviceErrorCode.getBizCode();
		this.msg = serviceErrorCode.getMsg();
	}

	public FrameworkErrorException(String msg) {
		super(msg);
		this.bizCode = "-1";
		this.msg = msg;
	}

	public FrameworkErrorException(String code, String msg) {
		super(msg);
		this.bizCode = code;
		this.msg = msg;
	}

}
