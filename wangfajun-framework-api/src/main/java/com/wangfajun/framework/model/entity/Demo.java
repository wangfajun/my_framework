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
 * demo表
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "fm_demo", autoResultMap = true)
public class Demo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long userId;

    /**
     * 会员名称
     */
    private String userName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 头像
     */
    private String userImgUrl;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 0:启用;1:禁用
     */
    private Integer status;

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
