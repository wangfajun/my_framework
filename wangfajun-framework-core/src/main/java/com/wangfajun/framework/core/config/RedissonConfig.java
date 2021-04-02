package com.wangfajun.framework.core.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson配置类
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Configuration
public class RedissonConfig {

	/**
	 * redis地址
	 */
	@Value("${spring.redis.host}")
	private String host;

	/**
	 * redis密码
	 */
	@Value("${spring.redis.password}")
	private String password;

	/**
	 * 端口
	 */
	@Value("${spring.redis.port}")
	private Integer port;

	/**
	 * 连接超时时间（毫秒）
	 */
	@Value("${spring.redis.timeout}")
	private Integer timeout;

	/**
	 * Redisson 单机模式
	 */
	@Bean(destroyMethod = "shutdown")
	public RedissonClient createRedissonClient() {
		Config config = new Config();
		SingleServerConfig singleServerConfig = config.useSingleServer();
		String redisHost = "redis://" + host + ":";
		singleServerConfig.setAddress(redisHost + port);
		singleServerConfig.setTimeout(timeout);
		singleServerConfig.setPassword(password);
		return Redisson.create(config);
	}

}
