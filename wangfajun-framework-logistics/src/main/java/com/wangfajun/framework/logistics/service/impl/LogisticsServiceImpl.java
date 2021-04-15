package com.wangfajun.framework.logistics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfajun.framework.logistics.entity.LogisticsRecord;
import com.wangfajun.framework.logistics.mapper.LogisticsMapper;
import com.wangfajun.framework.logistics.service.LogisticsService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * rocketmq 物流服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, LogisticsRecord> implements LogisticsService {

	/**
	 * 保存物流记录
	 * @param logisticsRecord
	 * @param transactionId
	 */
	@Override
	public void createLogisticsRecord(LogisticsRecord logisticsRecord, String transactionId) {

	}
}
