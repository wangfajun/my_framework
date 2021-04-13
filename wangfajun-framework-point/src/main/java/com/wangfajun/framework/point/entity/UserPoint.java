package com.wangfajun.framework.point.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 积分表
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "user_point", autoResultMap = true)
public class UserPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 用户编号
     */
    private String userId;

	/**
	 * 积分状态(0:可用，1：禁用)
	 */
	private Integer status;

	/**
	 * 积分数
	 */
	private Integer points;

    /**
     * 创建时间
     */
    private Date createTime;

}
