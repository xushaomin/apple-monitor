package com.appleframework.monitor.plus.push;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.config.core.PropertyConfigurer;
import com.appleframework.monitor.plus.ThirdPlus;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class PushPlusOfJPush implements ThirdPlus {
	
	private final static Logger logger = LoggerFactory.getLogger(PushPlusOfJPush.class);
		
	private JPushClient jpushClient;
	
	private String appKey;
	private String appSecret;
        
    /***
	 * 实时推送APP 报警信息
	 * @param type
	 * @param time
     * @throws Exception 
	 */
	private boolean push(PushMessage message) {
		boolean isResultOk = false;
		if(pushAndroid(message))
			isResultOk = true;
		if(pushIos(message))
			isResultOk = true;
		return isResultOk;
	}
	
	/***
	 * 实时推送APP 报警信息
	 * @param type
	 * @param time
     * @throws Exception 
	 */
	public boolean pushAndroid(PushMessage message) {
		PushPayload andPushPayload = null;
		//Message msg = null;
		int mode = message.getMode();
		Map<String, String> data = message.getData();
		/*if(data.size() > 0) {
			msg = Message.newBuilder().setMsgContent(message.getContent()).addExtras(data).build();
		}
		else {
			msg = Message.newBuilder().setMsgContent(message.getContent()).build();
		}*/
		
		if(mode == PushMessage.MESSAGE_MODE_BROADCAST) {		
			andPushPayload = PushPayload.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(Audience.all())
					.setNotification(Notification.newBuilder()
	                        .addPlatformNotification(AndroidNotification.newBuilder()
	                        		.setTitle(message.getTitle())
	                                .setAlert(message.getContent())
	                                //.setBadge(1)
	                                //.setSound("default")
	                                .addExtras(data)
	                                .build())
	                        .build())
					/*.setMessage(msg)*/.build();
		}
		else if(mode == PushMessage.MESSAGE_MODE_MULTICAST) {
			String receiver = message.getReceiver();
			String[] tags = receiver.split(",");
			andPushPayload = PushPayload.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(Audience.tag(tags))
					.setNotification(Notification.newBuilder()
	                        .addPlatformNotification(AndroidNotification.newBuilder()
	                        		.setTitle(message.getTitle())
	                                .setAlert(message.getContent())
	                                //.setBadge(1)
	                                //.setSound("default")
	                                .addExtras(data)
	                                .build())
	                        .build())
					/*.setMessage(msg)*/.build();
		}
		else {
			String receiver = message.getReceiver();
			String[] alias = receiver.split(",");
			andPushPayload = PushPayload.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(Audience.alias(alias))
					.setNotification(Notification.newBuilder()
	                        .addPlatformNotification(AndroidNotification.newBuilder()
	                        		.setTitle(message.getTitle())
	                                .setAlert(message.getContent())
	                                //.setBadge(1)
	                                //.setSound("default")
	                                .addExtras(data)
	                                .build())
	                        .build())
					/*.setMessage(msg)*/.build();
		}

		try {
			PushResult andResult = jpushClient.sendPush(andPushPayload);
			logger.info("ios push = " + andPushPayload.toString());
			//result = jpushIos.sendIosNotificationWithAlias(msgContent, extras, alias);
			if(andResult.isResultOK()) {
				return true;
			}
			else {
				return false;
			}
		} catch (APIRequestException e) {
			logger.error(e.getMessage());
			return false;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	
	/***
	 * 实时推送APP 报警信息
	 * @param type
	 * @param time
     * @throws Exception 
	 */
	public boolean pushIos(PushMessage message) {
		
		String apnsProduction = PropertyConfigurer.getValue("apns.production", "false");
		PushPayload iosPushPayload = null;
		int type = message.getMode();
		Map<String, String> data = message.getData();
		/*if(data.size() > 0) {
			msg = Message.newBuilder().setMsgContent(message.getContent()).addExtras(data).build();
		}
		else {
			msg = Message.newBuilder().setMsgContent(message.getContent()).build();
		}*/
		if(type == PushMessage.MESSAGE_MODE_BROADCAST) {
			iosPushPayload = PushPayload.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(Audience.all())
					.setNotification(Notification.newBuilder()
	                        .addPlatformNotification(IosNotification.newBuilder()
	                                .setAlert(message.getContent())
	                                //.setBadge(1)
	                                //.setSound("default")
	                                .addExtras(data)
	                                .build())
	                        .build())
					.setOptions(Options.newBuilder().setApnsProduction(Boolean.valueOf(apnsProduction)).build())
					/*.setMessage(msg)*/.build();
		}
		else if(type == PushMessage.MESSAGE_MODE_MULTICAST) {
			String receiver = message.getReceiver();
			String[] tags = receiver.split(",");
			iosPushPayload = PushPayload.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(Audience.tag(tags))
					.setNotification(Notification.newBuilder()
	                        .addPlatformNotification(IosNotification.newBuilder()
	                                .setAlert(message.getContent())
	                                //.setBadge(1)
	                                //.setSound("default")
	                                .addExtras(data)
	                                .build())
	                        .build())
					.setOptions(Options.newBuilder().setApnsProduction(Boolean.valueOf(apnsProduction)).build())
					/*.setMessage(msg)*/.build();
		}
		else {
			String receiver = message.getReceiver();
			String[] alias = receiver.split(",");
			iosPushPayload = PushPayload.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(Audience.alias(alias))
					.setNotification(Notification.newBuilder()
	                        .addPlatformNotification(IosNotification.newBuilder()
	                                .setAlert(message.getContent())
	                                //.setBadge(1)
	                                //.setSound("default")
	                                .addExtras(data)
	                                .build())
	                        .build())
					.setOptions(Options.newBuilder().setApnsProduction(Boolean.valueOf(apnsProduction)).build())
					/*.setMessage(msg)*/.build();
		}

		try {
			PushResult iosResult = jpushClient.sendPush(iosPushPayload);
			logger.info("ios push = " + iosPushPayload.toString());
			//result = jpushIos.sendIosNotificationWithAlias(msgContent, extras, alias);
			if(iosResult.isResultOK()) {
				return true;
			}
			else {
				return false;
			}
		} catch (APIRequestException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void setThirdKey(String thirdKey) {
		this.appKey = thirdKey;
	}

	@Override
	public void setThirdSecret(String thirdSecret) {
		this.appSecret = thirdSecret;
		
	}

	@Override
	public void setThirdExtend(String thirdExtend) {
		this.jpushClient = new JPushClient(appSecret, appKey);
	}

	@Override
	public boolean doSend(String sendTo, String sendMessage) {
		PushMessage message = new PushMessage();
		message.setReceiver(sendTo);
		message.setContent(sendMessage);
		message.setMode(PushMessage.MESSAGE_MODE_DIRECTIONAL);
		return this.push(message);
	}
	
}
