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
  * Date: May 25, 2005 2:34:02 PM
 * @author Bhavana
 * @author Rakesh Kalra
 */
public class AlertConfig {

    private String alertId;
    private String alertName;
    private String[] alertDelivery;
    private String emailAddress;
    private AlertSourceConfig alertSourceConfig;

    public static String getNextAlertId(){
        return String.valueOf(System.currentTimeMillis());
    }
    
    public AlertConfig(){

    }
    
    public AlertConfig(String alertId, String alertName, String[] alertDelivery, String emailAddress){
        this.alertId = alertId;
        this.alertName = alertName;
        this.alertDelivery = alertDelivery;
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public String[] getAlertDelivery() {
        return alertDelivery;
    }

    public void setAlertDelivery(String[] alertDelivery) {
        if(alertDelivery!=null){
            this.alertDelivery = alertDelivery;
        }
    }

    public AlertSourceConfig getAlertSourceConfig() {
        return alertSourceConfig;
    }

    public void setAlertSourceConfig(AlertSourceConfig alertSourceConfig) {
        this.alertSourceConfig = alertSourceConfig;
    }
}
