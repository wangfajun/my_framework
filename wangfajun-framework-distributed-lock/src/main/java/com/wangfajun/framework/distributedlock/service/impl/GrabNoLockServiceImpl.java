package com.wangfajun.framework.distributedlock.service.impl;

import com.wangfajun.framework.distributedlock.service.GrabService;
import com.wangfajun.framework.distributedlock.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 无锁
 *
 * @author Administrator
 */
@Service("noLockService")
public class GrabNoLockServiceImpl implements GrabService {

	@Autowired
	OrderService orderService;

	@Override
	public String grabOrder(int orderId, int driverId) {
		try {
			System.out.println("司机:" + driverId + " 执行抢单逻辑");

			boolean b = orderService.grab(orderId, driverId);
			if (b) {
				System.out.println("司机:" + driverId + " 抢单成功");
			} else {
				System.out.println("司机:" + driverId + " 抢单失败");
			}

		} finally {

		}

		return null;
	}

}
