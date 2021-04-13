package com.wangfajun.framework.account.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 账户表
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "user_account", autoResultMap = true)
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号 主键
     */
    private String id;

	/**
	 * 用户状态(0:可用，1：禁用)
	 */
	private Integer status;

	/**
	 * 用户名
	 */
	private String userName;

	/**
     * 创建时间
     */
    private Date createTime;

}
