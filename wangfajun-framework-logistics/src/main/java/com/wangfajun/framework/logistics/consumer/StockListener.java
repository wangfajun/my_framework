package com.wangfajun.framework.logistics.consumer;

import com.alibaba.fastjson.JSONObject;
import com.wangfajun.framework.logistics.entity.LogisticsRecord;
import com.wangfajun.framework.logistics.entity.Stock;
import com.wangfajun.framework.logistics.service.LogisticsService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;

/**
 * 消费账户消息，模拟加积分
 *
 * @author Administrator
 */
@Component
@Slf4j
public class StockListener implements MessageListenerConcurrently {

    @Autowired
	LogisticsService logisticsService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
        log.info("消费者线程监听到消息。");
        try{
            for (MessageExt message:list) {
                log.info("开始处理库存数据，准备增加物流信息....");
				Stock stock  = JSONObject.parseObject(message.getBody(), Stock.class);

				// 保存物流信息
				LogisticsRecord record = new LogisticsRecord();
				record.setId(String.valueOf(new Random().nextInt(10000)));
				record.setOrderId(stock.getOrderId());
				record.setCreateTime(new Date());
				logisticsService.save(record);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }catch (Exception e){
            log.error("处理消费者数据发生异常。{}",e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}