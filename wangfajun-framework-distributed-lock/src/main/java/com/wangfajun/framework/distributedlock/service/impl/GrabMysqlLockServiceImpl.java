package com.wangfajun.framework.distributedlock.service.impl;

import com.wangfajun.framework.distributedlock.entity.OrderLock;
import com.wangfajun.framework.distributedlock.lock.MysqlLock;
import com.wangfajun.framework.distributedlock.service.GrabService;
import com.wangfajun.framework.distributedlock.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据库锁
 *
 * @author Administrator
 */
@Service("mysqlLockService")
public class GrabMysqlLockServiceImpl implements GrabService {

	@Autowired
	private MysqlLock lock;

	@Autowired
	OrderService orderService;

	ThreadLocal<OrderLock> orderLock = new ThreadLocal<>();

	@Override
	public String grabOrder(int orderId, int driverId) {
		// 生成 锁
		//生成key
		OrderLock ol = new OrderLock();
		ol.setOrderId(orderId);
		ol.setDriverId(driverId);

		orderLock.set(ol);
		lock.setOrderLockThreadLocal(orderLock);

		// lock
		lock.lock();

		// 执行业务
		try {
			System.out.println("司机:" + driverId + " 执行抢单逻辑");

			boolean b = orderService.grab(orderId, driverId);
			if (b) {
				System.out.println("司机:" + driverId + " 抢单成功");
			} else {
				System.out.println("司机:" + driverId + " 抢单失败");
			}
		} finally {
			// 释放锁
			lock.unlock();
		}

		return null;
	}
}
