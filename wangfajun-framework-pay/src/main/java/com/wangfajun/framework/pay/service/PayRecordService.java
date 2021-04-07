package com.wangfajun.framework.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangfajun.framework.pay.entity.PayRecord;

/**
 * 支付流水服务层
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public interface PayRecordService extends IService<PayRecord> {

	void updateRecord1(PayRecord payRecord);

	void updateRecord2(PayRecord payRecord);

	void cancelRecord1(PayRecord payRecord);

	void cancelRecord2(PayRecord payRecord);

	/**
	 * 模拟支付,保存支付流水记录
	 *
	 * @param orderId 订单编号
	 */
	void savePayRecord(String orderId);

	/**
	 * 模拟支付回调
	 *
	 * @param payId 支付单编号
	 * @param orderId 订单编号
	 */
	void simulationPayCallBack(String payId,String orderId);

}
