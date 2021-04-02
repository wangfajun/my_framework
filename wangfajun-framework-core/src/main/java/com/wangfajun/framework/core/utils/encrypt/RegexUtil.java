package com.wangfajun.framework.core.utils.encrypt;

/**
 * 国密工具类
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public class RegexUtil {

	public static final String CHECK_SM4 = "\\s*|\t|\r|\n";

	public static final String CHECK_CARD_NAME = "^[\\u4e00-\\u9fa5_a-zA-Z_]{1,64}$";

	public static final String CHECK_NUMBER = "[a-zA-Z0-9_]{1,64}";

	public static final String CHECK_CARD_NUMBER = "[a-zA-Z0-9_]{1,32}";

	public static final String CHECK_EMAIL = "^([a-z0-9A-Z]+[_|-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	public static final String CHECK_TELEPHONE = "^[1][3,4,5,7,8][0-9]{9}$";

	/**
	 * 判断字符串是否为数字,0-9重复0次或者多次
	 *
	 * @param strnum
	 * @return true, 符合; false, 不符合。
	 */
	public static final String CHECK_IS_NUMBER = "[0-9]*";

	public static final String CHECK_BIRTHDAY = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$";

	public static final String CHECK_PULL_TAG = "<([a-zA-Z]+)[^<>]*>";

	public static final String CHECK_IS_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

	public static final String CHECK_ID_CARD = "^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$";

	public static final String REMOVE_XML_NULL_VALUES_TAG = "<(\\w+)></\\1>|<\\w+/>";

	public static final String CHECK_IS_PICTUTR = "^//d+(//.//d+)?$";

}
