package com.wangfajun.framework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 删除标志枚举
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Getter
@AllArgsConstructor
public enum DelFlag {

	/**
	 * 有效
	 */
    VALID(0, "有效"),

	/**
	 * 无效
	 */
    INVALID(1, "无效");

    /**
     * 删除标志值
     */
    private Integer value;

    /**
     * 描述
     */
    private String desc;

}
