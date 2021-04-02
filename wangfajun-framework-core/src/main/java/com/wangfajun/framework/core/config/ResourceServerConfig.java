package com.wangfajun.framework.core.config;

import com.wangfajun.framework.core.exception.AuthExceptionEntryPoint;
import com.wangfajun.framework.core.properties.PermitAllUrlProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 资源服务器配置放行接口 标识为资源服务器，请求服务中的资源，就要带着token过来
 * 找不到token或token是无效访问不了资源 开启方法级别权限控制
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private PermitAllUrlProperties permitAllUrlProperties;

	@Autowired
	private TokenStore tokenStore;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// token存储方式
		resources.tokenStore(tokenStore);
		// oauth2异常拦截
		resources.authenticationEntryPoint(new AuthExceptionEntryPoint());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		// 跨域处理
		http.cors();

		// 允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
		http.headers().frameOptions().disable();
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
		permitAllUrlProperties.getIgnoreUrls().forEach(url -> registry.antMatchers(url).permitAll());

		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/**").authenticated()
				.anyRequest().authenticated()
				.and().csrf().disable();
	}

}
