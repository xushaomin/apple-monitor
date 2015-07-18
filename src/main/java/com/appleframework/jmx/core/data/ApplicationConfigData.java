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
package com.appleframework.jmx.core.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * date:  Jan 9, 2005
 * @author	Rakesh Kalra
 */
public class ApplicationConfigData implements Serializable {

	private static final long serialVersionUID = -7094389293327083709L;

	private String appId;
    private String name;
    private String host;
    private Integer port;
    private String url;
    private String username;
    private String password;
    private String type;
    private boolean isCluster;
    private List childApplications;
    protected Map<String, String> paramValues;

    public String getApplicationId() {
        return appId;
    }

    public void setApplicationId(String appId) {
        this.appId = appId;
    }

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

    public String getURL() {
        return url;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getParamValues(){
        return paramValues;
    }

    public void setParamValues(Map<String, String> paramValues){
        this.paramValues = paramValues;
    }

    public void setCluster(boolean isCluster){
        this.isCluster = isCluster;
    }

    /**
     * @return true: if its a application cluster; false: otherwise
     */
    public boolean isCluster(){
        return isCluster;
    }

    public List getChildApplications() {
        return childApplications;
    }

    public void setChildApplications(List childApplications) {
        assert isCluster;
        this.childApplications = childApplications;
    }
}
