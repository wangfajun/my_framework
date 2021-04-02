package com.wangfajun.framework.core.utils;

import lombok.extern.slf4j.Slf4j;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * bean工具类
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
@Slf4j
public class BeanUtil {

	public static final Pattern p = Pattern.compile("^(\\d+)\\D*(\\d*)\\D*(\\d*)\\D*(\\d*)\\D*(\\d*)\\D*(\\d*)");

	public static final Map<Integer, Character> charMap = new HashMap<Integer, Character>();

	static {
		charMap.put(1, 'y');
		charMap.put(2, 'M');
		charMap.put(3, 'd');
		charMap.put(4, 'H');
		charMap.put(5, 'm');
		charMap.put(6, 's');
	}

	public BeanUtil() {
	}

	public static Map convertBean(Object bean) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

		for (int i = 0; i < propertyDescriptors.length; ++i) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!"class".equals(propertyName)) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}

		return returnMap;
	}

	public static Map<String, String> objectToMap(Object obj) {
		if (obj == null) {
			return null;
		} else {
			HashMap map = new HashMap();

			try {
				String exclude = "crtuser,crttime,updtime,upduser";
				Field[] declaredFields = obj.getClass().getDeclaredFields();
				Field[] var4 = declaredFields;
				int var5 = declaredFields.length;

				for (int var6 = 0; var6 < var5; ++var6) {
					Field field = var4[var6];
					String propertyName = field.getName();
					if (!"class".equals(propertyName) && !exclude.contains(propertyName)) {
						field.setAccessible(true);
						map.put(propertyName, String.valueOf(field.get(obj)));
					}
				}
			} catch (SecurityException var9) {
				var9.printStackTrace();
			} catch (IllegalArgumentException var10) {
				var10.printStackTrace();
			} catch (IllegalAccessException var11) {
				var11.printStackTrace();
			}

			return map;
		}
	}

	public static Object convertMap(Class type, Map map) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		Object obj = type.newInstance();
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

		for (int i = 0; i < propertyDescriptors.length; ++i) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (map.containsKey(propertyName)) {
				Object value = map.get(propertyName);
				if ("joinTime".equals(propertyName) && value != null) {
					try {
						value = stringToDate(value.toString());
						/*String format = dateFormat.format(value);
						value = dateFormat.parse(format);*/
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
				Object[] args = new Object[]{value};
				descriptor.getWriteMethod().invoke(obj, args);
			}
		}

		return obj;
	}

	public static Object beanFieldNullConvertEmpty(Object obj) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		if (obj == null) {
			return null;
		} else {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (int i = 0; i < propertyDescriptors.length; ++i) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!"class".equals(propertyName)) {
					Object result = descriptor.getReadMethod().invoke(obj, new Object[0]);
					if (result == null && "java.lang.String".equals(descriptor.getPropertyType().getName())) {
						descriptor.getWriteMethod().invoke(obj, new Object[]{""});
					}
				}
			}

			return obj;
		}
	}

	public static Boolean existsBeanMethod(Object obj, String methodName) {
		Method[] methods = obj.getClass().getDeclaredMethods();
		Method[] var3 = methods;
		int var4 = methods.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			Method method = var3[var5];
			if (method.getName().indexOf(methodName) != -1) {
				return Boolean.valueOf(true);
			}
		}

		return Boolean.valueOf(false);
	}

	/**
	 * 任意日期字符串转换为Date，不包括无分割的纯数字（13位时间戳除外） ，日期时间为数字，年月日时分秒，但没有毫秒
	 *
	 * @param dateString 日期字符串
	 * @return Date
	 */
	public static Date stringToDate(String dateString) {
		dateString = dateString.trim().replaceAll("[a-zA-Z]", " ");
		if (Pattern.matches("^[-+]?\\d{13}$", dateString)) {//支持13位时间戳
			return new Date(Long.parseLong(dateString));
		}
		Matcher m = p.matcher(dateString);
		StringBuilder sb = new StringBuilder(dateString);
		if (m.find(0)) {//从被匹配的字符串中，充index = 0的下表开始查找能够匹配pattern的子字符串。m.matches()的意思是尝试将整个区域与模式匹配，不一样。
			int count = m.groupCount();
			for (int i = 1; i <= count; i++) {
				for (Map.Entry<Integer, Character> entry : charMap.entrySet()) {
					if (entry.getKey() == i) {
						sb.replace(m.start(i), m.end(i), replaceEachChar(m.group(i), entry.getValue()));
					}
				}
			}
		} else {
			log.info("错误的日期格式");
			return null;
		}
		String format = sb.toString();
		SimpleDateFormat sf = new SimpleDateFormat(format);
		try {
			return sf.parse(dateString);
		} catch (ParseException e) {
			log.info("日期字符串转Date出错");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将指定字符串的所有字符替换成指定字符，跳过空白字符
	 *
	 * @param s 被替换字符串
	 * @param c 字符
	 * @return 新字符串
	 */
	public static String replaceEachChar(String s, Character c) {
		StringBuilder sb = new StringBuilder("");
		for (Character c1 : s.toCharArray()) {
			if (c1 != ' ') {
				sb.append(String.valueOf(c));
			}
		}
		return sb.toString();
	}


}
