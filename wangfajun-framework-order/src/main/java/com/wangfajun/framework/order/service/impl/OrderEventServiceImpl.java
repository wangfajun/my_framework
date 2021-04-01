package com.wangfajun.framework.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfajun.framework.enums.EventStatusEnum;
import com.wangfajun.framework.enums.OrderStatusEnum;
import com.wangfajun.framework.model.entity.OrderEvent;
import com.wangfajun.framework.order.mapper.OrderEventMapper;
import com.wangfajun.framework.order.service.OrderEventService;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单事件服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrderEventServiceImpl extends ServiceImpl<OrderEventMapper, OrderEvent> implements OrderEventService {

	/**
	 * 保存订单事件
	 *
	 * @return
	 */
	@Override
	public void saveOrderEvent(OrderEvent orderEvent) throws Exception {
		this.save(orderEvent);
	}


	/**
	 * 修改订单事件
	 *
	 * @return
	 */
	@Override
	public void updOrderEvent(OrderEvent orderEvent) {
		orderEvent.setStatus(EventStatusEnum.FINISHED.getStatus());
		this.updateById(orderEvent);
	}
}
