package com.wangfajun.framework.pay.controller;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.wangfajun.framework.api.util.R;
import com.wangfajun.framework.pay.service.PayRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 支付
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/31 13:58
 */
@RestController
@RequestMapping("/payLcn")
public class PayLcnController {

	@Autowired
	PayRecordService payService;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * lcn 模拟支付 与order服务的orderLcn/save方法做分布式事务测试
	 *
	 * @return
	 */
	@LcnTransaction
	@PostMapping("/save")
	@Transactional(rollbackFor = Exception.class)
	public String save(){
		restTemplate.postForEntity("http://service-order/orderLcn/save",null,String.class);
		payService.savePayRecord("demo");
		return "新增支付流水成功";
	}

}
