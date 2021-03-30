package com.wangfajun.framework.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author zhangzhengguang
 * @date 2021/3/26 2:18 下午
 */
@Configuration
public class FlexibleFeignConfig {
	@Bean
	public RequestInterceptor headerInterceptor() {
		return template -> {
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (null != attributes) {
				HttpServletRequest request = attributes.getRequest();
				Enumeration<String> headerNames = request.getHeaderNames();
				if (headerNames != null) {
					while (headerNames.hasMoreElements()) {
						String name = headerNames.nextElement();
						String values = request.getHeader(name);
						template.header(name, values);
					}
				}
			}
		};
	}
}
