package com.appleframework.monitor.utils;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.monitor.plus.weixin.bean.AccessToken;

public class WeixinAccessTokenUtils {

	private static final Logger logger = LoggerFactory.getLogger(WeixinAccessTokenUtils.class);	

	public static AccessToken getAccessToken(final String appid, final String secret) {
		String accessToken = findAccessToken(appid, secret);
		if (StringUtils.isBlank(accessToken)) {
			accessToken = httpAccessToken(appid, secret);
		}
		if (StringUtils.isBlank(accessToken)) {
			logger.error("appid = {},secret = {},from send http get is null", appid, secret);
			return null;
		}
		return getAccessToken(accessToken);
	}
	
	public static AccessToken getAccessToken(final String accessToken) {
		AccessToken acces = new AccessToken();
		acces.setAccessToken(accessToken);
		Date date = DateUtil.getDate();
		String ticket = httpWeixinTicket(accessToken);
		if (StringUtils.isBlank(ticket)) {
			logger.info("accessToken = {},获取ticket为空", accessToken);
			return null;
		}
		acces.setJsapiTicket(ticket);
		acces.setAccessTime(date);
		acces.setCancleTime(DateUtil.addMinute(date, 60));
		return acces;
	}
	
	public static String findAccessToken(final String appid, final String secret) {
		return httpAccessToken(appid,secret);
	}
	
	public static String findWeixinTicket(String accessToken) {
		return httpWeixinTicket(accessToken);
	}
	
	public static String validAccessTokenAndRefush(String result, String appid, String secret) {
		if (StringUtils.isNotBlank(result)) {
			Map<String, Object> map;
			try {
				map = JsonUtils.fromJson(result);
				Object errcode = map.get("errcode");
				logger.info("AccessToken request,weixin response result = {}", result);
				if (errcode != null && !"0".equals(String.valueOf(errcode))) {
					return removeAndGetAccessToken(appid, secret);
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	public static String removeAndGetAccessToken(String appid, String secret) {
		return findAccessToken(appid, secret);
	}

	public static String httpAccessToken(String appid, String secret) {
		String accessTokenUrl = buildWeixinAccessTokenUrl(appid, secret);
		String result = HttpsUtils.getMethod(accessTokenUrl);
		logger.info("get http accessToken result = {}", result);
		Map<String, String> json2map = JsonUtils.mapStrFromJson(result);
		String accessToken = json2map.get("access_token");
		logger.info("appid = {},secret = {},send http get accessToken = {}", appid, secret, accessToken);
		return accessToken;
	}
	
	public static String httpWeixinTicket(String accessToken) {
		String ticketUrl = buildWeixinTicketUrl(accessToken);
		String result = HttpsUtils.getMethod(ticketUrl);
		logger.info("get http ticket result = {}", result);
		Map<String, String> json2map = JsonUtils.mapStrFromJson(result);
		String ticket = json2map.get("ticket");
		logger.info("accessToken = {},send http get ticket = {}", accessToken, ticket);
		return ticket;
	}

	public static String buildWeixinAccessTokenUrl(String appid, String secret) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("https://api.weixin.qq.com/cgi-bin/token");
		stringBuilder.append("?");
		stringBuilder.append("grant_type=client_credential&");
		stringBuilder.append("appid=" + appid + "&");
		stringBuilder.append("secret=" + secret);
		return stringBuilder.toString();
	}
	
	public static String buildWeixinTicketUrl(String accessToken) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("https://api.weixin.qq.com/cgi-bin/ticket/getticket");
		stringBuilder.append("?");
		stringBuilder.append("access_token=" + accessToken + "&");
		stringBuilder.append("type=jsapi");
		return stringBuilder.toString();
	}
	
	public static void main(String[] args) {
		AccessToken token = WeixinAccessTokenUtils.getAccessToken("wxff1726c97bf85704", "41a0187762b37a392b73466e88a97c8e");
		System.out.println(token.getAccessToken());
	}
}
