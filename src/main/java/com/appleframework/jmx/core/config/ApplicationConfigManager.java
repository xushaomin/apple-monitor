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
package com.appleframework.jmx.core.config;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.appleframework.jmx.core.config.event.ApplicationChangedEvent;
import com.appleframework.jmx.core.config.event.ApplicationRemovedEvent;
import com.appleframework.jmx.core.config.event.NewApplicationEvent;
import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.jmx.event.EventSystem;

/**
 * ApplicationConfigManager holds configuration of managed applications. It
 * provides add/update/remove operations for ApplicationConfig objects.
 * 
 * date: Jun 13, 2004
 * 
 * @author Rakesh Kalra, Shashank Bellary
 */
@Service("applicationConfigManager")
@Lazy(false)
public class ApplicationConfigManager {

	private static final Logger logger = Loggers.getLogger(ApplicationConfigManager.class);

	private static List<ApplicationConfig> applicationConfigs 
		= Collections.synchronizedList(new LinkedList<ApplicationConfig>());
	
	@Resource
	private ConfigReader configReader;
	
	@Resource
	private ConfigWriter configWriter;

	@PostConstruct
	public void init() {
		Config config = configReader.read();
		/* read the configuration */
		List<ApplicationConfig> clusterList = config.getApplications();
		for (ApplicationConfig applicationConfig : clusterList) {
			if(applicationConfig instanceof ApplicationClusterConfig) {
				ApplicationClusterConfig appClusterConfig = (ApplicationClusterConfig)applicationConfig;
				List<ApplicationConfig> appList = appClusterConfig.getApplications();
				for (ApplicationConfig applicationConfig2 : appList) {
					applicationConfigs.add(applicationConfig2);
				}
			}
			else {
				applicationConfigs.add(applicationConfig);
			}
		}
	}

	public static ApplicationConfig getApplicationConfig(String applicationId) {
		for (ApplicationConfig appConfig : applicationConfigs) {
			if (appConfig.getApplicationId().equals(applicationId)) {
				return appConfig;
			}
			if (appConfig.isCluster()) {
				for (Iterator<?> it2 = appConfig.getApplications().iterator(); it2.hasNext();) {
					appConfig = (ApplicationConfig) it2.next();
					if (appConfig.getApplicationId().equals(applicationId)) {
						return appConfig;
					}
				}
			}
		}
		return null;
	}
	
	public static List<ApplicationConfig> getApplicationsByClusterId(String clusterId) {
		List<ApplicationConfig> list = new ArrayList<ApplicationConfig>();
		for (ApplicationConfig applicationConfig : applicationConfigs) {
			ApplicationConfig clusterConfig = applicationConfig.getClusterConfig();;
			if (null != clusterConfig && clusterConfig.getAppId().equals(clusterId)) {
				list.add(applicationConfig);
			}
		}
		return list;
	}
	
	public static ApplicationConfig getApplicationConfig(String clusterId, String applicationId) {
		for (ApplicationConfig applicationConfig : applicationConfigs) {
			ApplicationClusterConfig clusterConfig = (ApplicationClusterConfig)applicationConfig;
			if (clusterConfig.getApplicationId().equals(clusterId)) {
				if (clusterConfig.isCluster()) {
					for (ApplicationConfig applicationConfig2 : clusterConfig.getApplications()) {
						if (applicationConfig2.getApplicationId().equals(applicationId)) {
							return applicationConfig2;
						}
					}
				}
				else {
					return clusterConfig;
				}
			}
		}
		return null;
	}
	
	/**
	 * Retrieve all configured applications.
	 * 
	 * @return
	 */
	public static List<ApplicationConfig> getApplications() {
		return applicationConfigs;
	}
	
	
	public void addOrUpdateApplication(ApplicationConfig config) {
		if(config instanceof ApplicationClusterConfig) {
			ApplicationClusterConfig clusterConfig = (ApplicationClusterConfig)config;
			if(clusterConfig.isCluster()) {
				List<ApplicationConfig> list = clusterConfig.getApplications();
				for (ApplicationConfig applicationConfig : list) {
					addOrUpdateApplication2(applicationConfig);
				}
			}
			else {
				addOrUpdateApplication2(clusterConfig);
			}
		}
		else {
			addOrUpdateApplication2(config);
		}
	}
	
	public void addOrUpdateApplication2(ApplicationConfig config) {
		ApplicationConfig existConfig = getApplicationConfig(config.getAppId());
		if(null == existConfig) {
			addApplication(config);
		}
		else {
			existConfig.setApplicationId(config.getAppId());
			existConfig.setName(config.getName());
			existConfig.setHost(config.getHost());
			existConfig.setPort(config.getPort());
			existConfig.setType(config.getType());
			existConfig.setUrl(config.getUrl());
			updateApplication(existConfig);
		}
	}
	
