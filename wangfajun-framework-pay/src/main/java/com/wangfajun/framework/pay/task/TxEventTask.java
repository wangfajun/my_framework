package com.wangfajun.framework.pay.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wangfajun.framework.api.model.entity.TxEvent;
import com.wangfajun.framework.api.constant.RabbitMqCommonConstants;
import com.wangfajun.framework.api.enums.TxEventStatusEnum;
import com.wangfajun.framework.pay.service.TxEventService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * 支付事件定时任务
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Component
public class TxEventTask {

	@Autowired
	TxEventService txEventService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 定时任务，查询支付事件为:新建,让订单服务消费
	 */
	@Scheduled(cron = "0/5 * * * * ?")
	@Transactional(rollbackFor = Exception.class)
	public void task() {
		log.info("支付事件定时任务开始");

		// 查询事件的状态为:新事件 数据
		List<TxEvent> payEventsList = txEventService.list(Wrappers.<TxEvent>lambdaQuery()
				.eq(TxEvent::getStatus, TxEventStatusEnum.NEW.getStatus()));

		for (TxEvent txEvent : payEventsList) {
			txEvent.setStatus(TxEventStatusEnum.SEND.getStatus());
			txEventService.updateById(txEvent);
			log.info(String.format("[%s]事件状态置为已发送", txEvent.getId()));

			// 往直接交换机 事件路由key 发消息
			//rabbitTemplate.convertAndSend(RabbitMqCommonConstants.DIRECT_TX_EVENT_EXCHANGE, RabbitMqCommonConstants.DIRECT_TX_EVENT_KEY, JSONObject.toJSONString(txEvent));

			// 往扇形交换机 发消息
			rabbitTemplate.convertAndSend(RabbitMqCommonConstants.FANOUT_TX_EVENT_EXCHANGE, null, JSONObject.toJSONString(txEvent));
		}
		log.info("支付事件定时任务结束");
	}

}
