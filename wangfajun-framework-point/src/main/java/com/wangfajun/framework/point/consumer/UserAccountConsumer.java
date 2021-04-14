package com.wangfajun.framework.point.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * 账户消息 rocketmq消费端
 *
 * @author Administrator
 */
@Component
public class UserAccountConsumer {

    String consumerGroup = "consumer-group";
    DefaultMQPushConsumer consumer;

    @Autowired
    UserAccountListener userAccountListener;
    
    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("account","*");
        consumer.registerMessageListener(userAccountListener);

        // 2次失败 就进私信队列
        consumer.setMaxReconsumeTimes(2);
        consumer.start();
    }
}