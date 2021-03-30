package com.wangfajun.framework.exception;

/**
 * 自定义枚举
 *
 * @author august
 * @version 1.0
 */
public enum FrameWorkErrorCode {

	/**
	 * 失败
	 */
	SUCCESS("0", "成功"),

	/**
	 * 失败
	 */
	FAILED("-1", "失败"),

	/**
	 * 登录已过期，请重新登录
	 */
	FRAMEWORK_TOKEN_EXPIRE_ERR("10001", "登录已过期，请重新登录"),

	/**
	 * token解析异常
	 */
	FRAMEWORK_TOKEN_ILLEGLE_ERR("10002", "token解析异常"),

	/**
	 * 签名失败
	 */
	FRAMEWORK_SIGN_ERR("10003", "签名失败"),

	/**
	 * 尚未登录，请先登录
	 */
	FRAMEWORK_NO_LOGIN_ERR("10004", "尚未登录，请先登录"),

	/**
	 * 登录方式不存在
	 */
	FRAMEWORK_LOGIN_TYPE_NOT_EXISTS_ERR("10005", "登录方式不存在"),

	/**
	 * 手机号登录失败
	 */
	FRAMEWORK_MOBILE_LOGIN_FAIL("10006", "手机号登录失败"),

	/**
	 * 参数缺失
	 */
	FRAMEWORK_PARAM_LOST_ERR("10007", "参数缺失"),

	/**
	 * 页面超时，请重新授权登录
	 */
	FRAMEWORK_ACCESS_TOKEN_EXPIRE_ERR("10008", "页面超时，请重新授权登录"),

	/**
	 * 微信令牌管理尚未配置
	 */
	FRAMEWORK_WX_CONFIG_ERR("10009", "微信令牌管理尚未配置"),

	/**
	 * 重复请求
	 */
	FRAMEWORK_DUPLICATE_REQ_ERR("10010", "重复请求"),

	/**
	 * 登录失败
	 */
	FRAMEWORK_LOGIN_ERR("10011", "登录失败"),

	/**
	 * 当前账号已被冻结
	 */
	FRAMEWORK_STATUS_INVALID_ERR("10012", "当前账号已被冻结"),

	/**
	 * 当前用户已在其他设备登录
	 */
	FRAMEWORK_LOGIN_ANOTHER_AREA_ERR("10013", "当前用户已在其他设备登录"),

	/**
	 * 无权限访问
	 */
	FRAMEWORK_AUTHORIZATION_ERR("10014",  "无权限访问"),
	/**
	 * 无效的token
	 */
	FRAMEWORK_AUTHORIZATION_TOKEN_INVALID_ERR("10015",  "无效的token"),
	/**
	 * token缺失
	 */
	FRAMEWORK_AUTHORIZATION_TOKEN_EMPTY_ERR("10016",  "token缺失"),

	/**
	 * 系统异常
	 */
	FRAMEWORK_SYS_ERR("500","系统异常"),

	;
	/**
	 * 业务异常码
	 */
	private String bizCode;

	/**
	 * 异常消息
	 */
    private String msg;

    FrameWorkErrorCode(String bizCode, String msg){
        this.bizCode = bizCode;
        this.msg = msg;
    }

    public String getBizCode() {
        return bizCode;
    }

    public String getMsg() {
        return msg;
    }

}
