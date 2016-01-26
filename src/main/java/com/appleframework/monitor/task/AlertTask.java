package com.appleframework.monitor.task;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;  
import org.springframework.stereotype.Component;

import com.appleframework.core.utils.ObjectUtility;
import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.config.ApplicationConfigManager;
import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.jmx.database.constant.PlusType;
import com.appleframework.jmx.database.entity.AlertContactEntity;
import com.appleframework.jmx.database.entity.AppConfigEntity;
import com.appleframework.jmx.database.service.AlertGroupContactService;
import com.appleframework.jmx.database.service.AppConfigService;
import com.appleframework.jmx.webui.view.ApplicationViewHelper;
import com.appleframework.monitor.plus.ThirdPlus;
import com.appleframework.monitor.service.PlusService;

@Component("alertTask")
public class AlertTask {
	
	private static final Logger logger = Loggers.getLogger(AlertTask.class);
	
	@Resource
	private AppConfigService appConfigService;
	
	@Resource
	private ApplicationViewHelper applicationViewHelper;
	
	@Resource
	private PlusService plusService;
	
	@Resource
	private AlertGroupContactService alertGroupContactService;
	
	private DateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	
	private final static String SMS_TMEP = "九指运维短信报警\r\n"
			+ "IP: {0}\r\n"
			+ "应用名: {1}\r\n"
			+ "告警时间: {2}\r\n"
			+ "告警内容: {3}\r\n"
			+ "告警等级: {4}\r\n"
			+ "【牙牙关注】";
	
	public static Map<Integer, Integer> sendCountMap = new HashMap<Integer, Integer>();
	
	@Scheduled(cron = "30 */1 * * * ?")
    public void alert() {
		ThirdPlus plus = plusService.generateByPlusType(PlusType.SMS.getIndex());
		List<AppConfigEntity> list = appConfigService.findListByIsAlert();
		for (AppConfigEntity appConfigEntity : list) {
			boolean isUp = false;
			ApplicationConfig applicationConfig 
				= ApplicationConfigManager.getApplicationConfig(appConfigEntity.getId().toString());
			if(ObjectUtility.isNotEmpty(applicationConfig)) {
				isUp = applicationViewHelper.isApplicationUp(applicationConfig);
			}
			else {
				isUp = false;
			}
			
			if(!isUp) {
				String message = MessageFormat.format(SMS_TMEP, 
						applicationConfig.getHost(), 
						applicationConfig.getName(), 
						dateFormat.format(new Date()),
						"DOWN",
						"严重");
				logger.info(message);
				Integer count = sendCountMap.get(appConfigEntity.getId());
				if(null == count || count == 0) {
					List<AlertContactEntity> contactList = alertGroupContactService.findAlertContactListByGroupId(appConfigEntity.getAlertGroupId());
					for (AlertContactEntity alertContactEntity : contactList) {
						plus.doSend(alertContactEntity.getMobile(), message);
					}
					count = 1;
					sendCountMap.put(appConfigEntity.getId(), count);
				}
				else {
					count ++;
					sendCountMap.put(appConfigEntity.getId(), count);
				}
			}
		}
    }
	
}