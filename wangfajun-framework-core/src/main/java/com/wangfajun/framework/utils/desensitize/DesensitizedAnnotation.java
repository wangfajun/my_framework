package com.wangfajun.framework.utils.desensitize;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 数据脱敏注解
 *
 * @author wy
 * @program zhundian-salary
 * @date 2020-12-30 18:06:25
 **/
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizedSerialize.class)
public @interface DesensitizedAnnotation {
	/**
	 * 脱敏类型
	 */
	DesensitizedEnum value();
}
