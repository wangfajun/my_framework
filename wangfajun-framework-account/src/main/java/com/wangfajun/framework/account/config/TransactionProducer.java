package com.wangfajun.framework.account.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;


/**
 * 用户账户 事务消息发送配置
 *
 * @author wangfajun
 */
@Component
public class TransactionProducer {

	private String producerGroup = "user_account_trans_group";

	private TransactionMQProducer producer;

	/**
	 * 用于执行本地事务和事务状态回查的监听器
	 */
	@Autowired
	UserAccountTransactionListener orderTransactionListener;

	/**
	 * 执行任务的线程池
	 */
	ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 60,
			TimeUnit.SECONDS, new ArrayBlockingQueue<>(50));

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		producer = new TransactionMQProducer(producerGroup);
		producer.setNamesrvAddr("127.0.0.1:9876");
		producer.setSendMsgTimeout(Integer.MAX_VALUE);
		producer.setExecutorService(executor);
		producer.setTransactionListener(orderTransactionListener);
		this.start();
	}

	/**
	 * 启动
	 */
	private void start() {
		try {
			this.producer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送事务消息
	 *
	 * @param data  数据
	 * @param topic 主题
	 * @return
	 * @throws MQClientException
	 */
	public TransactionSendResult send(String data, String topic) throws MQClientException {
		Message message = new Message(topic, data.getBytes());
		return this.producer.sendMessageInTransaction(message, null);
	}
}