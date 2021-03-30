package com.wangfajun.framework.config;

import com.alibaba.druid.pool.DruidDataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * 令牌管理
 *
 * @program: zhundian-contract
 * @author: wangfajun
 */
@Configuration
public class TokenConfig {

	/**
	 * jdbc管理token
	 *
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return new DruidDataSource();
	}

	/**
	 * Jwt管理令牌
	 *
	 * @return
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource());
	}

}
