package com.wangfajun.framework.point.consumer;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wangfajun.framework.point.entity.UserAccount;
import com.wangfajun.framework.point.entity.UserPoint;
import com.wangfajun.framework.point.service.UserPointServiceAt;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * 消费账户消息，模拟加积分
 *
 * @author Administrator
 */
@Component
@Slf4j
public class UserAccountListener implements MessageListenerConcurrently {

    @Autowired
	UserPointServiceAt userPointServiceAt;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
        log.info("消费者线程监听到消息。");
        try{

            System.out.println(1/0);
            for (MessageExt message:list) {
                log.info("开始处理订单数据，准备增加积分....");
				UserAccount account  = JSONObject.parseObject(message.getBody(), UserAccount.class);

				// todo 加积分
				LambdaUpdateWrapper<UserPoint> updateWrapper = Wrappers.lambdaUpdate();
				updateWrapper.eq(UserPoint::getUserId,account.getId());
				updateWrapper.set(UserPoint::getPoints,100);
				userPointServiceAt.update(updateWrapper);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }catch (Exception e){
            log.error("处理消费者数据发生异常。{}",e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}