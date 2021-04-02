package com.wangfajun.framework.order.task;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wangfajun.framework.api.enums.TxEventStatusEnum;
import com.wangfajun.framework.api.enums.TxEventTypeEnum;
import com.wangfajun.framework.api.model.entity.TxEvent;
import com.wangfajun.framework.order.service.TxEventService;
import com.wangfajun.framework.order.service.OrderRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单事件定时任务
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
	OrderRecordService orderRecordService;

	/**
     * 定时任务，查询订单事件为:已接收，修改订单表支付状态，状态表状态为已处理
	 */
    @Scheduled(cron="0/50 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void task(){
        log.info("订单事件定时任务开始");

        // 查询订单事件的状态为:已接收 数据
        List<TxEvent> txEventList = txEventService.list(Wrappers.<TxEvent>lambdaQuery()
				.eq(TxEvent::getEventType, TxEventTypeEnum.ORDER_EVENT.getType())
				.eq(TxEvent::getStatus, TxEventStatusEnum.RECEIVED.getStatus()));

        for (TxEvent event : txEventList) {

			// 修改订单状态为已支付
			orderRecordService.finishOrder(event.getContent());

			// 修改状态：已处理
			event.setStatus(TxEventStatusEnum.FINISHED.getStatus());
			txEventService.updateById(event);
        }
		log.info("订单事件定时任务结束");
    }

}
