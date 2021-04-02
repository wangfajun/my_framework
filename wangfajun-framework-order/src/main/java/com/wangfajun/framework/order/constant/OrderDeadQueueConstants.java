package com.wangfajun.framework.order.constant;

/**
 * 订单模块 死信队列 常量配置
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/4/2 11:18
 */
public interface OrderDeadQueueConstants {

	/**
	 * 死信交换机
	 */
	String ORDER_DEAD_EXCHANGE = "order-dead-exchange";

	/**
	 * 死信队列 名称
	 */
	String ORDER_DEAD_QUEUE = "order-dead-queue";

	/**
	 *  死信队列key
	 */
	String ORDER_DEAD_KEY = "order.dead.key";

}
