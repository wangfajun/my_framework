package com.wangfajun.framework.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Jackson工具
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
public class JacksonUtils {

	public static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static <T> String marshallToString(T t) {
		try {
			return mapper.writeValueAsString(t);
		} catch (Exception e) {
			log.error("MarshallToString error", e);
		}
		return null;
	}

	public static <T> T jsonToObject(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			log.info("JsonToObject error", e);
		}
		return null;
	}

	public static <T> T jsonToObject(String json, TypeReference<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			log.error("JsonToObject error", e);
		}
		return null;
	}

}
