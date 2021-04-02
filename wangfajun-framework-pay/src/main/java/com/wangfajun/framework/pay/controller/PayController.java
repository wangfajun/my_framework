package com.wangfajun.framework.pay.controller;

import com.wangfajun.framework.pay.service.PayRecordService;
import com.wangfajun.framework.api.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/31 13:58
 */
@RestController
@RequestMapping("/pay")
public class PayController {

	@Autowired
	PayRecordService payService;

	/**
	 * 模拟支付,保存预支付订单
	 *
	 * @param orderId 订单编号
	 */
	@PostMapping("save")
	public R savePrePayRecord(String orderId){
		payService.savePayRecord(orderId);
		return R.ok();
	}

	/**
	 * 模拟支付回调
	 *
	 * @param payId 支付单编号
	 * @param orderId 订单编号
	 */
	@PostMapping("callBack")
	public void callBack(String payId,String orderId){
		payService.simulationPayCallBack(payId,orderId);
	}
	
}
