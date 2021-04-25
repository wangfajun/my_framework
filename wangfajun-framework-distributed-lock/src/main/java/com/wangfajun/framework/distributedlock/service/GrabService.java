package com.wangfajun.framework.distributedlock.service;

/**
 * 抢单服务
 *
 * @author Administrator
 */
public interface GrabService {

	/**
	 * 司机抢单
	 *
	 * @param orderId
	 * @param driverId
	 * @return
	 */
	String grabOrder(int orderId, int driverId);
}
