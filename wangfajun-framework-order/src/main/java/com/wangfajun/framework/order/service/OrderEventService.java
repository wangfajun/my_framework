package com.wangfajun.framework.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangfajun.framework.model.entity.OrderEvent;
import com.wangfajun.framework.order.entity.OrderRecord;

/**
 * 订单事件服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public interface OrderEventService extends IService<OrderEvent> {

	/**
	 * 保存订单事件
	 *
	 * @return
	 */
	void saveOrderEvent(OrderEvent orderEvent) throws Exception;

	/**
	 * 修改订单事件
	 *
	 * @return
	 */
	void updOrderEvent(OrderEvent orderEvent);

}
