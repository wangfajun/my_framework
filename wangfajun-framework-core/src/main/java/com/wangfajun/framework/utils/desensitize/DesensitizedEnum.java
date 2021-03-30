package com.wangfajun.framework.utils.desensitize;

import lombok.Getter;

/**
 * @author wy
 * @Description 脱敏类型
 * @Date 2020-12-30 17:49:15
 */
@Getter
public enum DesensitizedEnum {
	/**
	 * 中文名
	 */
	CHINESE_NAME,
	/**
	 * 身份证号
	 */
	ID_CARD,
	/**
	 * 座机号
	 */
	FIXED_PHONE,
	/**
	 * 手机号
	 */
	MOBILE_PHONE,
	/**
	 * 地址
	 */
	ADDRESS,
	/**
	 * 电子邮件
	 */
	EMAIL,
	/**
	 * 银行卡
	 */
	BANK_CARD,
	/**
	 * 密码
	 */
	PASSWORD;
}