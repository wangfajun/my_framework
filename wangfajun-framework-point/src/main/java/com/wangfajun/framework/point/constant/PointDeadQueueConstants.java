package com.wangfajun.framework.point.constant;

/**
 * 积分服务 死信队列 名称常量
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/4/2 11:18
 */
public interface PointDeadQueueConstants {

	/**
	 * 死信交换机
	 */
	String POINT_DEAD_EXCHANGE = "point-dead-exchange";

	/**
	 * 死信队列 名称
	 */
	String POINT_DEAD_QUEUE = "point-dead-queue";

	/**
	 *  死信队列key
	 */
	String POINT_DEAD_KEY = "point.dead.key";

}
