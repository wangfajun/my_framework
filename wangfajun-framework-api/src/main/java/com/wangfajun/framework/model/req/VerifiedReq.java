package com.wangfajun.framework.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value="实名认证", description="实名认证")
public class VerifiedReq {

	@ApiModelProperty(value = "姓名")
	@NotEmpty(message = "姓名不能为空")
	private String name;

	@ApiModelProperty(value = "身份证")
	@NotEmpty(message = "身份证号不能为空")
	private String idCard;
}
