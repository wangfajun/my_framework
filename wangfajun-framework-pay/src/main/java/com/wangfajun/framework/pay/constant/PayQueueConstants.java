package com.wangfajun.framework.pay.constant;

/**
 * 支付模块 支付队列 名称常量
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/4/2 14:52
 */
public interface PayQueueConstants {

	/**
	 * 支付队列(直接交换机)
	 */
	String DIRECT_PAY_QUEUE = "direct-pay-queue";

	/**
	 * 支付队列(扇形交换机)
	 */
	String FANOUT_PAY_QUEUE = "fanout-pay-queue";

}
