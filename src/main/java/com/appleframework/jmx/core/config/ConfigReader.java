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

import org.jdom.input.SAXBuilder;
import org.jdom.JDOMException;
import org.jdom.Document;
import org.jdom.Element;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.core.crypto.Crypto;
import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.jmx.core.util.CoreUtils;
import com.appleframework.jmx.database.entity.AppClusterEntity;
import com.appleframework.jmx.database.entity.AppConfigEntity;
import com.appleframework.jmx.database.service.AppClusterService;
import com.appleframework.jmx.database.service.AppConfigService;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

/**
 *
 * Date: Jun 19, 2004
 * @author  Shashank Bellary
 */
@Service("configReader")
public class ConfigReader implements ConfigConstants {

    private static final Logger logger = Loggers.getLogger(ConfigReader.class);
    
    @Resource
    private AppConfigService appConfigService;
    
    @Resource
    private AppClusterService appClusterService;
    
    /**
     * To retrieve the details of all configured applications.
     */
    public Config read(){
    	List<ApplicationConfig> appConfigList = new ArrayList<ApplicationConfig>();
    	List<AppClusterEntity> appClusterList = appClusterService.findListByStart();
    	for (AppClusterEntity appCluster : appClusterList) {
    		Integer applicationId = appCluster.getId();
    		String applicationName = appCluster.getClusterName();
    		
    		ApplicationClusterConfig applicationClusterConfig 
    			= new ApplicationClusterConfig(String.valueOf(applicationId), applicationName);
    		//applicationClusterConfig.setCluster(Boolean.valueOf(appCluster.getIsCluster() + ""));
    		
    		List<AppConfigEntity> appConfigEntityList = appConfigService.findListByClusterIdAndStart(applicationId);

    		
			for (AppConfigEntity appConfigEntity : appConfigEntityList) {
				String applicationConfigString = appConfigEntity.getAppConfig();
				try {
					StringReader reader = new StringReader(applicationConfigString);
					Document config = new SAXBuilder().build(reader);

					Element applicationCofigElement = config.getRootElement();
					ApplicationConfig appConfig = this.getApplicationConfig(applicationCofigElement);
					appConfig.setClusterConfig(applicationClusterConfig);
					appConfig.setCluster(false);
					appConfigList.add(appConfig);

				} catch (JDOMException e) {
					logger.error(e);
				} catch (IOException e) {
					logger.error(e);
				}
			}    		
		}
        return new Config(appConfigList);
    }

    private List<String> getDashboardConfigList(List<Element> dashboards){
        final List<String> dashboardConfigList = new LinkedList<String>();
        for (Object dashboardElement : dashboards) {
            Element dashboard = (Element) dashboardElement;
            dashboardConfigList.add(dashboard.getAttributeValue(DASHBOARD_ID));
        }
        return dashboardConfigList;
    }

    private List<ApplicationConfig> getApplicationConfigList(List<Element> applications,
    														 ApplicationConfig clusterConfig){
        final List<ApplicationConfig> applicationConfigList = new LinkedList<ApplicationConfig>();
        for (Object applicationElement : applications) {
            Element application = (Element) applicationElement;
            ApplicationConfig appConfig = null;
            if (APPLICATION.equalsIgnoreCase(application.getName())) {
                appConfig = getApplicationConfig(application);
                appConfig.setClusterConfig(clusterConfig);
            } else if (APPLICATION_CLUSTER.equals(application.getName())) {
                assert clusterConfig == null: "found cluster within a cluster";
                appConfig = getApplicationClusterConfig(application);
            } else {
                assert false:"Invalid element:" + application.getName();
            }
            applicationConfigList.add(appConfig);
        }
        return applicationConfigList;
    }

