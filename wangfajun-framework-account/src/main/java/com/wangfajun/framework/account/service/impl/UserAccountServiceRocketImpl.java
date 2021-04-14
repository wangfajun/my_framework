package com.wangfajun.framework.account.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfajun.framework.account.config.TransactionProducer;
import com.wangfajun.framework.account.entity.TransactionLog;
import com.wangfajun.framework.account.entity.UserAccount;
import com.wangfajun.framework.account.mapper.TransactionLogMapper;
import com.wangfajun.framework.account.mapper.UserAccountMapper;
import com.wangfajun.framework.account.service.UserAccountServiceAt;
import com.wangfajun.framework.account.service.UserAccountServiceRocket;

import org.apache.rocketmq.client.exception.MQClientException;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

/**
 * rocketmq 账户服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Service
public class UserAccountServiceRocketImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountServiceRocket {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	TransactionProducer producer;

	@Autowired
	TransactionLogMapper transactionLogMapper;

	/**
	 * 创建账号业务
	 *
	 * @param account 账户信息
	 * @param transactionId 事务编号，唯一
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createAccount(UserAccount account, String transactionId) {
		// 保存账号信息
		this.save(account);

		//2.写入事务日志
		TransactionLog log = new TransactionLog();
		log.setId(transactionId);
		log.setBusiness("account");
		log.setForeignKey(String.valueOf(account.getId()));
		transactionLogMapper.insert(log);
	}

	/**
	 * 前端调用，只用于向RocketMQ发送事务消息
	 * @param account
	 * @throws MQClientException
	 */
	@Override
	public void sendCreateAccountMsg(UserAccount account) throws MQClientException {
		producer.send(JSON.toJSONString(account),"account");
	}

}
