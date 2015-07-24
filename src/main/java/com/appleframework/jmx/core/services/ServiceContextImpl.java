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

import com.appleframework.jmx.core.services.ServiceContext;
import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.management.ObjectName;
import com.appleframework.jmx.core.management.ServerConnection;
import com.appleframework.jmx.core.management.ServerConnector;

/**
 *
 * date:  Jan 19, 2005
 * @author	Rakesh Kalra
 * @author  Shashank Bellary
 */
public class ServiceContextImpl implements ServiceContext {

	private static final long serialVersionUID = 5117302022597868291L;

    private String appId;
    private String mbeanName;

    private transient ServerConnection serverConnection;

    public ApplicationConfig getApplicationConfig() {
    	if (appId == null)
    		return null;
        return ServiceUtils.getApplicationConfigById(appId);
    }

    public ObjectName getObjectName() {
    	if (mbeanName == null)
    		return null;
        String mbeanName = ServiceUtils.resolveMBeanName(getApplicationConfig(), this.mbeanName);
        return new ObjectName(mbeanName);
    }

    public ServerConnection getServerConnection() {
        ApplicationConfig appConfig = getApplicationConfig();
        assert appConfig != null;
        assert !appConfig.isCluster():"not supported for cluster";
        if (serverConnection == null) {
            serverConnection = ServerConnector.getServerConnection(appConfig);
        }
        return serverConnection;
    }

    /**
     * @param appName configured application id
     */
    public void setApplicationId(String appId) {
        this.appId = appId;
    }

    /**
     * @param mbeanName configured mbean name or object name
     */
    public void setMBeanName(String mbeanName){
        this.mbeanName =  mbeanName;
    }

    public void releaseResources() {
        ServiceUtils.close(serverConnection);
        serverConnection = null;
    }

	public ServiceContextImpl(String appId, String mbeanName) {
		super();
		this.appId = appId;
		this.mbeanName = mbeanName;
	}
    
	public ServiceContextImpl() {
		super();
	}
}
