package com.wangfajun.framework.stock.controller;

import com.wangfajun.framework.stock.entity.Stock;
import com.wangfajun.framework.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.Random;

/**
 * 库存
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/4/14 13:58
 */
@RestController
@RequestMapping("/stock")
public class StockController {

	@Autowired
	StockService stockService;

	/**
	 * 扣库存
	 */
	@PostMapping("/dec")
	public String decStock(){
		// 构造用户信息
		Stock stock = new Stock();
		stock.setId("1");
		stock.setStatus(0);
		stock.setNums(1);
		stock.setCreateTime(new Date());

		stockService.sendDecStockMsg(stock);

		return "success";
	}

}
