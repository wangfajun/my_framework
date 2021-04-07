package com.wangfajun.framework.order;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 订单服务启动类
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@EnableScheduling
@SpringBootApplication
@EnableDistributedTransaction
@MapperScan(basePackages="com.wangfajun.framework.order.mapper")
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

}
