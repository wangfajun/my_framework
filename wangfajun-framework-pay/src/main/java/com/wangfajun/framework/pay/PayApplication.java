package com.wangfajun.framework.pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 支付服务启动类
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages="com.wangfajun.framework.pay.mapper")
public class PayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayApplication.class, args);
	}

}
