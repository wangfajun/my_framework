package com.wangfajun.framework.tm;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 分布式事务协调管理器
 *
 * @author https://www.codingapi.com/docs/txlcn-preface/
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@SpringBootApplication
@EnableTransactionManagerServer
public class TmApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmApplication.class, args);
	}

}
