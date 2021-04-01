package com.wangfajun.framework.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfajun.framework.enums.OrderStatusEnum;
import com.wangfajun.framework.order.entity.OrderRecord;
import com.wangfajun.framework.order.mapper.OrderRecordMapper;
import com.wangfajun.framework.order.service.OrderRecordService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单流水服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrderRecordServiceImpl extends ServiceImpl<OrderRecordMapper, OrderRecord> implements OrderRecordService {

	OrderRecordMapper orderRecordMapper;

	/**
	 * 保存订单流水记录
	 *
	 * @return
	 */
	@Override
	public void saveOrderRecord() {
		OrderRecord order = new OrderRecord();
		order.setOrderId(UUID.randomUUID().toString());
		order.setStatus(OrderStatusEnum.NEED_PAY.getStatus());
		order.setBalance(new BigDecimal("100"));
		this.save(order);
	}

	/**
	 * 修改订单状态为已支付
	 *
	 * @param orderId 订单编号
	 * @return
	 */
	@Override
	public boolean finishOrder(String orderId) {
		OrderRecord orderRecord = new OrderRecord();
		orderRecord.setOrderId(orderId);
		orderRecord.setStatus(OrderStatusEnum.PAYED.getStatus());
		return this.updateById(orderRecord);
	}

}
