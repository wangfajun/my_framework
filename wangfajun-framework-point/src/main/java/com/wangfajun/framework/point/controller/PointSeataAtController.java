package com.wangfajun.framework.point.controller;

import com.wangfajun.framework.point.entity.UserPoint;
import com.wangfajun.framework.point.service.UserPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.Random;

/**
 * seata AT模式 测试
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/31 13:58
 */
@RestController
@RequestMapping("/seataAt")
public class PointSeataAtController {

	@Autowired
	UserPointService pointService;

	/**
	 * seata AT模式 与account服务的seataAt/save方法做分布式事务测试
	 *
	 * @return
	 */
	@PostMapping("save")
	public void save(){

		UserPoint payRecord = new UserPoint();
		payRecord.setId(String.valueOf(new Random().nextInt(10000)));
		payRecord.setUserId("1");
		payRecord.setPoints(1);
		payRecord.setCreateTime(new Date());
		payRecord.setStatus(0);
		pointService.save(payRecord);

		int i = 1/0;

	}

}
