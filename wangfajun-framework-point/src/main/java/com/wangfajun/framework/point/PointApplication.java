package com.wangfajun.framework.point;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 积分服务
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@MapperScan(basePackages="com.wangfajun.framework.point.mapper")
@SpringBootApplication
@EnableScheduling
public class PointApplication {

	public static void main(String[] args) {
		SpringApplication.run(PointApplication.class, args);
	}

}
