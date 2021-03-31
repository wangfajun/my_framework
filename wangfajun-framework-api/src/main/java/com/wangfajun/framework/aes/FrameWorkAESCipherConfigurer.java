package com.wangfajun.framework.aes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.UnsupportedEncodingException;

/**
 * 加密
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Configuration
public class FrameWorkAESCipherConfigurer {

	@Value("${aes.key}")
	private String key;

	@Bean
	public FrameWorkAESCipher salaryAesCipher() throws UnsupportedEncodingException {
		return new FrameWorkAESCipher(key);
	}

}
