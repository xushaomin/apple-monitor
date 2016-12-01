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
package com.appleframework.jmx.core.services;

import com.appleframework.jmx.core.management.ServerConnection;
import com.appleframework.jmx.core.management.ServerConnector;
import com.appleframework.jmx.core.management.ConnectionFailedException;
import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.config.ApplicationConfigManager;
import com.appleframework.jmx.core.config.MBeanConfig;
import com.appleframework.jmx.core.util.ErrorCodes;
import com.appleframework.jmx.core.util.Loggers;

import org.apache.log4j.Logger;
import java.io.IOException;

/**
 *
 * date:  Feb 21, 2005
 * @author	Rakesh Kalra
 */
public class ServiceUtils {

    private static final Logger logger = Loggers.getLogger(ServiceUtils.class);

    // TODO: It will be nice to have a concept of ClusterServerConnection
    //      which will implement all cluster level operations - rk
	public static ServerConnection getServerConnectionEvenIfCluster(ApplicationConfig appConfig) {
		if (!appConfig.isCluster()) {
			return ServerConnector.getServerConnection(appConfig);
		} else {
			if (appConfig.getApplications().size() == 0) {
				throw new ServiceException(ErrorCodes.CLUSTER_NO_APPLICATIONS);
			}
			for (ApplicationConfig childAppConfig : appConfig.getApplications()) {
				try {
					return ServerConnector.getServerConnection(childAppConfig);
				} catch (ConnectionFailedException e) {
					logger.info("Couldn't connect to childApp= " + childAppConfig);
				}
			}
		}
		throw new ConnectionFailedException(null);
	}

	public static ApplicationConfig getApplicationConfigById(String appId) throws ServiceException {
		ApplicationConfig appConfig = ApplicationConfigManager.getApplicationConfig(appId);
		if (appConfig == null) {
			throw new ServiceException(ErrorCodes.INVALID_APPLICATION_ID, appId);
		}
		return appConfig;
	}

    public static String resolveMBeanName(ApplicationConfig appConfig, String mbeanName){
        /* check if the mbeanName is the configured mbean name */
        MBeanConfig mbeanConfig = appConfig.findMBean(mbeanName);
        if(mbeanConfig != null){
            mbeanName = mbeanConfig.getObjectName();
        }
        return mbeanName;
    }

	public static void close(ServerConnection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (IOException e) {
				logger.info("Error closing connection: " + e.getMessage());
			}
		}
	}
}
