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

/**
 *
 * date:  Sep 15, 2004
 * @author	Rakesh Kalra
 */
public class MetaApplicationConfig {

    private boolean displayHost;
    private boolean displayPort;
    private boolean displayURL;
    private boolean displayUsername;
    private boolean displayPassword;

    /* the ApplicationConfig class for this module */
    private String configClass;

    public MetaApplicationConfig(boolean host, boolean port, boolean url,
                                 boolean username, boolean password,
                                 String configClass){
        this.displayHost = host;
        this.displayPort = port;
        this.displayURL = url;
        this.displayUsername = username;
        this.displayPassword = password;
        this.configClass = configClass;
    }

    public boolean isDisplayHost() {
        return displayHost;
    }

    public boolean isDisplayPort() {
        return displayPort;
    }

    public boolean isDisplayURL() {
        return displayURL;
    }

    public boolean isDisplayUsername() {
        return displayUsername;
    }

    public boolean isDisplayPassword() {
        return displayPassword;
    }

    public String getApplicationConfigClassName(){
        return configClass;
    }
}