    @SuppressWarnings("unchecked")
	public ApplicationConfig getApplicationConfig(Element application){
        List<Element> params = application.getChildren(PARAMETER);
        Iterator<?> paramIterator = params.iterator();
        Map<String, String> paramValues = new HashMap<String, String>(1);
        while(paramIterator.hasNext()){
            Element param = (Element)paramIterator.next();
            paramValues.put(param.getChildTextTrim(PARAMETER_NAME), param.getChildTextTrim(PARAMETER_VALUE));
        }

        final String encryptedPassword = application.getAttributeValue(PASSWORD);
        String password = null;
        if(encryptedPassword != null){
            password = Crypto.decrypt(encryptedPassword);
        }
        Integer port = null;
        if(application.getAttributeValue(PORT) != null){
            port = new Integer(application.getAttributeValue(PORT));
        }
        
        String id = application.getAttributeValue(APPLICATION_ID);
        String name = application.getAttributeValue(APPLICATION_NAME);
        String type = application.getAttributeValue(APPLICATION_TYPE);
        String host = application.getAttributeValue(HOST);

        String url = application.getAttributeValue(URL);
        String username = application.getAttributeValue(USERNAME);
        
		ApplicationConfig config = ApplicationConfigFactory.create(id, name,
				type, host, port, url, username, password, paramValues);

        config.setMBeans(getMBeanConfigList(application));
        config.setAlerts(getAlertsList(application, config));
        config.setGraphs(getGraphConfigList(application, config));
        config.setDashboards(getDashbardConfigList(application));
        return config;
    }

    @SuppressWarnings("unchecked")
	private ApplicationConfig getApplicationClusterConfig(Element application){

        ApplicationClusterConfig config =
                new ApplicationClusterConfig(application.getAttributeValue(APPLICATION_ID),
                        application.getAttributeValue(APPLICATION_NAME));
        if(application.getChild(APPLICATIONS) != null){
            List<Element> applications = application.getChild(APPLICATIONS).getChildren();
            List<ApplicationConfig> appConfigList = getApplicationConfigList(applications, config);
            config.addAllApplications(appConfigList);
        }

        config.setMBeans(getMBeanConfigList(application));
        // todo: graphs are not yet supported at the cluster level
        // todo: to enable this, we will have to store applicationId at graph attribute level
        //config.setGraphs(getGraphConfigList(application, config));
        return config;
    }

    @SuppressWarnings("unchecked")
	private List<MBeanConfig> getMBeanConfigList(Element application){
        /* read mbeans */
        List<MBeanConfig> mbeanConfigList = new LinkedList<MBeanConfig>();
        if(application.getChild(MBEANS) != null){
            List<Element> mbeans = application.getChild(MBEANS).getChildren(MBEAN);
            for (Object mbeanElement : mbeans) {
                Element mbean = (Element) mbeanElement;
                MBeanConfig mbeanConfig =
                        new MBeanConfig(mbean.getAttributeValue(MBEAN_NAME), mbean.getChildTextTrim(MBEAN_OBJECT_NAME));
                mbeanConfigList.add(mbeanConfig);
            }
        }
        return mbeanConfigList;
    }

    @SuppressWarnings("unchecked")
	private List<String> getDashbardConfigList(Element application){
        List<String> dashboardConfigList;
        Element dashboards = application.getChild(DASHBOARDS);
        if(dashboards != null){
            dashboardConfigList = getDashboardConfigList(dashboards.getChildren());
        }else{
            dashboardConfigList = new LinkedList<String>();
        }
        return dashboardConfigList;
    }


    @SuppressWarnings("unchecked")
	private List<GraphConfig> getGraphConfigList(Element application, ApplicationConfig appConfig){
        /* read graphs */
        List<GraphConfig> graphConfigList = new LinkedList<GraphConfig>();
        if(application.getChild(GRAPHS) != null){
            List<Element> graphs = application.getChild(GRAPHS).getChildren(GRAPH);
            for (Object graphElement : graphs) {
                Element graph = (Element) graphElement;
                List<Element> attributes = graph.getChildren(GRAPH_ATTRIBUTE);
                List<GraphAttributeConfig> attributeConfigList = new LinkedList<GraphAttributeConfig>();
                for (Object attributeElement : attributes) {
                    Element attribute = (Element) attributeElement;
                    GraphAttributeConfig graphAttrConfig =
                            new GraphAttributeConfig(
                                    attribute.getAttributeValue(GRAPH_ATTRIBUTE_MBEAN),
                                    attribute.getAttributeValue(GRAPH_ATTRIBUTE_NAME),
                                    attribute.getAttributeValue(GRAPH_ATTRIBUTE_DISPLAY_NAME));
                    attributeConfigList.add(graphAttrConfig);
                }
                String graphId = graph.getAttributeValue(GRAPH_ID);
                String graphName = graph.getAttributeValue(GRAPH_NAME);
                long pollingInterval = Long.parseLong(graph.getAttributeValue(GRAPH_POLLING_INTERVAL));

                GraphConfig graphConfig =
                        new GraphConfig(graphId, graphName, pollingInterval, appConfig, attributeConfigList);
                graphConfig.setYAxisLabel(graph.getAttributeValue(GRAPH_Y_AXIS_LABEL));
                String scaleFactor = graph.getAttributeValue(GRAPH_SCALE_FACTOR);
                if (scaleFactor != null)
                    graphConfig.setScaleFactor(new Double(scaleFactor));
                String scaleUp = graph.getAttributeValue(GRAPH_SCALE_UP);
                if (scaleUp != null)
                    graphConfig.setScaleUp(Boolean.valueOf(scaleUp));

                graphConfigList.add(graphConfig);
            }
        }
        return graphConfigList;
    }

