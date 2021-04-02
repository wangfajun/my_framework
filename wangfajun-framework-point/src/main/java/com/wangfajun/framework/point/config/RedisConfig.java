package com.wangfajun.framework.point.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.time.Duration;

/**
 * redis配置类
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	/**
	 * Redis服务器地址
	 */
	@Value("${spring.redis.host}")
	private String host;

	/**
	 * Redis服务器连接端口
	 */
	@Value("${spring.redis.port}")
	private Integer port;

	/**
	 * Redis数据库索引（默认为0）
	 */
	@Value("${spring.redis.database}")
	private Integer database;

	/**
	 * Redis服务器连接密码（默认为空）
	 */
	@Value("${spring.redis.password}")
	private String password;

	/**
	 * 连接超时时间（毫秒）
	 */
	@Value("${spring.redis.timeout}")
	private Integer timeout;

	/**
	 * 连接池最大连接数（使用负值表示没有限制）
	 */
	@Value("${spring.redis.lettuce.pool.max-active}")
	private Integer maxTotal;

	/**
	 * 连接池最大阻塞等待时间（使用负值表示没有限制）
	 */
	@Value("${spring.redis.lettuce.pool.max-wait}")
	private Integer maxWait;

	/**
	 * 连接池中的最大空闲连接
	 */
	@Value("${spring.redis.lettuce.pool.max-idle}")
	private Integer maxIdle;

	/**
	 * 连接池中的最小空闲连接
	 */
	@Value("${spring.redis.lettuce.pool.min-idle}")
	private Integer minIdle;

	/**
	 * 关闭超时时间
	 */
	@Value("${spring.redis.lettuce.shutdown-timeout}")
	private Integer shutdown;

	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return (target, method, params) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(target.getClass().getName());
			sb.append(method.getName());
			for (Object obj : params) {
				sb.append(obj.toString());
			}
			return sb.toString();
		};
	}

	/**
	 * 缓存配置管理器
	 */
	@Bean
	@Override
	public CacheManager cacheManager() {
		//以锁写入的方式创建RedisCacheWriter对象
		RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(getConnectionFactory());
        /*
        设置CacheManager的Value序列化方式为JdkSerializationRedisSerializer,
        但其实RedisCacheConfiguration默认就是使用
        StringRedisSerializer序列化key，
        JdkSerializationRedisSerializer序列化value,
        所以以下注释代码就是默认实现，没必要写，直接注释掉
         */
		// RedisSerializationContext.SerializationPair pair = RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer(this.getClass().getClassLoader()));
		// RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
		//创建默认缓存配置对象
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
		return new RedisCacheManager(writer, config);
	}

	/**
	 * 获取缓存操作助手对象
	 *
	 * @return
	 */
	@Bean
	public RedisTemplate<String, String> redisTemplate() {
		//创建Redis缓存操作助手RedisTemplate对象
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(getConnectionFactory());
		//以下代码为将RedisTemplate的Value序列化方式由JdkSerializationRedisSerializer更换为Jackson2JsonRedisSerializer
		//此种序列化方式结果清晰、容易阅读、存储字节少、速度快，所以推荐更换
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setKeySerializer(new StringRedisSerializer());//RedisTemplate对象需要指明Key序列化方式，如果声明StringRedisTemplate对象则不需要
		//template.setEnableTransactionSupport(true);//是否启用事务
		template.afterPropertiesSet();
		return template;
	}

	/**
	 * 获取缓存连接
	 *
	 * @return
	 */
	@Bean
	public RedisConnectionFactory getConnectionFactory() {
		//单机模式
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(host);
		configuration.setPort(port);
		configuration.setDatabase(database);
		configuration.setPassword(RedisPassword.of(password));
		//哨兵模式
		//RedisSentinelConfiguration configuration1 = new RedisSentinelConfiguration();
		//集群模式
		//RedisClusterConfiguration configuration2 = new RedisClusterConfiguration();
		LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration, getPoolConfig());
		//factory.setShareNativeConnection(false);//是否允许多个线程操作共用同一个缓存连接，默认true，false时每个操作都将开辟新的连接
		return factory;
	}

	/**
	 * 获取缓存连接池
	 *
	 * @return
	 */
	@Bean
	public LettucePoolingClientConfiguration getPoolConfig() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxWaitMillis(maxWait);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		return LettucePoolingClientConfiguration.builder()
				.poolConfig(config)
				.commandTimeout(Duration.ofMillis(10000))
				.shutdownTimeout(Duration.ofMillis(100))
				.build();
	}

}