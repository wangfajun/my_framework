package com.wangfajun.framework.order.config;

import com.wangfajun.framework.order.constant.OrderDeadQueueConstants;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 订单服务 死信队列 配置
 *
 * @description: 支付服务与订单服务做分布式事务（基于本地事件表）
 * 支付回调成功后，向支付服务本地事务表中插入事件数据，待定时任务拉取后，向MQ推，订单服务（order）消费消息，做订单状态处理
 *
 * RabbitMq中4中交换机：
 * 1.Direct exchange---直接类型交换机
 * 要求消息带的路由键和绑定的路由键完全匹配，这是一个完整的匹配。
 * 比如一个队列A绑定到该交换机上的路由键是“abc”，则只有指定的路由键是“abc”的消息才被投递给队列A，其他的不会投递给队列A
 *
 * 2.Fanout Exchange---扇出类型交换机
 * 只需要简单的将队列绑定到该类型交换机上，该类型的交换机绑定队列时可以不指定路由键(Routingkey)
 * 当消息发送给该交换机后，它会将消息投递给与该交换机绑定的所有队列
 * 很像广播，每台子网内的机器都会获得一份消息，Fanout交换机转发消息是最快的
 *
 * 3.Topic Exchange---主题类型交换机
 * 将路由键和某模式进行匹配。此时队列需要绑定某一个模式上。符号#匹配0个或多个单词，符号 *匹配一个单词。
 *
 * 4.Headers Exchanges
 * 这种不常用，可以选择性忽略
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/31 12:22
 */
@Configuration
public class OrderDeadQueueConfig {

	/**
	 * 订单死信队列设置
	 *
	 * @return
	 */
	@Bean
	public Queue orderDeadQueue() {
		return new Queue(OrderDeadQueueConstants.ORDER_DEAD_QUEUE,  true,  false,  false);
	}

	/**
	 * 订单死信交换机
	 *
	 * @return
	 */
	@Bean
	public DirectExchange orderDeadExchange() {
		return new DirectExchange(OrderDeadQueueConstants.ORDER_DEAD_EXCHANGE,  true,  false);
	}

	/**
	 * 订单死信交换机绑定订单死信队列及订单死信路由key
	 *
	 * @return
	 */
	@Bean
	public Binding bindOrderDeadExchangeAndOrderDeadQueue() {
		return BindingBuilder.bind(orderDeadQueue()).to(orderDeadExchange()).with(OrderDeadQueueConstants.ORDER_DEAD_KEY);
	}

}