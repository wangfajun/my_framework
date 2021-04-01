package com.wangfajun.framework.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfajun.framework.enums.EventStatusEnum;
import com.wangfajun.framework.enums.EventTypeEnum;
import com.wangfajun.framework.enums.PayStatusEnum;
import com.wangfajun.framework.model.entity.PayEvent;
import com.wangfajun.framework.pay.entity.PayRecord;
import com.wangfajun.framework.pay.mapper.PayEventMapper;
import com.wangfajun.framework.pay.mapper.PayRecordMapper;
import com.wangfajun.framework.pay.service.PayRecordService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 支付流水服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayRecordServiceImpl extends ServiceImpl<PayRecordMapper, PayRecord> implements PayRecordService {

	PayEventMapper payEventMapper;

	/**
	 * 模拟支付,保存支付流水记录
	 */
	@Override
	public void savePayRecord(String orderId) {
		PayRecord payRecord = new PayRecord();
		payRecord.setPayId(UUID.randomUUID().toString());
		payRecord.setStatus(PayStatusEnum.NEED_PAY.getStatus());
		payRecord.setOrderId(orderId);
		payRecord.setCreateTime(new Date());
		payRecord.setBalance(new BigDecimal("100"));
		this.save(payRecord);
	}

	/**
	 * 模拟支付回调
	 *
	 * @param payId 支付单编号
	 * @param orderId 订单编号
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void simulationPayCallBack(String payId,String orderId) {
		// 1.更新支付流水状态
		PayRecord payRecord = new PayRecord();
		payRecord.setPayId(payId);
		payRecord.setStatus(PayStatusEnum.PAYED.getStatus());
		this.updateById(payRecord);

		// 2.插入支付事件,状态为新事件
		PayEvent payEvent = new PayEvent();
		payEvent.setId(new Random().nextInt(100000));
		payEvent.setEventType(EventTypeEnum.PAY_EVENT.getType());
		payEvent.setStatus(EventStatusEnum.NEW.getStatus());
		// 内容为：订单编号
		payEvent.setContent(orderId);
		payEvent.setCreateTime(new Date());
		payEventMapper.insert(payEvent);
	}

}
