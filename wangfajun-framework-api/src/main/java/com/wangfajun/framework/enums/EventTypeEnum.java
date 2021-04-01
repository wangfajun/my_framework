package com.wangfajun.framework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 事件类型枚举
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Getter
@AllArgsConstructor
public enum EventTypeEnum {

	/**
	 * 支付事件
	 */
    PAY_EVENT(1, "支付事件"),

	/**
	 * 订单事件
	 */
	ORDER_EVENT(2, "订单事件");

    /**
     * 状态值
     */
    private Integer type;

    /**
     * 描述
     */
    private String description;

}
