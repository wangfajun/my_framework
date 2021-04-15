package com.wangfajun.framework.logistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangfajun.framework.logistics.entity.LogisticsRecord;

/**
 * rocketmq 物流服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public interface LogisticsService extends IService<LogisticsRecord> {

	void createLogisticsRecord(LogisticsRecord logisticsRecord, String transactionId);

}
