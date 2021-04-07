package com.wangfajun.framework.pay.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 自定义RestTemplate实例
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Configuration
public class RestTemplateConfig {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate(httpRequestFactory());
	}

	public ClientHttpRequestFactory httpRequestFactory() {
		return new HttpComponentsClientHttpRequestFactory(httpClient());
	}

	public HttpClient httpClient() {
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", SSLConnectionSocketFactory.getSocketFactory())
				.build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
		//设置整个连接池最大连接数 根据自己的场景决定
		connectionManager.setMaxTotal(100);
		//路由是对maxTotal的细分
		connectionManager.setDefaultMaxPerRoute(100);
		RequestConfig requestConfig = RequestConfig.custom()
				//服务器返回数据(response)的时间，超过该时间抛出read timeout
				.setSocketTimeout(10000)
				//连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
				.setConnectTimeout(5000)
				//从连接池中获取连接的超时时间，超过该时间未拿到可用连接，
				// 会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
				.setConnectionRequestTimeout(1000)
				.build();
		return HttpClientBuilder.create()
				.setDefaultRequestConfig(requestConfig)
				.setConnectionManager(connectionManager)
				.build();
	}

}