package com.wangfajun.framework.stock.config;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wangfajun.framework.stock.entity.Stock;
import com.wangfajun.framework.stock.entity.TransactionLog;
import com.wangfajun.framework.stock.mapper.TransactionLogMapper;
import com.wangfajun.framework.stock.service.StockService;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * 库存事务 rocketmq事务消息
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/4/14 19:56
 */
@Component
@Slf4j
public class StockTransactionListener implements TransactionListener {

	@Autowired
	StockService stockService;

	@Autowired
	TransactionLogMapper transactionLogMapper;

	/**
	 * 发送half msg 返回send ok后调用的方法
	 *
	 * @param message
	 * @param o
	 * @return
	 */
	@Override
	public LocalTransactionState executeLocalTransaction(Message message, Object o) {
		log.info("开始执行本地事务....");
		LocalTransactionState state;
		try {
			String body = new String(message.getBody());
			Stock stock = JSONObject.parseObject(body, Stock.class);
			stockService.decStock(stock, message.getTransactionId());
			// 返回commit后，消息能被消费者消费
			state = LocalTransactionState.COMMIT_MESSAGE;
//            state = LocalTransactionState.ROLLBACK_MESSAGE;
//            state = LocalTransactionState.UNKNOW;
			log.info("本地事务已提交。{}", message.getTransactionId());

		} catch (Exception e) {
			log.info("执行本地事务失败。{}", e);
			state = LocalTransactionState.ROLLBACK_MESSAGE;
		}
		return state;
	}

	/**
	 * 回查 走的方法
	 *
	 * @param messageExt
	 * @return
	 */
	@Override
	public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
		// 回查多次失败 人工补偿。提醒人。发邮件的。
		log.info("开始回查本地事务状态。{}", messageExt.getTransactionId());
		LocalTransactionState state;
		String transactionId = messageExt.getTransactionId();
		LambdaQueryWrapper<TransactionLog> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(TransactionLog::getId, transactionId);
		if (transactionLogMapper.selectCount(queryWrapper) > 0) {
			state = LocalTransactionState.COMMIT_MESSAGE;
		} else {
			// 可能业务还在执行，事务日志还没记录，让mq过段时间再来回查一下
			state = LocalTransactionState.UNKNOW;
		}
		log.info("结束本地事务状态查询：{}", state);
		return state;
	}
}