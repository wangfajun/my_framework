package com.wangfajun.framework.pay.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wangfajun.framework.constant.CommonConstants;
import com.wangfajun.framework.enums.EventStatusEnum;
import com.wangfajun.framework.enums.EventTypeEnum;
import com.wangfajun.framework.model.entity.PayEvent;
import com.wangfajun.framework.pay.mapper.PayEventMapper;
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
public class PayEventsTask {

    @Autowired
    private PayEventMapper payEventMapper;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 定时任务，查询支付事件为:新建,让订单服务消费
	 */
    @Scheduled(cron="0/5 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void task(){
        log.info("支付事件定时任务开始");

        // 查询支付事件的状态为:新事件 数据
        List<PayEvent> payEventsList = payEventMapper.selectList(Wrappers.<PayEvent>lambdaQuery()
				.eq(PayEvent::getEventType, EventTypeEnum.PAY_EVENT.getType())
				.eq(PayEvent::getStatus, EventStatusEnum.NEW.getStatus()));

        for (int i = 0; i < payEventsList.size(); i++) {
			PayEvent event = payEventsList.get(i);

			// 更改事件状态为:已发送
			event.setStatus(EventStatusEnum.SEND.getStatus());
			payEventMapper.updateById(event);
            log.info("修改数据库完成");

			rabbitTemplate.convertAndSend(CommonConstants.ORDER_EXCHANGE,CommonConstants.ORDER_KEY, JSONObject.toJSONString(event));

        }
		log.info("支付事件定时任务结束");
    }

}
