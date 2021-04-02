package com.wangfajun.framework.api.util.desensitization;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 数据脱敏注解
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizedSerialize.class)
public @interface DesensitizedAnnotation {

	/**
	 * 脱敏类型
	 */
	DesensitizedEnum value();
	
}
