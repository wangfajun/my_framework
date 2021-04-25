package com.wangfajun.framework.distributedlock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfajun.framework.distributedlock.entity.OrderRecord;
import com.wangfajun.framework.distributedlock.mapper.OrderRecordMapper;
import com.wangfajun.framework.distributedlock.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * 订单服务
 *
 * @author Administrator
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderRecordMapper, OrderRecord> implements OrderService {


	@Override
	public boolean grab(int orderId, int driverId) {
		OrderRecord order = this.getById(orderId);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 还有填写其他很多业务信息。包括哪个司机啥的。起点，终点。

		if (order.getStatus().intValue() == 0) {
			order.setStatus(1);
			this.updateById(order);
			return true;
		}
		return false;

	}
}
