package com.wangfajun.framework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.boot.SpringApplication;

/**
 * @author Administrator
 */
@EnableSwagger2
@SpringBootApplication
@MapperScan("com.wangfajun.framework.mapper")
@EnableFeignClients(basePackages = {"com.wangfajun.framework.client"})
public class FrameworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrameworkApplication.class, args);
	}

}
