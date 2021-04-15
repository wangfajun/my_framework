package com.wangfajun.framework.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangfajun.framework.stock.entity.Stock;

/**
 * rocketmq 库存服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public interface StockService extends IService<Stock> {

	/**
	 * 扣库存
	 *
	 * @param stock         库存信息
	 * @param transactionId 事务编号，唯一
	 */
	void decStock(Stock stock, String transactionId);

	/**
	 * 前端调用，只用于向RocketMQ发送事务消息
	 *
	 * @param stock
	 */
	void sendDecStockMsg(Stock stock);

}
