package com.wangfajun.framework.order.controller;

import com.wangfajun.framework.order.service.OrderRecordService;
import com.wangfajun.framework.api.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/31 13:58
 */
@RestController
@RequestMapping("/order")
public class OrderRecordController {

	@Autowired
	OrderRecordService orderRecordService;

	/**
	 * 模拟下订单
	 */
	@PostMapping("save")
	public R saveOrderRecord(){
		orderRecordService.saveOrderRecord();
		return R.ok();
	}

}
