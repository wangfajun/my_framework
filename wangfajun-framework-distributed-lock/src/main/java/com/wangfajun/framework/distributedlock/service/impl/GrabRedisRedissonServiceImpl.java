package com.wangfajun.framework.distributedlock.service.impl;

import com.wangfajun.framework.distributedlock.service.GrabService;
import com.wangfajun.framework.distributedlock.service.OrderService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

/**
 * Redisson锁
 *
 * @author Administrator
 */
@Service("redisRedissonService")
public class GrabRedisRedissonServiceImpl implements GrabService {

	@Autowired
	RedissonClient redissonClient;

	@Autowired
	OrderService orderService;
	
    @Override
    public String grabOrder(int orderId , int driverId){
        //生成key
    	String lock = "order_"+(orderId+"");
    	
    	RLock rlock = redissonClient.getLock(lock.intern());

        try {

    		rlock.lock();

            try {
				// 此代码默认 设置key 超时时间30秒，过10秒，再延时
                //TimeUnit.MINUTES.sleep(3);

				TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			System.out.println("司机:"+driverId+" 执行抢单逻辑");
			
            boolean b = orderService.grab(orderId, driverId);
            if(b) {
            	System.out.println("司机:"+driverId+" 抢单成功");
            }else {
            	System.out.println("司机:"+driverId+" 抢单失败");
            }
            
        } finally {
        	rlock.unlock();
        }
        return null;
    }
}