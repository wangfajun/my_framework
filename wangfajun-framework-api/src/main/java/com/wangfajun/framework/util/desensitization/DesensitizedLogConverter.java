package com.wangfajun.framework.util.desensitization;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日志脱敏
 *
 * @author wy
 * @Date 2021/1/7 14:42
 */
@Slf4j
public class DesensitizedLogConverter extends MessageConverter {

	/**
	 * 脱敏规则
	 */
	private static final Map<Pattern, String> RULE = new ImmutableMap.Builder<Pattern, String>()
			// (mobile|phone|phoneNo),需要脱敏的key,与实体类需匹配属性名
			// (:|":"|=),匹配的输出格式:key=value 或 key:value
			// (\\d{4})\\d+(\\d{4})"),{字符串左边保留长度} **** {字符串右边保留长度}
			//手机号
			.put(Pattern.compile("(mobile|phone|phoneNo)(:|\":\"|=)(\\d{3})\\d{4}(\\d{4})"), "$1$2$3****$4")
			//银行卡号
			.put(Pattern.compile("(cardNo|cardNo|bankCard|bankCardNo)(:|\":\"|=)(\\d{4})\\d+(\\d{4})"), "$1$2$3********$4")
			//身份证
			.put(Pattern.compile("(idCard|identity|identityNumber|corpLegalPersonCardNo)(:|\":\"|=)(\\d{6})\\d+(\\d{4})"), "$1$2$3*******$4")
			//姓名
			.put(Pattern.compile("(realName|custName|userName|username|corpLegalPerson|name)(:|\":\"|=)([\\u2E80-\\uFE4F])[\\u2E80-\\uFE4F]+"), "$1$2$3**")
			.build();


	/**
	 * 日志脱敏逻辑
	 *
	 * @param event 输出的日志对象
	 * @return 脱敏后的字符串
	 */
	@Override
	public String convert(ILoggingEvent event) {
		String message = event.getFormattedMessage();
		for (Map.Entry<Pattern, String> entry : RULE.entrySet()) {
			final Matcher matcher = entry.getKey().matcher(message);
			if (matcher.find()) {
				message = matcher.replaceAll(entry.getValue());
			}
		}
		return message;
	}
}