    @SuppressWarnings("unchecked")
	private List<AlertConfig> getAlertsList(Element application, ApplicationConfig appConfig){
        List<AlertConfig> alertsList = new LinkedList<AlertConfig>();
        if(application.getChild(ALERTS)!=null){
            List<Element> alerts = application.getChild(ALERTS).getChildren(ALERT);
            for (Element alert : alerts) {
                AlertConfig alertConfig = new AlertConfig();
                alertConfig.setAlertId(alert.getAttributeValue(ALERT_ID));
                alertConfig.setAlertName(alert.getAttributeValue(ALERT_NAME));
                List<Element> alertDeliveryList = alert.getChildren(ALERT_DELIVERY);
                String[] alertDelivery = new String[alertDeliveryList.size()];
                for (int i = 0; i < alertDeliveryList.size(); i++) {
                    Element alertDel = (Element) alertDeliveryList.get(i);
                    alertDelivery[i] = alertDel.getAttributeValue(ALERT_DELIVERY_TYPE);
                    if(alertDelivery[i].equals(AlertDeliveryConstants.EMAIL_ALERT_DELIVERY_TYPE)){
                        alertConfig.setEmailAddress(alertDel.getAttributeValue(
                                ConfigConstants.ALERT_EMAIL_ADDRESS));
                    }
                }
                alertConfig.setAlertDelivery(alertDelivery);

                /* set alert source */
                Element source = alert.getChild(ALERT_SOURCE);
                String sourceType = source.getAttributeValue(ALERT_SOURCE_TYPE);
                String mbean = source.getAttributeValue(ALERT_SOURCE_MBEAN);
                AlertSourceConfig sourceConfig = null;
                if (sourceType.equals(AlertSourceConfig.SOURCE_TYPE_NOTIFICATION))
                {
                    String notificationType = source.getAttributeValue(ALERT_SOURCE_NOTIFICATION_TYPE);
                    sourceConfig = new AlertSourceConfig(mbean, notificationType);
                } else if (sourceType.equals(AlertSourceConfig.SOURCE_TYPE_GAUGE_MONITOR)) {
                    String attribute = source.getAttributeValue(ALERT_ATTRIBUTE_NAME);
                    String lowThreshold = source.getAttributeValue(ALERT_ATTRIBUTE_LOW_THRESHOLD);
                    String highThreshold = source.getAttributeValue(ALERT_ATTRIBUTE_HIGH_THRESHOLD);
                    String attributeDataType = source.getAttributeValue(ALERT_ATTRIBUTE_DATA_TYPE);
                    sourceConfig = new AlertSourceConfig(mbean, attribute,
                            CoreUtils.valueOf(lowThreshold, attributeDataType),
                            CoreUtils.valueOf(highThreshold, attributeDataType),
                            attributeDataType);
                } else if (sourceType.equals(
                        AlertSourceConfig.SOURCE_TYPE_STRING_MONITOR)) {
                    String attribute = source.getAttributeValue(ALERT_ATTRIBUTE_NAME);
                    String stringAttributeValue = source.getAttributeValue(ALERT_STRING_ATTRIBUTE_VALUE);
                    sourceConfig = new AlertSourceConfig(mbean, attribute, stringAttributeValue);
                }else if(sourceType.equals(AlertSourceConfig.SOURCE_TYPE_APPLICATION_DOWN)){
                    sourceConfig = new AlertSourceConfig();
                }
                assert sourceConfig != null;
                sourceConfig.setApplicationConfig(appConfig);
                alertConfig.setAlertSourceConfig(sourceConfig);
                alertsList.add(alertConfig);
            }
        }
        return alertsList;
    }
}