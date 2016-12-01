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

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.database.constant.PlusType;
import com.appleframework.jmx.database.entity.AlertContactEntity;
import com.appleframework.jmx.database.entity.AppConfigEntity;
import com.appleframework.jmx.database.service.AlertGroupContactService;
import com.appleframework.monitor.plus.ThirdPlus;
import com.appleframework.monitor.service.PlusService;

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
	
	@Resource
	private PlusService plusService;
	
	private ThirdPlus plus;

	public void deliver(ApplicationConfig applicationConfig, AppConfigEntity config) {

		String message = MessageFormat.format(SMS_TMEP, applicationConfig.getHost(), applicationConfig.getName(),
				dateFormat.format(new Date()), "DOWN", "严重");

			List<AlertContactEntity> contactList = alertGroupContactService
					.findAlertContactListByGroupId(config.getAlertGroupId());
			for (AlertContactEntity alertContactEntity : contactList) {
				getPlus().doSend(alertContactEntity.getEmail(), message);
			}
	}

	public ThirdPlus getPlus() {
		if(null == plus) {
			plus = plusService.generateByPlusType(PlusType.MAIL.getIndex());
		}
		return plus;
	}

}