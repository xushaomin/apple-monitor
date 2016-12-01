/**
 * Copyright 2004-2005 jManage.org
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
package com.appleframework.jmx.core.services.impl;

import com.appleframework.jmx.core.config.*;
import com.appleframework.jmx.core.data.ApplicationConfigData;
import com.appleframework.jmx.core.data.MBeanData;
import com.appleframework.jmx.core.services.ConfigurationService;
import com.appleframework.jmx.core.services.ServiceContext;
import com.appleframework.jmx.core.services.ServiceException;
import com.appleframework.jmx.core.services.ServiceUtils;
import com.appleframework.jmx.core.util.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * date: Jan 17, 2005
 * 
 * @author Rakesh Kalra, Shashank Bellary
 */

@Service("configurationService")
public class ConfigurationServiceImpl implements ConfigurationService {

	private static Logger logger = Loggers.getLogger(ConfigurationService.class);

	@Resource
	private ApplicationConfigManager applicationConfigManager;

	/**
	 * Add/Configure a new application without any dashboards.
	 *
	 * @param context
	 * @param data
	 * @return Application config with dashboard details.
	 */
	public ApplicationConfigData addApplication(ServiceContext context, ApplicationConfigData data) {
		/* do the operation */
		String appId = ApplicationConfig.getNextApplicationId();

		ApplicationConfig config = ApplicationConfigFactory.create(appId, data.getName(), data.getType(),
				data.getHost(), data.getPort(), data.getURL(), data.getUsername(), data.getPassword(),
				data.getParamValues());
		try {
			applicationConfigManager.addApplication(config);
		} catch (Exception e) {
			throw new ServiceException(ErrorCodes.APPLICATION_ID_ALREADY_EXISTS, appId);
		}

		data.setApplicationId(config.getApplicationId());

		return data;
	}

	// TODO: sort the list before returning
	public List<ApplicationConfigData> getAllApplications(ServiceContext context) {
		List<ApplicationConfig> appConfigs = ApplicationConfigManager.getApplications();
		return appConfigListToAppConfigDataList(appConfigs);
	}

	private List<ApplicationConfigData> appConfigListToAppConfigDataList(List<ApplicationConfig> appConfigs) {
		ArrayList<ApplicationConfigData> appDataObjs = new ArrayList<ApplicationConfigData>(appConfigs.size());
		for (Object appConfig1 : appConfigs) {
			ApplicationConfigData configData = new ApplicationConfigData();
			ApplicationConfig appConfig = (ApplicationConfig) appConfig1;
			CoreUtils.copyProperties(configData, appConfig);
			appDataObjs.add(configData);
			if (appConfig.isCluster()) {
				List<ApplicationConfigData> childApplications = appConfigListToAppConfigDataList(
						appConfig.getApplications());
				configData.setChildApplications(childApplications);
			}
		}
		return appDataObjs;
	}

	public List<MBeanData> getConfiguredMBeans(ServiceContext context) throws ServiceException {
		String applicationId = context.getApplicationConfig().getAppId();
		ApplicationConfig appConfig = ServiceUtils.getApplicationConfigById(applicationId);
		List<MBeanConfig> mbeanList = appConfig.getMBeans();
		ArrayList<MBeanData> mbeanDataList = new ArrayList<MBeanData>(mbeanList.size());
		for (Object mbeanConfig1 : mbeanList) {
			MBeanConfig mbeanConfig = (MBeanConfig) mbeanConfig1;
			mbeanDataList.add(new MBeanData(mbeanConfig.getObjectName(), mbeanConfig.getName()));
		}
		return mbeanDataList;
	}

	public GraphConfig addGraph(ServiceContext context, GraphConfig graphConfig) {
		ApplicationConfig appConfig = context.getApplicationConfig();
		// todo: this method needs attention. GraphConfig is added before
		// calling this method
		// appConfig.addGraph(graphConfig);
		try {
			applicationConfigManager.updateApplication(appConfig);
		} catch (Exception e) {
			logger.error(e);
			// unexpected exception
			throw new RuntimeException(e);
		}
		return graphConfig;
	}
}
