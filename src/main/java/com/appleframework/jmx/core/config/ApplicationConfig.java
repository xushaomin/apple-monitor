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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ApplicationConfig holds configuration for a single application. 
 * It has attributes like host, port, url ,etc.
 * 
 * date:  Jun 11, 2004
 * @author	Rakesh Kalra, Shashank Bellary
 */
public abstract class ApplicationConfig {

    // jsr160 constants
    public static final String JNDI_FACTORY = "java.naming.factory.initial";
    public static final String JNDI_URL     = "java.naming.provider.url";
    
    private boolean isCluster = false;

    public static String getNextApplicationId(){
        //todo: Need something better
        return String.valueOf(System.currentTimeMillis());
    }

    private String appId;
    private String type;
    private String name;
    private String host;
    private Integer port;
    private String url;
    private String username;
    private String password;
    
    protected Map<String, String> paramValues;
    
    private List<MBeanConfig> mbeanList = new LinkedList<MBeanConfig>();
    private List<GraphConfig> graphList = new LinkedList<GraphConfig>();
    private List<AlertConfig> alertsList = new LinkedList<AlertConfig>();
    // list of dashboard ids
    private List<String> dashboards;
    
    // clusterConfig: if this is part of a cluster
    private ApplicationConfig clusterConfig;

    public String getApplicationId(){
        return appId;
    }

    public void setApplicationId(String appId){
        assert this.appId == null;
        this.appId = appId;
    }

    /**
     * @return  type of application
     */
    public String getType(){
        return type;
    }

    public void setType(String type){
        assert this.type == null;
        this.type = type;
    }

    /**
     * @return  the name of the application
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @JsonIgnore
    public String getURL() {
        if(url == null){
            return host + ":" + port;
        }
        return url;
    }

    @JsonIgnore
    public void setURL(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    public Map<String, String> getParamValues(){
        if(paramValues == null)
            paramValues = new HashMap<String, String>();
        return paramValues;
    }

    public void setParamValues(Map<String, String> paramValues){
        this.paramValues = paramValues;
    }

    /**
     * @return true: if its a application cluster; false: otherwise
     */
    public boolean isCluster(){
        return this.isCluster;
    }

    public void setCluster(boolean isCluster) {
		this.isCluster = isCluster;
	}

	/**
     * If this is a application cluster, this method returns a list of
     * applications in this cluster.
     *
     * @return  list of applications in this cluster
     */
    public List<ApplicationConfig> getApplications(){
        return null;
    }

    /**
     * @return list of MBeanConfig objects
     */
    public List<MBeanConfig> getMBeans(){
        return mbeanList;
    }

    /**
     *
     * @param mbeanList list of MBeanConfig objects
     */
    public void setMBeans(List<MBeanConfig> mbeanList){
        if(mbeanList != null){
            this.mbeanList = mbeanList;
        }else{
            this.mbeanList = new LinkedList<MBeanConfig>();
        }
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appId == null) ? 0 : appId.hashCode());
		return result;
	}

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationConfig other = (ApplicationConfig) obj;
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		return true;
	}

    public String toString(){
        return getApplicationId() + ";" + getName() + ";" + getURL();
    }

    public MBeanConfig removeMBean(String objectName){
    	for (MBeanConfig mbeanConfig : mbeanList) {
    		if(mbeanConfig.getObjectName().equals(objectName)){
                mbeanList.remove(mbeanConfig);
                return mbeanConfig;
            }
		}
        return null;
    }

    public void addMBean(MBeanConfig mbeanConfig){
        assert !containsMBean(mbeanConfig.getObjectName());
        mbeanList.add(mbeanConfig);
    }

    public ApplicationConfig getClusterConfig() {
        return clusterConfig;
    }

    public void setClusterConfig(ApplicationConfig clusterConfig) {
        this.clusterConfig = clusterConfig;
    }

    public MBeanConfig findMBean(String mbeanName) {
    	for (MBeanConfig mbeanConfig : mbeanList) {
            if(mbeanConfig.getName().equals(mbeanName)){
                return mbeanConfig;
            }
        }
        return null;
    }

    public MBeanConfig findMBeanByObjectName(String objectName){
    	for (MBeanConfig mbeanConfig : mbeanList) {
            if(mbeanConfig.getObjectName().equals(objectName)){
                return mbeanConfig;
            }
        }
        return null;
    }

    public boolean containsMBean(String objectName) {
        return findMBeanByObjectName(objectName) != null;
    }

    public void addGraph(GraphConfig graphConfig){
        assert graphConfig!=null:"graphConfig is null";
        graphList.add(graphConfig);
    }
    
    public void setGraphs(List<GraphConfig> graphList) {
        if(graphList != null){
            this.graphList = graphList;
        }else{
            this.graphList = new LinkedList<GraphConfig>();
        }
    }

    public List<GraphConfig> getGraphs(){
        return graphList;
    }

    public GraphConfig findGraph(String graphId) {
    	for (GraphConfig graphConfig : graphList) {
    		if(graphConfig.getId().equals(graphId)){
                return graphConfig;
            }
		}
        return null;
    }
    public GraphConfig removeGraph(String graphId){
        GraphConfig graphConfig = findGraph(graphId);
        if(graphConfig!=null){
            graphList.remove(graphConfig);
            return graphConfig;
        }
        return null;
    }



   public void addAlert(AlertConfig alertConfig){
       assert alertConfig!=null:"alert config is null";
       alertsList.add(alertConfig);
   }

    /**
     * @return list of AlertConfig objects
     */
   public List<AlertConfig> getAlerts(){
       return alertsList;
   }
   /**
    *
    * @param alertsList list of MBeanConfig objects
    */
   public void setAlerts(List<AlertConfig> alertsList){
       if(alertsList != null){
           this.alertsList = alertsList;
       }else{
           this.alertsList = new LinkedList<AlertConfig>();
       }
   }

	public AlertConfig findAlertById(String alertId) {
		AlertConfig alert = null;
		for (AlertConfig alertConfig : alertsList) {
			if (alertConfig.getAlertId().equals(alertId)) {
				alert = alertConfig;
				break;
			}
		}
		return alert;
	}

    public AlertConfig removeAlert(String alertId){
        AlertConfig alertConfig = findAlertById(alertId);
        if(alertConfig!=null){
            alertsList.remove(alertConfig);
            return alertConfig;
        }
        return null;
    }

    /**
     * 
     * @return a list of dashboard IDs
     */
    public List<String> getDashboards() {
        return dashboards;
    }

    public void setDashboards(List<String> dashboards) {
        this.dashboards = dashboards;
    }

    public ClassLoader getApplicationClassLoader(){
        ApplicationType appType = ApplicationTypes.getApplicationType(getType());
        assert appType != null;
        return appType.getClassLoader();
    }

    public ApplicationType getApplicationType(){
        return ApplicationTypes.getApplicationType(getType());
    }
    
    
}
