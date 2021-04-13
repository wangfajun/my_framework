package com.wangfajun.framework.constant;

/**
 * RabbitMq 直接交换机、扇出交换机 常量
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public interface RabbitMqCommonConstants {

	/**********************直接型交换机**********************/

	/**
	 * 事件队列交换机
	 */
	String DIRECT_TX_EVENT_EXCHANGE = "direct-tx-event-exchange";

	/**
	 * 事件队列key
	 */
	String DIRECT_TX_EVENT_KEY = "direct.tx.event.key";


	/**********************扇出型交换机 **********************/

	/**
	 * 事件队列 交换机
	 */
	String FANOUT_TX_EVENT_EXCHANGE = "fanout-tx-event-exchange";

	/**
	 * 订单事件 队列名称
	 */
	String FANOUT_TX_EVENT_QUEUE = "fanout-tx-event-queue";

}
