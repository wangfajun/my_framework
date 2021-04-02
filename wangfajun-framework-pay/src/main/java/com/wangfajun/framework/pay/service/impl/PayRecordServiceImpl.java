package com.wangfajun.framework.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfajun.framework.api.model.req.EventContent;
import com.wangfajun.framework.pay.mapper.PayRecordMapper;
import com.wangfajun.framework.pay.service.PayRecordService;
import com.wangfajun.framework.api.enums.TxEventStatusEnum;
import com.wangfajun.framework.api.enums.TxEventTypeEnum;
import com.wangfajun.framework.api.model.entity.TxEvent;
import com.wangfajun.framework.pay.service.TxEventService;
import com.wangfajun.framework.api.enums.PayStatusEnum;
import com.wangfajun.framework.pay.entity.PayRecord;

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

	TxEventService txEventService;

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
	 * 模拟第三方支付回调
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

		// 2.支付成功, 需要跟订单做分布式事务，发布一条 订单事件，状态为:新事件，让订单服务去消费做订单处理
		TxEvent payEvent = new TxEvent();
		payEvent.setId(new Random().nextInt(100000));
		payEvent.setEventType(TxEventTypeEnum.ORDER_EVENT.getType());
		payEvent.setStatus(TxEventStatusEnum.NEW.getStatus());
		// 内容为：订单编号
		EventContent content = new EventContent();
		content.setBalance(new BigDecimal(100));
		content.setOrderId(orderId);
		content.setUserId("888888");
		payEvent.setContent(JSON.toJSONString(content));
		payEvent.setCreateTime(new Date());
		txEventService.save(payEvent);
	}

}
