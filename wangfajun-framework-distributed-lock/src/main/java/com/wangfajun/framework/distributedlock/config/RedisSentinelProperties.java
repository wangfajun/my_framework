package com.wangfajun.framework.distributedlock.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author yueyi2019
 */
@Component
@ConfigurationProperties(prefix = "sentinel")
@Order(0)
@Data
public class RedisSentinelProperties {

    private String[] address;

    private String masterName;
}
