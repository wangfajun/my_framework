package com.wangfajun.framework.distributedlock.service;

/**
 * 订单服务
 *
 * @author Administrator
 */
public interface OrderService {

	boolean grab(int orderId, int driverId);

}
