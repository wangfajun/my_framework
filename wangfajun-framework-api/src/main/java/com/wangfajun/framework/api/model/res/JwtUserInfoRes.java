package com.wangfajun.framework.api.model.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * jwt token解析返回内容
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Data
public class JwtUserInfoRes {

    /**
     * 会员id
     */
	@ApiModelProperty(value = "会员id")
    private String userId;

    /**
     * 手机号
     */
	@ApiModelProperty(value = "手机号")
    private String mobile;

}
