package com.wangfajun.framework.order.controller;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.wangfajun.framework.order.service.OrderRecordService;
import com.wangfajun.framework.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 本地事件表 订单
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/31 13:58
 */
@RestController
@RequestMapping("/orderEvent")
public class OrderEventController {

	@Autowired
	OrderRecordService orderRecordService;

	/**
	 * 模拟下订单
	 */
	@LcnTransaction
	@RequestMapping("save")
	public R saveOrderRecord(){
		orderRecordService.saveOrderRecord();
		return R.ok();
	}

}
