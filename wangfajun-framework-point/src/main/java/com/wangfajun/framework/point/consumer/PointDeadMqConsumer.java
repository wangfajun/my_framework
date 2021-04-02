package com.wangfajun.framework.point.consumer;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.wangfajun.framework.api.model.entity.TxEvent;
import com.wangfajun.framework.point.constant.PointDeadQueueConstants;
import com.wangfajun.framework.point.constant.PointQueueConstants;
import com.wangfajun.framework.point.utils.RedisUtil;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * 积分服务 死信队列 补偿
 * <p>
 * 消息变成死信有以下几种情况： 消息被拒绝（basic.reject/ basic.nack）并且requeue=false 消息TTL过期（参考：RabbitMQ之TTL（Time-To-Live
 * 过期时间）） 队列达到最大长度
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/31 14:56
 */
@Slf4j
@Component
public class PointDeadMqConsumer {

	@Autowired
	RedisUtil redisUtil;

	/**
	 * 积分队列的死信队列
	 *
	 * @param message
	 * @param channel
	 * @param headers
	 */
	@RabbitListener(queues = {PointDeadQueueConstants.POINT_DEAD_QUEUE})
	@RabbitHandler
	public void consumeDead(Message message, Channel channel, @Headers Map<String, Object> headers) throws Exception {
		Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
		TxEvent txEvent = JSON.parseObject(new String(message.getBody(), "UTF-8"), TxEvent.class);
		try {
			log.info("积分服务，死信队列消费端收到消息:" + message);
			// 应答模式确认接收到消息,后面那个参数如果是false,就是只确认签收当前消息,如果是true,则签收全部比当前TagId小的消息
			channel.basicAck(deliveryTag, false);

			// 清除key
			redisUtil.del(PointQueueConstants.DIRECT_POINT_QUEUE + txEvent.getId());
		} catch (Exception e) {
			log.error("积分服务，死信队列消费事件异常", e);
			try {
				boolean redelivered = (boolean) headers.get(AmqpHeaders.REDELIVERED);
				channel.basicNack(deliveryTag, false, !redelivered);
			} catch (IOException ioException) {
				log.error("积分服务，通知MQ重发消息");
			}
		}
	}

}
