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
 * Date: Jun 19, 2004
 * @author  Shashank, Rakesh Kalra
 */
public interface ConfigConstants {

    public String APPLICATION_CONFIG = "application-config";
    public String APPLICATIONS = "applications";
    public String APPLICATION_CLUSTER = "application-cluster";
    public String APPLICATION = "application";
    public String PARAMETER = "param";
    public String PARAMETER_NAME = "param-name";
    public String PARAMETER_VALUE = "param-value";
    public String APPLICATION_ID = "id";
    public String APPLICATION_NAME = "name";
    public String APPLICATION_TYPE = "type";
    public String HOST = "host";
    public String PORT = "port";
    public String URL = "url";
    public String USERNAME = "username";
    public String PASSWORD = "password";

    public String MBEANS = "mbeans";
    public String MBEAN = "mbean";
    public String MBEAN_NAME = "name";
    public String MBEAN_OBJECT_NAME = "object-name";

    public String ALERTS="alerts";
    public String ALERT="alert";
    public String ALERT_ID="id";
    public String ALERT_NAME="name";
    public String ALERT_DELIVERY = "delivery";
    public String ALERT_DELIVERY_TYPE="type";
    public String ALERT_EMAIL_ADDRESS="emailAddress";
    public String ALERT_SMS_MOBILE="mobile";
    public String ALERT_SOURCE = "source";
    public String ALERT_SOURCE_TYPE = "type";
    public String ALERT_SOURCE_MBEAN = "mbean";
    public String ALERT_SOURCE_NOTIFICATION_TYPE = "notificationType";
    public String ALERT_ATTRIBUTE_NAME = "attribute";
    public String ALERT_ATTRIBUTE_LOW_THRESHOLD = "lowThreshold";
    public String ALERT_ATTRIBUTE_HIGH_THRESHOLD = "highThreshold";
    public String ALERT_STRING_ATTRIBUTE_VALUE = "stringAttributeValue";
    public String ALERT_ATTRIBUTE_DATA_TYPE = "attributeDataType";

    public String GRAPHS = "graphs";
    public String GRAPH = "graph";
    public String GRAPH_ID = "id";
    public String GRAPH_NAME = "name";
    public String GRAPH_POLLING_INTERVAL = "pollingInterval";
    public String GRAPH_Y_AXIS_LABEL = "yAxisLabel";
    public String GRAPH_SCALE_FACTOR = "scaleFactor";
    public String GRAPH_SCALE_UP = "scaleUp";
    public String GRAPH_ATTRIBUTE = "attribute";
    public String GRAPH_ATTRIBUTE_MBEAN = "mbean";
    public String GRAPH_ATTRIBUTE_NAME = "attribute";
    public String GRAPH_ATTRIBUTE_DISPLAY_NAME = "displayName";

    public String DASHBOARDS = "dashboards";
    public String DASHBOARD = "dashboard";
    public String DASHBOARD_ID = "id";
    public String DASHBOARD_NAME = "name";
}
