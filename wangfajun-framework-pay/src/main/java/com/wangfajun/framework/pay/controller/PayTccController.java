package com.wangfajun.framework.pay.controller;

import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.wangfajun.framework.pay.entity.PayRecord;
import com.wangfajun.framework.pay.service.PayRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;

/**
 * 支付Tcc测试
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/31 13:58
 */
@RestController
@RequestMapping("/payTcc")
public class PayTccController {

	@Autowired
	PayRecordService payService;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * tcc 模拟支付 与order服务的orderTcc/save方法做分布式事务测试
	 *
	 * @return
	 */
	@TccTransaction
	@PostMapping("/save")
	public void save(){
		restTemplate.postForEntity("http://service-order/orderTcc/save",null,String.class);
		System.out.println("try");

		PayRecord payRecord = new PayRecord();
		payRecord.setPayId("1");
		payRecord.setBalance(new BigDecimal(100));
		payService.updateRecord1(payRecord);

		int i= 1/0;
		PayRecord payRecord1 = new PayRecord();
		payRecord1.setPayId("2");
		payRecord1.setBalance(new BigDecimal(200));
		payService.updateById(payRecord1);

	}

	public void confirmSave(){
		System.out.println("confirm");
	}

	public void cancelSave(){
		System.out.println("cancel");
		PayRecord payRecord = new PayRecord();
		payRecord.setPayId("1");
		payRecord.setBalance(new BigDecimal(1));
		payService.updateById(payRecord);

		System.out.println("cancel 1执行完");
		PayRecord payRecord1 = new PayRecord();
		payRecord1.setPayId("2");
		payRecord1.setBalance(new BigDecimal(2));
		payService.updateById(payRecord1);
		System.out.println("cancel 2执行完");
	}
}
