package com.wangfajun.framework.config.web;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * <p>
 * cors配置
 * </p>
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Configuration
@ConfigurationProperties(prefix = "globalcors.configurations")
@Setter
public class GlobalCorsConfig {

	private List<String> origin;

	@Bean
	public CorsFilter corsFilter() {
		//1.添加CORS配置信息
		CorsConfiguration config = new CorsConfiguration();
		//1) 允许的域,不要写*，否则cookie就无法使用了
		if (ObjectUtils.isNotNull(origin)) {
			origin.forEach(e -> {
				config.addAllowedOrigin(e);
			});
		} else {
			config.addAllowedOrigin("*");
		}
		//2）允许的头信息
		config.addAllowedHeader("*");
		//3) 允许的请求方式
		config.addAllowedMethod("*");
		//4) 是否发送Cookie信息
		config.setAllowCredentials(true);

		//2.添加映射路径
		UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
		configSource.registerCorsConfiguration("/**", config);

		//3.返回新的CorsFilter.
		return new CorsFilter(configSource);
	}

}
