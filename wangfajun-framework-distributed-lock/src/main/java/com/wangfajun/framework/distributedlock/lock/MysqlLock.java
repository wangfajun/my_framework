package com.wangfajun.framework.distributedlock.lock;

import com.wangfajun.framework.distributedlock.entity.OrderLock;
import com.wangfajun.framework.distributedlock.mapper.OrderLockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import lombok.Data;

/**
 * mysql分布式锁
 *
 * @author Administrator
 */
@Service
@Data
public class MysqlLock implements Lock {

	@Autowired
	private OrderLockMapper orderLockMapper;
	
	private ThreadLocal<OrderLock> orderLockThreadLocal ;

	@Override
	public void lock() {
		// 1、尝试加锁
		if(tryLock()) {
			System.out.println("尝试加锁");
			return;
		}
		// 2.休眠
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 3.递归再次调用
		lock();
	}
	
	/**
	 * 	非阻塞式加锁，成功，就成功，失败就失败。直接返回
	 */
	@Override
	public boolean tryLock() {
		try {
			OrderLock orderLock = orderLockThreadLocal.get();
			orderLockMapper.insert(orderLock);
			System.out.println("加锁对象："+orderLockThreadLocal.get());
			return true;
		}catch (Exception e) {
			return false;
		}
		
		
	}
	
	@Override
	public void unlock() {
		orderLockMapper.deleteById(orderLockThreadLocal.get().getOrderId());
		System.out.println("解锁对象："+orderLockThreadLocal.get());
		orderLockThreadLocal.remove();
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
