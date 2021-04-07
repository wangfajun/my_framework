package com.wangfajun.framework.order.controller;

import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.wangfajun.framework.api.util.R;
import com.wangfajun.framework.order.service.OrderRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Tcc测试
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/31 13:58
 */
@RestController
@RequestMapping("/orderTcc")
public class OrderTccController {

	@Autowired
	OrderRecordService orderRecordService;

	/**
	 * tcc分布式事务测试
	 */
	@TccTransaction
	@Transactional(rollbackFor = Exception.class)
	@RequestMapping("save")
	public R save(){
		System.out.println("try");
		return R.ok();
	}

	public R confirmSave(){
		System.out.println("confirm");
		return R.ok();
	}

	public R cancelSave(){
		System.out.println("cancel");
		return R.ok();
	}

}
