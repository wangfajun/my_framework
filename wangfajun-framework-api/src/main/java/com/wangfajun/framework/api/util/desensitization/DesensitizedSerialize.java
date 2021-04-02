package com.wangfajun.framework.api.util.desensitization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;
import java.util.Objects;

/**
 * 数据脱敏序列化类
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public class DesensitizedSerialize extends JsonSerializer<String> implements ContextualSerializer {

	private DesensitizedEnum type;

	public DesensitizedSerialize() {
	}

	public DesensitizedSerialize(DesensitizedEnum type) {
		this.type = type;
	}

	@Override
	public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		switch (this.type) {
			case CHINESE_NAME:
				jsonGenerator.writeString(DesensitizedUtils.chineseName(s));
				break;
			case ID_CARD:
				jsonGenerator.writeString(DesensitizedUtils.idCardNum(s));
				break;
			case FIXED_PHONE:
				jsonGenerator.writeString(DesensitizedUtils.fixedPhone(s));
				break;
			case MOBILE_PHONE:
				jsonGenerator.writeString(DesensitizedUtils.mobilePhone(s));
				break;
			case ADDRESS:
				jsonGenerator.writeString(DesensitizedUtils.address(s));
				break;
			case EMAIL:
				jsonGenerator.writeString(DesensitizedUtils.email(s));
				break;
			case BANK_CARD:
				jsonGenerator.writeString(DesensitizedUtils.bankCard(s));
				break;
			case PASSWORD:
				jsonGenerator.writeString(DesensitizedUtils.password(s));
				break;
			default:
				break;
		}
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
		//非空则序列化
		if (!Objects.isNull(beanProperty)) {
			if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
				DesensitizedAnnotation desensitizedAnnotation = beanProperty.getAnnotation(DesensitizedAnnotation.class);
				if (Objects.isNull(desensitizedAnnotation)) {
					desensitizedAnnotation = beanProperty.getContextAnnotation(DesensitizedAnnotation.class);
				}
				//得到脱敏注解,将脱敏类型传入序列化类
				if (!Objects.isNull(desensitizedAnnotation)) {
					return new DesensitizedSerialize(desensitizedAnnotation.value());
				}
			}
			return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
		}
		return serializerProvider.findNullValueSerializer(null);
	}
}
