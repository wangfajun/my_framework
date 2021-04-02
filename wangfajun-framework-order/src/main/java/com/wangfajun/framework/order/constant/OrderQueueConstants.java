package com.wangfajun.framework.order.constant;

/**
 * 订单模块 订单队列 常量配置
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/4/2 14:52
 */
public interface OrderQueueConstants {

	/**
	 * 订单队列(直接交换机)
	 */
	String DIRECT_ORDER_QUEUE = "direct-order-queue";

	/**
	 * 订单队列(扇形交换机)
	 */
	String FANOUT_ORDER_QUEUE = "fanout-order-queue";

}
