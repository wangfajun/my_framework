package com.wangfajun.framework.point;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 积分服务
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */

@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages="com.wangfajun.framework.point.mapper")
public class PointApplication {

	public static void main(String[] args) {
		SpringApplication.run(PointApplication.class, args);
	}

}
