package com.wangfajun.framework.distributedlock.service.impl;

import com.wangfajun.framework.distributedlock.constant.RedisKeyConstant;
import com.wangfajun.framework.distributedlock.service.GrabService;
import com.wangfajun.framework.distributedlock.service.OrderService;
import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

/**
 * redisson 红锁
 *
 * @author Administrator
 */
@Service("redisRedissonRedLockLockService")
public class GrabRedisRedissonRedLockLockServiceImpl implements GrabService {

//	@Autowired
//	@Qualifier("redissonRed1")
//	private RedissonClient redissonRed1;
//
//	@Autowired
//	@Qualifier("redissonRed2")
//	private RedissonClient redissonRed2;
//
//	@Autowired
//	@Qualifier("redissonRed3")
//	private RedissonClient redissonRed3;

	@Autowired
	Redisson redisson;

	@Autowired
	OrderService orderService;

	@Override
	public String grabOrder(int orderId, int driverId) {
		//生成key
		String lockKey = (RedisKeyConstant.GRAB_LOCK_ORDER_KEY_PRE + orderId).intern();

		//redisson锁 哨兵
        RLock rLock = redisson.getLock(lockKey);
//        rLock.lock();

		//redisson锁 单节点
//        RLock rLock = redissonRed1.getLock(lockKey);

		//红锁 redis son
//		RLock rLock1 = redissonRed1.getLock(lockKey);
//		RLock rLock2 = redissonRed2.getLock(lockKey);
//		RLock rLock3 = redissonRed3.getLock(lockKey);
//		RedissonRedLock rLock = new RedissonRedLock(rLock1, rLock2, rLock3);

		try {
			rLock.lock();
			try {
				TimeUnit.MINUTES.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 此代码默认 设置key 超时时间30秒，过10秒，再延时
			System.out.println("司机:" + driverId + " 执行抢单逻辑");

			boolean b = orderService.grab(orderId, driverId);
			if (b) {
				System.out.println("司机:" + driverId + " 抢单成功");
			} else {
				System.out.println("司机:" + driverId + " 抢单失败");
			}
		} finally {
			rLock.unlock();
		}
		return null;
	}

}