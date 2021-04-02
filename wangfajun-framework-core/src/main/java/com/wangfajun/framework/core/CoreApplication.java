package com.wangfajun.framework.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.boot.SpringApplication;

/**
 * 后台SSM基本框架
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@EnableSwagger2
@SpringBootApplication
@MapperScan("com.wangfajun.framework.mapper")
@EnableFeignClients(basePackages = {"com.wangfajun.framework.api.client"})
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}
