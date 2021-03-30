package com.wangfajun.framework.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *  灵活用工用户表
 * </p>
 *
 * @author wangfajun
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "fl_user_info", autoResultMap = true)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 会员名称
     */
    private String userName;

    /**
     * 手机号
     */
//	@TableField(typeHandler = SalaryAESEncryptHandler.class)
    private String mobile;

    /**
     * 会员头像
     */
    private String userImgUrl;

    /**
     * 身份证
     */
//	@TableField(typeHandler = SalaryAESEncryptHandler.class)
    private String idCard;

    /**
     * 0:启用;1:禁用
     */
    private Integer status;

	/**
	 * 银行账号
	 */
	private String bankNo;

	/**
	 * 银行名称
	 */
	private String bankName;

	/**
	 * 银行类型
	 */
	private String bankType;

	/**
	 * 阿里账号
	 */
	private String aliPayNo;

	/**
	 * 是否已经认证 0:默认未认证 1:已认证
	 */
	private Integer isCert;

	/**
	 * 认证时间
	 */
	private Date certTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUser;

}
