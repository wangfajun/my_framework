package com.wangfajun.framework.distributedlock.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 分布式订单锁表
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "order_lock", autoResultMap = true)
public class OrderLock implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId
    private Integer orderId;

    private Integer driverId;

}