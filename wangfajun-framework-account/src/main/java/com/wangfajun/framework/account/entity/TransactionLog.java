package com.wangfajun.framework.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * transaction_log
 *
 * @author wangfajun
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "transaction_log", autoResultMap = true)
public class TransactionLog implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 事务ID
     */
    private String id;

    /**
     * 业务标识
     */
    private String business;

    /**
     * 对应业务表中的主键
     */
    private String foreignKey;


}