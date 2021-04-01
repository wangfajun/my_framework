package com.wangfajun.framework.constant;

/**
 * 公共常量
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public interface CommonConstants {

	/**
	 * 订单交换机
	 */
	String ORDER_EXCHANGE = "order-exchange";

	/**
	 * 订单死信交换机
	 */
	String ORDER_DEAD_EXCHANGE = "order-dead-exchange";

	/**
	 * 订单队列名称
	 */
	String ORDER_QUEUE = "order-queue";

	/**
	 * 订单死信队列名称
	 */
	String ORDER_DEAD_QUEUE = "order-dead-queue";

	/**
	 * 订单key
	 */
	String ORDER_KEY = "order.key";

	/**
	 * 订单死信key
	 */
	String ORDER_DEAD_KEY = "order.dead.key";

	/**
	 * 成功标记
	 */
	Integer SUCCESS = 0;

	/**
	 * 失败标记
	 */
	Integer FAIL = -1;

	/**
	 * 登录，手机号作为前缀分布式key
	 */
	String FRAMEWORK_DISTRIBUTED_LOGIN_KEY = "flexible:login:";

	/**
	 * 手机号登录类型
	 */
	String MOBILE_CLIENT_TYPE_LOGIN = "mobileLogin";

	/**
	 * 登录令牌
	 */
	String FRAMEWORK_LOGIN_TOKEN = "login:token:";

	/**
	 * JWT令牌参数 签名加密秘钥（base64）
	 */
	String FRAMEWORK_BASE64_SECRET = "WkhVTkRJQU5RSVlFRlVXVVNISVlFQlVBSUxBT1dBTkc=";

	/**
	 * JWT的签发主体
	 */
	String FRAMEWORK_CLIENT_ID = "ee7c739cfdbb4ae6bc3fb69262732277";

	/**
	 * JWT的接收对象
	 */
	String FRAMEWORK_NAME = "ZHUNDIAN";

	/**
	 * JWT令牌失效时间,30天
	 */
	Integer FRAMEWORK_JWT_TOKEN_EXPIRE_SECOND = 3600 * 24 * 30;

	/**
	 * SM4对称加密秘钥,用于jwt载荷加密
	 */
	String FRAMEWORK_SM4_SECRET = "38dd05d98eecc818";

	/**
	 * 准点登录，redis中的令牌失效时间5小时
	 */
	Integer FRAMEWORK_REDIS_TOKEN_EXPIRE_SECOND = 3600 * 5;

	/**
	 * 分布式锁等待时间
	 */
	Long WAIT_TIME = 3000L;
	Long LEASE_TIME = 5000L;

	/**
	 * 员工默认头像
	 */
	String FRAMEWORK_DEFAULT_IMG_URL = "http://static-zhundian-test.limahui.com.cn/salary/defaultAvatar@3x.png";

	/**
	 * 手机验证码前缀
	 */
	String MNS_SMS_VERIFY_CODE_KEY = "mns:verifyCode:";

	/**
	 * 客户端类型，登录发短信验证码以这个为clientReqType
	 */
	String FRAMEWORK_CLIENT_TYPE = "frameword:";
}
