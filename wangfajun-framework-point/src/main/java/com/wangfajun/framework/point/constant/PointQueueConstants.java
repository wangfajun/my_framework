package com.wangfajun.framework.point.constant;

/**
 * 积分服务 积分队列 名称常量
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/4/2 14:52
 */
public interface PointQueueConstants {

	/**
	 * 积分队列(直接交换机)
	 */
	String DIRECT_POINT_QUEUE = "direct-point-queue";

	/**
	 * 积分队列(扇形交换机)
	 */
	String FANOUT_POINT_QUEUE = "fanout-point-queue";

}
