package com.wangfajun.framework.distributedlock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 分布式锁demo
 *
 * @version 1.0
 */
@SpringBootApplication
@MapperScan(basePackages="com.wangfajun.framework.distributedlock.mapper")
public class DistributedLockApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedLockApplication.class, args);
	}

}
