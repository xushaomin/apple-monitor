package com.appleframework.monitor.plus.weixin;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.appleframework.monitor.plus.ThirdPlus;
import com.appleframework.monitor.plus.weixin.bean.AccessToken;
import com.appleframework.monitor.utils.HttpsUtils;
import com.appleframework.monitor.utils.JsonUtils;
import com.appleframework.monitor.utils.WeixinAccessTokenUtils;

public class WeixinPlus implements ThirdPlus {

	private static final Log logger = LogFactory.getLog(WeixinPlus.class);

	private String appid;
	private String secret;

	private AccessToken accessToken;

	@Override
	public void setThirdKey(String thirdKey) {
		this.appid = thirdKey;
	}

	@Override
	public void setThirdSecret(String thirdSecret) {
		this.secret = thirdSecret;
	}

	@Override
	public void setThirdExtend(String thirdExtend) {
		accessToken = WeixinAccessTokenUtils.getAccessToken(appid, secret);
	}

	@Override
	public boolean doSend(String sendTo, String sendMessage) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + getAccessToken();
		try {
			String result = HttpsUtils.httpsRequest(url, "Post".toUpperCase(), sendMessage);
			logger.info("发送微信推送消息, 微信返回结果result = " + result);
			if (StringUtils.isNotBlank(result)) {
				Map<String, Object> map = JsonUtils.fromJson(result);
				Object errcode = map.get("errcode");
				if ("0".contains(errcode.toString())) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	private String getAccessToken() {
		Date now = new Date();
		if(null == accessToken || accessToken.getCancleTime().before(now)) {
			accessToken = WeixinAccessTokenUtils.getAccessToken(appid, secret);
		}
		return accessToken.getAccessToken();
	}

}
