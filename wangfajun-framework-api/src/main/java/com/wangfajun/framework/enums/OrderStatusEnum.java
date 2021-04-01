package com.wangfajun.framework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单支付状态枚举
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

	/**
	 * 待支付
	 */
    NEED_PAY(0, "待支付"),

	/**
	 * 已支付
	 */
	PAYED(1, "已支付");

	/**
     * 状态值
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;

}
