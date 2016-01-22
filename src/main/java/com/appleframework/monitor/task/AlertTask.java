package com.appleframework.monitor.task;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;  
import org.springframework.stereotype.Component;

import com.appleframework.core.utils.ObjectUtility;
import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.config.ApplicationConfigManager;
import com.appleframework.jmx.database.constant.PlusType;
import com.appleframework.jmx.database.entity.AppConfigEntity;
import com.appleframework.jmx.database.service.AppConfigService;
import com.appleframework.jmx.webui.view.ApplicationViewHelper;
import com.appleframework.monitor.plus.ThirdPlus;
import com.appleframework.monitor.service.PlusService;

@Component("alertTask")
public class AlertTask {
	
	@Resource
	private AppConfigService appConfigService;
	
	@Resource
	private ApplicationViewHelper applicationViewHelper;
	
	@Resource
	private PlusService plusService;
	
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
			
			System.out.println(applicationConfig.getName() + ":" + isUp);
			boolean result = plus.doSend("13760189357", "【牙牙关注】" + applicationConfig.getName() + "发生错误");
			System.out.println(result);
		}
    }
	
}