	public void addApplication(ApplicationConfig config) {
		saveConfig(config);
		applicationConfigs.add(config);
		/*
		 * fire the NewApplicationEvent if this is not a cluster which has been added
		 */
		if (!config.isCluster()) {
			EventSystem.getInstance().fireEvent(new NewApplicationEvent(config));
		}
	}

	public void updateApplication(ApplicationConfig config) {
		assert config != null : "application config is null";
		saveConfig(config);
		
		int index = applicationConfigs.indexOf(config);
		if (index != -1) {
			applicationConfigs.remove(index);
			applicationConfigs.add(index, config);
		} else {
			/* its part of a cluster */
			assert config.isCluster() == false;
			ApplicationConfig clusterConfig = config.getClusterConfig();
			assert clusterConfig != null;
			index = clusterConfig.getApplications().indexOf(config);
			assert index != -1 : "application not found in cluster";
			clusterConfig.getApplications().remove(index);
			clusterConfig.getApplications().add(index, config);
		}
		if (!config.isCluster()) {
			EventSystem.getInstance().fireEvent(new ApplicationChangedEvent(config));
		}
	}

	public ApplicationConfig deleteApplication(String applicationId) {
		assert applicationId != null : "applicationId is null";
		ApplicationConfig config = getApplicationConfig(applicationId);
		if(null == config)
			return null;
		assert config != null : "there is no application with id=" + applicationId;
		if (config.isCluster())
			deleteApplication(config, true);
		else
			deleteApplication(config);
		return (config);
	}

	public void deleteApplication(ApplicationConfig config) {
		deleteApplication(config, false);
	}

	public void deleteApplication(ApplicationConfig config, boolean clusterSetup) {
		assert config != null : "application config is null";
		boolean removed = applicationConfigs.remove(config);
		if (!removed) {
			/* this app is in a cluster. remove from cluster */
			for (Iterator<?> it = applicationConfigs.iterator(); it.hasNext();) {
				ApplicationConfig appConfig = (ApplicationConfig) it.next();
				if (appConfig.isCluster()) {
					ApplicationClusterConfig clusterConfig = (ApplicationClusterConfig) appConfig;
					if (clusterConfig.removeApplication(config)) {
						removed = true;
						break;
					}
				}
			}
		}
		if (removed && !clusterSetup) {
			EventSystem.getInstance().fireEvent(new ApplicationRemovedEvent(config));
		} else if (clusterSetup) {
			logger.info( config.getName() + " added to cluster");
		} else {
			logger.warn("ApplicationConfig not found for removal:" + config.getName());
		}
		deleteConfig(config);
	}

	private void saveConfig(ApplicationConfig config) {
		configWriter.write(config);
	}
	
	private void deleteConfig(ApplicationConfig config) {
		configWriter.delete(config);
	}

	public static List<ApplicationConfig> getAllApplications() {
		Iterator<?> appItr = applicationConfigs.iterator();
		List<ApplicationConfig> applications = new LinkedList<ApplicationConfig>();
		while (appItr.hasNext()) {
			ApplicationConfig appConfig = (ApplicationConfig) appItr.next();
			applications.add(appConfig);
			if (appConfig.isCluster()) {
				applications.addAll(appConfig.getApplications());
			}
		}
		return applications;
	}

	public static List<AlertConfig> getAllAlerts() {
		Iterator<?> appItr = applicationConfigs.iterator();
		List<AlertConfig> alerts = new LinkedList<AlertConfig>();
		while (appItr.hasNext()) {
			ApplicationConfig appConfig = (ApplicationConfig) appItr.next();
			alerts.addAll(appConfig.getAlerts());
			if (appConfig.isCluster()) {
				for (ApplicationConfig childAppConfig : appConfig.getApplications()) {
					alerts.addAll(childAppConfig.getAlerts());
				}
			}
		}
		return alerts;
	}

	/*private static void validateAppName(String appName, String applicationId) 
		throws DuplicateApplicationNameException {
		for (ApplicationConfig appConfig : applicationConfigs) {
			if (!appConfig.getApplicationId().equals(applicationId)
					&& (appConfig.getName().toUpperCase()).equals(appName.toUpperCase())) {
				throw new DuplicateApplicationNameException(appName);
			}
		}
	}
	
	private static void validateAppId(String applicationId) 
		throws DuplicateApplicationNameException {
		for (ApplicationConfig appConfig : applicationConfigs) {
			if (!appConfig.getApplicationId().equals(applicationId)) {
				throw new DuplicateApplicationNameException(applicationId);
			}
		}
	}*/

}
