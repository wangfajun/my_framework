package com.wangfajun.framework.order.controller;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.wangfajun.framework.api.util.R;
import com.wangfajun.framework.order.service.OrderRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LCN测试
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/31 13:58
 */
@RestController
@RequestMapping("/orderLcn")
public class OrderLcnController {

	@Autowired
	OrderRecordService orderRecordService;

	/**
	 * lcn分布式事务测试
	 */
	@LcnTransaction
	@RequestMapping("save")
	public R saveLcn(){
		orderRecordService.saveOrderRecord();
		return R.ok();
	}

}
