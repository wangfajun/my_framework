package com.wangfajun.framework.utils;

import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * redisson锁工具类
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Component
@AllArgsConstructor
public class RedissonLockUtils {

	private RedissonClient redissonClient;

	/**
	 * 锁key
	 *
	 * @param lockKey
	 */
	public void lock(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock();
	}

	/**
	 * 根据key解锁
	 *
	 * @param lockKey
	 */
	public void unlock(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.unlock();
	}

	/**
	 * 加锁
	 *
	 * @param lockKey   key
	 * @param leaseTime 至少尝试加锁时间
	 */
	public void lock(String lockKey, int leaseTime) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock(leaseTime, TimeUnit.MILLISECONDS);
	}

	/**
	 * 加锁
	 *
	 * @param lockKey key
	 * @param timeout 超时时间
	 * @param unit    单位
	 */
	public void lock(String lockKey, int timeout, TimeUnit unit) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock(timeout, unit);
	}

	/**
	 * 非阻塞加锁
	 *
	 * @param lockKey
	 * @return
	 */
	public boolean tryLock(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
		return lock.tryLock();
	}

	/**
	 * 非阻塞加锁
	 *
	 * @param lockKey   key
	 * @param waitTime  超时时间
	 * @param leaseTime 至少尝试加锁时间
	 * @param unit      单位
	 * @return
	 * @throws InterruptedException
	 */
	public boolean tryLock(String lockKey, long waitTime, long leaseTime,
						   TimeUnit unit) throws InterruptedException {
		RLock lock = redissonClient.getLock(lockKey);
		return lock.tryLock(waitTime, leaseTime, unit);
	}

	/**
	 * 判断是否已加锁
	 *
	 * @param lockKey key
	 * @return
	 */
	public boolean isLocked(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
		return lock.isLocked();
	}
}
