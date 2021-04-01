package com.wangfajun.framework.order.consumer;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.wangfajun.framework.constant.CommonConstants;
import com.wangfajun.framework.enums.EventStatusEnum;
import com.wangfajun.framework.enums.EventTypeEnum;
import com.wangfajun.framework.model.entity.OrderEvent;
import com.wangfajun.framework.model.entity.PayEvent;
import com.wangfajun.framework.order.service.OrderEventService;
import com.wangfajun.framework.order.service.OrderRecordService;
import com.wangfajun.framework.order.utils.RedisUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.Map;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单队列消费端
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/31 14:56
 */
@Slf4j
@Component
public class PayEventConsumer {

	@Autowired
	OrderRecordService orderRecordService;

	@Autowired
	OrderEventService orderEventService;

	@Autowired
	RedisUtil redisUtil;

	/**
	 * 消费支付事件发送过来的事件消息
	 *
	 * @param message
	 * @param channel
	 */
	@Transactional(rollbackFor = Exception.class)
	@RabbitHandler
	@RabbitListener(queues = CommonConstants.ORDER_QUEUE)
	public void consumePayEvent(Message message,Channel channel, @Headers Map<String,Object> headers) throws Exception {
		log.info("订单队列消费端收到消息:"+message);
		Long tag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
		PayEvent payEvent = JSON.parseObject(new String(message.getBody(),"UTF-8"), PayEvent.class);
		try {
			// 保存订单事件（新建状态）,通过主键做幂等
			OrderEvent orderEvent = new OrderEvent();
			orderEvent.setId(payEvent.getId());
			orderEvent.setStatus(EventStatusEnum.RECEIVED.getStatus());
			orderEvent.setContent(payEvent.getContent());
			orderEvent.setCreateTime(new Date());
			orderEvent.setEventType(EventTypeEnum.ORDER_EVENT.getType());
			orderEventService.saveOrderEvent(orderEvent);
			// 应答模式确认接收到消息,后面那个参数如果是false,就是只确认签收当前消息,如果是true,则签收全部比当前TagId小的消息
			channel.basicAck(tag, false);
		} catch (Exception e) {
			Object retryCountObj = redisUtil.get(String.valueOf(payEvent.getId()));
			log.info("------ retryCountObj : " + retryCountObj);
			// 事实上手动提交的时候,basicNack的最后一个参数requeue = true时,消息会被无限次的放入消费队列重新消费,直至回送ACK
			// 但是当requeue = false 的时候,此时消息就会立马进入到死信队列。
			if (ObjectUtil.isNotEmpty(retryCountObj) && (int)retryCountObj >= 3) {
				//requeue = false 放入死信队列
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
				log.info("------ 放入死信队列");
			} else {
				//requeue = true 放入消费队列重试消费
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
				redisUtil.incr(String.valueOf(payEvent.getId()), 1);
				log.info("------ 放入消费队列重试消费");
			}
		}
	}

}
