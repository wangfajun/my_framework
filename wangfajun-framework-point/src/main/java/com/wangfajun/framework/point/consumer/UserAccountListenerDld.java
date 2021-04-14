package com.wangfajun.framework.point.consumer;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * 账户消息死信队列监听
 *
 * @author Administrator
 */
@Component
@Slf4j
public class UserAccountListenerDld implements MessageListenerConcurrently {

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
		log.info("死信队列：消费者线程监听到消息。");
		try {
			for (MessageExt message : list) {
				log.info("死信队列：开始处理订单数据，准备增加积分....");
				System.out.println("发邮件");
			}
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		} catch (Exception e) {
			log.error("死信队列：处理消费者数据发生异常。{}", e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
	}
}