package com.wangfajun.framework.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * demo状态枚举
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Getter
@AllArgsConstructor
public enum DemoStatusEnum {

	/**
	 * 正常
	 */
    DEMO_AVAILABLE(0, "正常"),

	/**
	 * 已冻结
	 */
	DEMO_FORBIDDEN(1, "已冻结");

    /**
     * 状态值
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;

}
