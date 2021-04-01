package com.wangfajun.framework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 事件状态枚举
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Getter
@AllArgsConstructor
public enum EventStatusEnum {

	/**
	 * 新事件
	 */
    NEW(1, "新事件"),

	/**
	 * 已发送
	 */
	SEND(2, "已发送"),

	/**
	 * 已接受
	 */
	RECEIVED(3, "已接受"),

	/**
	 * 已处理
	 */
	FINISHED(4, "已处理");

	/**
     * 状态值
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;

}
