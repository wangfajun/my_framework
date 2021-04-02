package com.wangfajun.framework.api.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录成功返回信息
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Data
@ApiModel(value = "登录成功返回信息")
public class DemoLoginRes {

    /**
     * 会员Id
     */
    @ApiModelProperty(value = "会员Id")
    private Long userId;

    /**
     * 会员名称
     */
    @ApiModelProperty(value = "会员名称")
    private String userName;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;

	/**
	 * 会员头像（暂时固定）
	 */
	@ApiModelProperty(value = "会员头像")
	private String userImgUrl;

	/**
	 * 身份证
	 */
	@ApiModelProperty(value = "身份证")
	private String idCard;

    /**
     * jwt登录令牌
     */
	@ApiModelProperty(value="jwt登录令牌")
    private String token;

}
