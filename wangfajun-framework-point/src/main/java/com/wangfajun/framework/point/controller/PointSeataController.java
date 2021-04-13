package com.wangfajun.framework.point.controller;

import com.wangfajun.framework.point.service.UserPointServiceAt;
import com.wangfajun.framework.point.service.UserPointServiceTcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.seata.spring.annotation.GlobalTransactional;

/**
 * seata AT模式、TCC模式测试
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/31 13:58
 */
@RestController
@RequestMapping("/seata")
public class PointSeataController {

	@Autowired
	UserPointServiceAt pointServiceAt;

	@Autowired
	UserPointServiceTcc userPointServiceTcc;

	/**
	 * seata AT模式 与account服务的seataAt/at方法做分布式事务测试
	 *
	 * @return
	 */
	@PostMapping("at")
	public void at(){
		pointServiceAt.saveAt();
		int i = 1/0;
	}

	/**
	 * seata TCC模式 与account服务的seata/tcc方法做分布式事务测试
	 *
	 * @return
	 */
	@PostMapping("tcc")
	@GlobalTransactional(rollbackFor = Exception.class)
	public void tcc(){
		userPointServiceTcc.pointTry(null);
		int i = 1/0;
	}

}
