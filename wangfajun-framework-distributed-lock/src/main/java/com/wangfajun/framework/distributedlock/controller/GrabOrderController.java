package com.wangfajun.framework.distributedlock.controller;

import com.wangfajun.framework.distributedlock.service.GrabService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

/**
 * 模拟抢单
 *
 * @author Administrator
 */
@Slf4j
@RestController
@RequestMapping("/grabOrder")
public class GrabOrderController {

	@Autowired
	/**
	 * 无锁
 	 */
//    @Qualifier("noLockService")

	/**
	 * jvm锁
	 */
//    @Qualifier("jvmLockService")

	/**
	 * mysql锁
	 */
//    @Qualifier("mysqlLockService")

	/**
	 * 手写redis
	 */
//    @Qualifier("redisLockService")

	/**
	 * 单个redisson
	 */
    @Qualifier("redisRedissonService")

	/**
	 * 红锁
	 */
//	@Qualifier("redisRedissonRedLockLockService")
    private GrabService grabService;

	/**
	 * 抢单
	 *
	 * @param orderId
	 * @param driverId
	 * @return
	 */
    @GetMapping("/do/{orderId}/{driverId}")
    public String grab(@PathVariable("orderId") int orderId,@PathVariable("driverId") int driverId){
        log.info(String.format("orderId:%s,driverId:%s",orderId,driverId));
        grabService.grabOrder(orderId,driverId);
        return "success";
    }

}
