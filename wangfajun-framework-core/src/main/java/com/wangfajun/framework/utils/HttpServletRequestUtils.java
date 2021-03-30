package com.wangfajun.framework.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Maps;
import com.wangfajun.framework.exception.FrameworkErrorException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author zhengyangjun
 * @version 1.0
 * @Description request请求参数封装
 * @date 2020/11/18 09:34
 */
public class HttpServletRequestUtils {

	private HttpServletRequestUtils() {

	}

	public static String getRequestInfo(HttpServletRequest request) {
		String method = request.getMethod();
		if ("GET".equals(method) && StringUtils.isNotBlank(request.getQueryString())) {
			try {
				return URLDecoder.decode(request.getQueryString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		Map<String, String[]> map = request.getParameterMap();
		StringBuffer params = new StringBuffer();
		if (map == null) {
			return "";
		}
		Iterator<String> keys = map.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			String[] values = map.get(key);
			String value = Arrays.toString(values);
			if (value != null && value.length() > 1) {
				value = value.substring(1, value.length() - 1);
			}
			params.append(key + "=" + value + "&");
		}
		if (params.length() > 0) {
			return params.substring(0, params.length() - 1);
		}
		return params.toString();
	}

	/**
	 * 请求参数封装成Map
	 *
	 * @param request
	 * @return
	 */
	public static Map<String, String> getRequestInfoMap(HttpServletRequest request) {
		Map<String, String> mapParam = new HashMap<>();
		try {
			String info = getRequestInfo(request);
			mapParam = getRequestInfoMap(info);
		} catch (Exception e) {
			throw new FrameworkErrorException("请求参数错误：" + e.getMessage());
		}
		return mapParam;
	}

	/**
	 * 请求参数封装成Map
	 *
	 * @param request
	 * @return
	 */
	public static Map<String, String> getParameterToMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
		}
		return map;
	}

	/**
	 * 请求参数封装成JSON
	 *
	 * @param request
	 * @return
	 */
	public static JSONObject getParameterToJSON(HttpServletRequest request) throws IOException {
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();
		String inputStr;
		while ((inputStr = streamReader.readLine()) != null) {
			responseStrBuilder.append(inputStr);
		}
		if (StringUtils.isBlank(responseStrBuilder.toString())) {
			throw new FrameworkErrorException("请求参数不能为空!!!");
		}
		JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
		return jsonObject;
	}

	public static Map<String, String> getRequestInfoMap(String info) {
		Map<String, String> map = Maps.newHashMap();
		if (StringUtils.isNotBlank(info)) {
			String[] params = info.split("&");
			Arrays.stream(params).forEach(param -> {
				String[] s = param.split("=");
				map.put(s[0], s[1]);
			});
		}
		return map;
	}

	public static void main(String[] args) {
		//单笔提现到卡
//		String json = "{\"innerTradeNo\":\"101160799744064140397\",\"charset\":\"UTF-8\",\"notifyTime\":\"20201215101200\",\"accountType\":\"BASIC\",\"withdrawalStatus\":\"TRADE_FINISHED\",\"version\":\"2.0\",\"uid\":\"20201208\",\"productCode\":\"10210\",\"notifyType\":\"withdrawal_status_sync\",\"withdrawalAmount\":\"100.01\",\"outerTradeNo\":\"83723329298501555\",\"signType\":\"RSA\",\"notifyId\":\"NT202012150960524623\",\"gmtClose\":\"20201215095951\"}";
		//子账户汇入
		String json = "{\"charset\":\"UTF-8\",\"chnlId\":\"CHNLID\",\"notifyTime\":\"20201215101449\",\"txId\":\"202012151014479708573485800\",\"gmtCreate\":\"20201215101447\",\"version\":\"2.0\",\"payeeCardName\":\"新增企业八\",\"subAccount\":\"5845342722920172763\",\"extInfo\":\"扩展信息\",\"payerBankOrgId\":\"308290003011\",\"payerRemark\":\"TEST汇款\",\"productCode\":\"10102\",\"notifyType\":\"remit_sync\",\"subAccountName\":\"新增企业八\",\"payerCardName\":\"zyjzyj\",\"outerTradeNo\":\"202012151014479708573485800\",\"signType\":\"RSA\",\"notifyId\":\"NT202012151060524628\",\"remitAmount\":\"88888.0000\",\"payerCardNo\":\"5845342111111111111\",\"payeeCardNo\":\"5845342722920172763\",\"status\":\"SUCCESS\"}";
		JSONObject jsonObject = JSONObject.parseObject(json);
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		System.out.println(sb);
	}


	/**
	 * 获取API接口
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestURL(HttpServletRequest request) {
		String url = request.getRequestURI();
		return url.substring(url.lastIndexOf("/") + 1, url.length());
	}

}
