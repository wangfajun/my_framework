package com.wangfajun.framework.core.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wangfajun.framework.constant.CommonConstants;
import com.wangfajun.framework.core.entity.Demo;
import com.wangfajun.framework.model.res.JwtUserInfoRes;
import com.wangfajun.framework.core.exception.FrameWorkErrorCode;
import com.wangfajun.framework.core.exception.FrameworkErrorException;
import com.wangfajun.framework.core.utils.encrypt.Sm4utils;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * 用户登录Jwt令牌工具类
 *
 * @author wangfajun
 * @version 1.0
 * @date 2021/3/30 19:56
 */
public class JwtTokenUtil {

	private static Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

	/**
	 * JWT令牌存放头中的key
	 */
	public static final String AUTH_HEADER_KEY = "Authorization";

	/**
	 * JWT令牌值前缀
	 */
	public static final String TOKEN_PREFIX = "Bearer ";

	public static final int TOKEN_PREFIX_LENTH = 7;

	/**
	 * 解析jwt
	 *
	 * @param jsonWebToken
	 * @param base64Security
	 */
	public static Claims parseJwt(String jsonWebToken, String base64Security) {
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
					.parseClaimsJws(jsonWebToken).getBody();
			return claims;
		} catch (ExpiredJwtException eje) {
			log.error("===== Token过期 =====");
			throw new FrameworkErrorException(FrameWorkErrorCode.FRAMEWORK_TOKEN_EXPIRE_ERR);
		} catch (Exception e) {
			log.error("===== token解析异常 =====");
			throw new FrameworkErrorException(FrameWorkErrorCode.FRAMEWORK_TOKEN_ILLEGLE_ERR);
		}
	}

	/**
	 * 构建jwt
	 *
	 * @param userInfo 员工信息
	 */
	public static String createJwt(Demo userInfo) {
		try {
			// 使用HS256加密算法
			SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

			long nowMillis = System.currentTimeMillis();
			Date now = new Date(nowMillis);

			//生成签名密钥
			byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(CommonConstants.FRAMEWORK_BASE64_SECRET);
			Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

			//载荷SM4加密
			String encryUserId = Sm4utils.encryptDataEcb(String.valueOf(userInfo.getUserId()), CommonConstants.FRAMEWORK_SM4_SECRET);
			String encryMobile = Sm4utils.encryptDataEcb(String.valueOf(userInfo.getMobile()), CommonConstants.FRAMEWORK_SM4_SECRET);

			//添加构成JWT的参数
			JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
					// 可以将基本不重要的对象信息放到claims
					.claim("userId", encryUserId)
					.claim("mobile", encryMobile)
					// 代表这个JWT的主体，即它的所有人
					.setSubject(encryMobile)
					// 代表这个JWT的签发主体；
					.setIssuer(CommonConstants.FRAMEWORK_CLIENT_ID)
					// 是一个时间戳，代表这个JWT的签发时间；
					.setIssuedAt(new Date())
					// 代表这个JWT的接收对象；
					.setAudience(CommonConstants.FRAMEWORK_NAME)
					.signWith(signatureAlgorithm, signingKey);

			//添加Token过期时间，单位为毫秒，目前设置30天，比redis中的5天令牌要长
			int ttlMillis = CommonConstants.FRAMEWORK_JWT_TOKEN_EXPIRE_SECOND * 1000;
			if (ttlMillis >= 0) {
				long expMillis = nowMillis + ttlMillis;
				Date exp = new Date(expMillis);
				// 是一个时间戳，代表这个JWT的过期时间；
				builder.setExpiration(exp)
						// 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
						.setNotBefore(now);
			}

			//生成JWT
			return builder.compact();
		} catch (Exception e) {
			log.error("签名失败", e);
			throw new FrameworkErrorException(FrameWorkErrorCode.FRAMEWORK_SIGN_ERR);
		}
	}

	/**
	 * 获取请求头token令牌
	 */
	public static String getHeaderToken() {
		// 获取请求头信息authorization信息
		final String authHeader = ContextHolderUtils.getRequest().getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
		log.info("## authHeader= {}", authHeader);

		if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
			log.info("### 用户未登录，请先登录 ###");
			throw new FrameworkErrorException(FrameWorkErrorCode.FRAMEWORK_NO_LOGIN_ERR);
		}

		// 获取token：以Bearer 前缀+空格开头
		final String token = authHeader.substring(JwtTokenUtil.TOKEN_PREFIX_LENTH);
		return token;
	}

	/**
	 * 从token中获取会员信息
	 */
	public static JwtUserInfoRes getUserInfo(String token) {
		Claims claims = JwtTokenUtil.parseJwt(token, CommonConstants.FRAMEWORK_BASE64_SECRET);
		JwtUserInfoRes dto = getJwtMemberResDto(claims);
		return dto;
	}

	/**
	 * 根据请求头中的token,获取会员信息
	 */
	public static JwtUserInfoRes getUserInfo() {
		String token = getHeaderToken();
		Claims claims = JwtTokenUtil.parseJwt(token, CommonConstants.FRAMEWORK_BASE64_SECRET);
		JwtUserInfoRes dto = getJwtMemberResDto(claims);
		return dto;
	}

	/**
	 * 解密
	 *
	 * @param claims
	 */
	private static JwtUserInfoRes getJwtMemberResDto(Claims claims) {
		String encryUserId = claims.get("userId", String.class);
		String encryMobile = claims.get("mobile", String.class);

		// SM4解密
		String userId = Sm4utils.decryptDataEcb(encryUserId, CommonConstants.FRAMEWORK_SM4_SECRET);
		String mobile = Sm4utils.decryptDataEcb(encryMobile, CommonConstants.FRAMEWORK_SM4_SECRET);

		// 构建会员对象
		JwtUserInfoRes dto = new JwtUserInfoRes();
		dto.setUserId(userId);
		dto.setMobile(mobile);
		return dto;
	}

	/**
	 * token是否已过期
	 */
	public static boolean isExpiration(String token) {
		return JwtTokenUtil.parseJwt(token, CommonConstants.FRAMEWORK_BASE64_SECRET).getExpiration().before(new Date());
	}

}
