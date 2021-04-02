package com.wangfajun.framework.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangfajun.framework.order.entity.OrderRecord;

/**
 * 订单服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public interface OrderRecordService extends IService<OrderRecord> {

	/**
	 * 保存订单流水记录
	 *
	 * @return
	 */
	void saveOrderRecord();

	/**
	 * 修改订单状态为完成信息
	 *
	 * @return
	 */
	boolean finishOrder(String content);

}
