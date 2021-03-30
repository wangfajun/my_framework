package com.wangfajun.framework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 员工状态枚举
 *
 * @author wangfajun
 */
@Getter
@AllArgsConstructor
public enum UserInfoStatusEnum {

	/**
	 * 正常
	 */
    USER_AVAILABLE(0, "正常"),

	/**
	 * 已冻结
	 */
	USER_FORBIDDEN(1, "已冻结");

    /**
     * 状态值
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;

}
