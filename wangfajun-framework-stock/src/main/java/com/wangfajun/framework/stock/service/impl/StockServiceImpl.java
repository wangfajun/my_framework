package com.wangfajun.framework.stock.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfajun.framework.stock.config.StockTransactionProducer;
import com.wangfajun.framework.stock.entity.Stock;
import com.wangfajun.framework.stock.entity.TransactionLog;
import com.wangfajun.framework.stock.mapper.StockMapper;
import com.wangfajun.framework.stock.mapper.TransactionLogMapper;
import com.wangfajun.framework.stock.service.StockService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * 库存服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	StockTransactionProducer producer;

	@Autowired
	TransactionLogMapper transactionLogMapper;

	/**
	 * 扣库存
	 *
	 * @param stock         库存信息
	 * @param transactionId 事务编号，唯一
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void decStock(Stock stock, String transactionId) {
		// 1.扣库存
		this.update(stock, Wrappers.<Stock>lambdaUpdate().eq(Stock::getId, stock.getId()));

		// 2.写入事务日志
		TransactionLog log = new TransactionLog();
		log.setId(transactionId);
		log.setBusiness("stock");
		log.setForeignKey(String.valueOf(stock.getId()));
		transactionLogMapper.insert(log);
	}

	/**
	 * 前端调用，只用于向RocketMQ发送事务消息
	 *
	 * @param stock
	 */
	@Override
	public void sendDecStockMsg(Stock stock) {
		try {
			producer.send(JSON.toJSONString(stock), "stock");
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}

}
