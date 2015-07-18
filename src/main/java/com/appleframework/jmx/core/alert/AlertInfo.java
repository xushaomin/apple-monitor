/**
* Copyright (c) 2004-2005 jManage.org
*
* This is a free software; you can redistribute it and/or
* modify it under the terms of the license at
* http://www.jmanage.org.
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.appleframework.jmx.core.alert;

import com.appleframework.jmx.core.management.ObjectNotification;
import com.appleframework.jmx.core.config.AlertConfig;
import com.appleframework.jmx.core.config.ApplicationConfig;

import java.text.SimpleDateFormat;

/**
 *
 * Date:  Jul 31, 2005
 * @author	Rakesh Kalra
 */
public class AlertInfo {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

    // unique id for the alert
    private String alertId = null;
    private String type = null;
    private long sequenceNumber = 0;
    private String message = null;
    private long timeStamp;
    private Object userData = null;
    private String objectName = null;

    private String alertConfigId;
    private String alertName;
    private String emailAddress;

    private String appId;
    private String appName;

    // TODO: create proper ctor -rk
    public AlertInfo(){
        setAlertId("appDown" + System.currentTimeMillis());
        setMessage("Application is down");
        setTimeStamp(System.currentTimeMillis());
    }

    public AlertInfo(ObjectNotification notification){
        // todo: figure out a better way to generate unique alert ids
        setAlertId(notification.getType() + System.currentTimeMillis());
        setType(notification.getType());
        setSequenceNumber(notification.getSequenceNumber());
        setMessage(notification.getMessage());
        setTimeStamp(notification.getTimeStamp());
        setUserData(notification.getUserData());
    }

    public void setAlertConfig(AlertConfig alertConfig) {
        this.alertConfigId = alertConfig.getAlertId();
        this.alertName = alertConfig.getAlertName();
        this.emailAddress = alertConfig.getEmailAddress();
        setObjectName(alertConfig.getAlertSourceConfig().getObjectName());
        ApplicationConfig appConfig = alertConfig.getAlertSourceConfig().getApplicationConfig();
        this.setApplicationId(appConfig.getApplicationId());
        this.setApplicationName(appConfig.getName());
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getFormattedTimeStamp(){
        return formatter.format(new java.util.Date(timeStamp));
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Object getUserData() {
        return userData;
    }

    public void setUserData(Object userData) {
        this.userData = userData;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getAlertConfigId() {
        return alertConfigId;
    }

    public void setAlertConfigId(String alertConfigId) {
        this.alertConfigId = alertConfigId;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getApplicationId() {
        return appId;
    }

    public void setApplicationId(String appId) {
        this.appId = appId;
    }

    public String getApplicationName() {
        return appName;
    }

    public void setApplicationName(String appName) {
        this.appName = appName;
    }
}
