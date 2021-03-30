package com.wangfajun.framework.client;

import com.wangfajun.framework.config.FlexibleFeignConfig;
import com.wangfajun.framework.constant.CommonConstants;
import com.wangfajun.framework.util.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;

@FeignClient(
		name = CommonConstants.REMOTE_CLIENT,
		url = "${otherService.service.url}",
		configuration = FlexibleFeignConfig.class
)
public interface OtherRemoteClient {

	@ApiOperation(value = "demo请求路径", notes = "demo描述")
	@PostMapping("/demo")
    R demo(@RequestBody @Valid Object object);

}
