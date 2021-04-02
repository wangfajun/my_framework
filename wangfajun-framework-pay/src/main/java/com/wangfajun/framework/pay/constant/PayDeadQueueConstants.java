package com.wangfajun.framework.pay.constant;

/**
 * 支付模块 死信队列 名称常量
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/4/2 11:18
 */
public interface PayDeadQueueConstants {

	/**
	 * 死信交换机
	 */
	String PAY_DEAD_EXCHANGE = "pay-dead-exchange";

	/**
	 * 死信队列 名称
	 */
	String PAY_DEAD_QUEUE = "pay-dead-queue";

	/**
	 *  死信队列key
	 */
	String PAY_DEAD_KEY = "pay.dead.key";

}
