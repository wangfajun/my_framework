package com.wangfajun.framework.logistics.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 账户消息 rocketmq消费端 死信队列
 *
 * @author Administrator
 */
@Component
public class StockConsumerDld {

    String consumerGroup = "consumer-group1";
    DefaultMQPushConsumer consumer;

    @Autowired
	StockListenerDld orderListener;
    
    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("%DLQ%consumer-group","*");
        consumer.registerMessageListener(orderListener);

        consumer.setMaxReconsumeTimes(2);
        consumer.start();
    }
}