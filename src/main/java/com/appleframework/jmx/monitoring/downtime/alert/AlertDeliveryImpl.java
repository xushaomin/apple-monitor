/**
 * Copyright 2004-2006 jManage.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appleframework.jmx.monitoring.downtime.alert;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.database.constant.PlusType;
import com.appleframework.jmx.database.entity.AlertContactEntity;
import com.appleframework.jmx.database.entity.AlertGroupEntity;
import com.appleframework.jmx.database.entity.AlertTemplEntity;
import com.appleframework.jmx.database.entity.AppConfigEntity;
import com.appleframework.jmx.database.service.AlertGroupContactService;
import com.appleframework.jmx.database.service.AlertGroupService;
import com.appleframework.jmx.database.service.AlertTemplService;
import com.appleframework.monitor.plus.ThirdPlus;
import com.appleframework.monitor.service.PlusService;

import freemarker.template.Template;

/**
 * Records application downtime in the database.
 * 
 * TODO: This reads all applications from the DB. This should be changed to just
 * read the current applications - rk
 * 
 * 
 * @author Rakesh Kalra
 */
@Service("alertDelivery")
public class AlertDeliveryImpl implements AlertDelivery {
	
	private static final Logger logger = LoggerFactory.getLogger(AlertDeliveryImpl.class);

	@Resource
	private AlertGroupContactService alertGroupContactService;
	
	@Resource
	private AlertGroupService alertGroupService;
	
	@Resource
	private AlertTemplService alertTemplService;
	
	private DateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
		
	@Resource
	private PlusService plusService;
	
	public void deliver(ApplicationConfig applicationConfig, AppConfigEntity config) {
		String code = "downtimeAlert";
		
		
		AlertGroupEntity group = alertGroupService.get(config.getAlertGroupId());
		
		List<AlertContactEntity> contactList = alertGroupContactService
					.findAlertContactListByGroupId(config.getAlertGroupId());
		boolean sendFlag = false;
		Map<String, String> data = new HashMap<String, String>();
		data.put("ip", applicationConfig.getHost());
		data.put("applicationName", applicationConfig.getName());
		data.put("alertTime", dateFormat.format(new Date()));
		data.put("alertContent", "DOWN");
		data.put("alertLevel", "严重");
				
		if(group.getNeedMail()) {
			AlertTemplEntity templ = alertTemplService.getByTypeAndCode(PlusType.MAIL.getIndex(), code);
			String templId = templ.getAuthId();
			String message = this.buildContent(templ, data);
			ThirdPlus plus = plusService.generateByPlusId(Integer.parseInt(templId));
			for (AlertContactEntity alertContactEntity : contactList) {
				sendFlag = plus.doSend(alertContactEntity.getEmail(), message);
				if(!sendFlag) {
					System.out.println("发送失败");
				}
			}
		}
		
		if(group.getNeedSms()) {
			AlertTemplEntity templ = alertTemplService.getByTypeAndCode(PlusType.SMS.getIndex(), code);
			String templId = templ.getAuthId();
			String message = this.buildContent(templ, data);
			ThirdPlus plus = plusService.generateByPlusId(Integer.parseInt(templId));
			for (AlertContactEntity alertContactEntity : contactList) {
				sendFlag = plus.doSend(alertContactEntity.getMobile(), message);
				if(!sendFlag) {
					System.out.println("发送失败");
				}
			}
		}
		
		if(group.getNeedWeixin()) {
			AlertTemplEntity templ = alertTemplService.getByTypeAndCode(PlusType.WEIXIN.getIndex(), code);
			String templId = templ.getAuthId();
			
			ThirdPlus plus = plusService.generateByPlusId(Integer.parseInt(templId));
			for (AlertContactEntity alertContactEntity : contactList) {
				if(templ.getIsContain()) {
					data.put("receiver", alertContactEntity.getWeixin());
					data.put("tmpThirdId", templ.getTmpThirdId());
				}
				String message = this.buildContent(templ, data);
				sendFlag = plus.doSend(alertContactEntity.getWeixin(), message);
				if(!sendFlag) {
					System.out.println("发送失败");
				}
			}
		}
	}
	
	public String buildContent(AlertTemplEntity alertTempl, Map<String, String> data) {
		try {
			String templ = alertTempl.getTemplate().trim();
            Template template = new Template(null, new StringReader(templ), null); 
			StringWriter out = new StringWriter();
			template.process(data, out);
			return out.getBuffer().toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
}