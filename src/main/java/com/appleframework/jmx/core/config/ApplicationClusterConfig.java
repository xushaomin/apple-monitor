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

import java.util.List;
import java.util.LinkedList;

/**
 *
 * date:  Sep 15, 2004
 * @author	Rakesh Kalra
 */
public class ApplicationClusterConfig extends ApplicationConfig {

    private List<ApplicationConfig> applications = new LinkedList<ApplicationConfig>();
    
    public ApplicationClusterConfig(String id, String name){
        setApplicationId(id);
        setName(name);
    }

    /**
     * @return true
     */
    public boolean isCluster(){
        return super.isCluster();
    }

    public void setCluster(boolean isCluster) {
		super.setCluster(isCluster);
	}

	/**
     * This method returns a list of applications in this cluster.
     *
     * @return  list of applications in this cluster
     */
    public List<ApplicationConfig> getApplications(){
        return applications;
    }

    public void setApplications(List<ApplicationConfig> appConfigList) {
        this.applications = appConfigList;
    }

    /**
     * Adds a application to this cluster.
     *
     * @param config    ApplicationConfig to be added to this cluster
     */
    public void addApplication(ApplicationConfig config){
        applications.add(config);
    }

    public void addAllApplications(List<ApplicationConfig> appConfigList){
        applications.addAll(appConfigList);
    }

    /**
     * Removes the application from the cluster.
     *
     * @param config
     * @return true: if the application was removed from the cluster
     */
    public boolean removeApplication(ApplicationConfig config){
        return applications.remove(config);
    }

}
