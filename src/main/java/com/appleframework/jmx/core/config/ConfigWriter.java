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

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.core.crypto.Crypto;
import com.appleframework.jmx.core.modules.jsr160.JSR160ApplicationConfig;
import com.appleframework.jmx.database.constant.StateType;
import com.appleframework.jmx.database.entity.AppConfigEntity;
import com.appleframework.jmx.database.service.AppConfigService;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

/**
 *
 * date:  Jun 21, 2004
 * @author	Rakesh Kalra, Shashank Bellary
 */
@Service
public class ConfigWriter {

	@Resource
    private AppConfigService appConfigService;
		
    public void write(ApplicationConfig application) {
        try {
        	if(application instanceof ApplicationClusterConfig) {
        		if (application.getApplications().size() >= 1) {
					for (ApplicationConfig config : application.getApplications()) {
						write2(config);
					}
				}
        		else {
					write2(application);
				}
        	}
        	else if (application instanceof JSR160ApplicationConfig) {
        		write2(application);
			}
        	else {
				System.out.println("error");
			}
            
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    private void write2(ApplicationConfig application) throws Exception {
    	Document doc = new Document();
        Element rootElement = createApplicationElement(application);
        doc.setRootElement(rootElement);
        String content = this.jdomToXmlStr(doc);
                    
        Integer clusterId = Integer.parseInt(application.getClusterConfig().getApplicationId());
        Integer applicationId = Integer.parseInt(application.getApplicationId());
        AppConfigEntity entity = appConfigService.get(applicationId);
        if (null == entity) {
        	entity = new AppConfigEntity();
        	entity.setId(applicationId);
        	entity.setClusterId(clusterId);
        	entity.setState(StateType.START.getIndex());
        	entity.setAppConfig(content);
        	entity.setIsAlert(false);
        	appConfigService.insert(entity);
		}
        else {
        	entity.setAppConfig(content);
        	appConfigService.update(entity);
        }
    }
    
    public void delete(ApplicationConfig application) {
        try {
        	String applicationId = application.getApplicationId();
        	AppConfigEntity entity = appConfigService.get(Integer.parseInt(applicationId));
        	entity.setState(StateType.DELETE.getIndex());
        	appConfigService.update(entity);            
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
	public String jdomToXmlStr(Document icDoc) throws Exception {
		StringWriter lcOut = new StringWriter();
		try {
			new XMLOutputter().output(icDoc, lcOut);
			return lcOut.toString();
		} catch (Exception e) {
			throw new Exception(new StringBuffer("JDom convert to XML string fail ").append(
					e.getMessage()).toString());
		}
	}
    
    /*public void write(Config config) {
        try {
            Document doc = new Document();
            Element rootElement = new Element(ConfigConstants.APPLICATION_CONFIG);
             applications 
            Element applicationsElement = new Element(ConfigConstants.APPLICATIONS);
            rootElement.addContent(applicationsElement);
            for(ApplicationConfig application : config.getApplications()){
                 get the application or application-cluster element 
                Element applicationElement = null;
                if(application.isCluster()){
                    applicationElement =
                            createApplicationClusterElement(application);
                }else{
                    applicationElement = createApplicationElement(application);
                }

                 add this application element to the root node 
                applicationsElement.addContent(applicationElement);
            }

            doc.setRootElement(rootElement);
            System.out.println(doc.toString());
             write to the disc 
            XMLOutputter writer = new XMLOutputter();
            writer.output(doc, new FileOutputStream(ConfigConstants.DEFAULT_CONFIG_FILE_NAME));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    /**
     * Creates the complete XML for ApplicationCluster
     *
     * @param application
     * @return
     */
    /*private Element createApplicationClusterElement(ApplicationConfig application){
        assert application.isCluster();
        Element applicationElement = createApplicationElement(application);
        Element childApplicationsElement = new Element(ConfigConstants.APPLICATIONS);
        applicationElement.addContent(childApplicationsElement);
        for(ApplicationConfig appConfig : application.getApplications()){
            Element childAppElement = createApplicationElement(appConfig);
            childApplicationsElement.addContent(childAppElement);
        }
        return applicationElement;
    }*/

    private Element createApplicationElement(ApplicationConfig application){
        Element applicationElement = null;
        if(application.isCluster()){
            applicationElement = new Element(ConfigConstants.APPLICATION_CLUSTER);
        }else{
            applicationElement = new Element(ConfigConstants.APPLICATION);
        }
        // <application id="1234" name="Weblogic 6.1" server="localhost" port="7001" 
        //username="system" password="12345678" type="Weblogic">
        applicationElement.setAttribute(ConfigConstants.APPLICATION_ID, application.getApplicationId());
        applicationElement.setAttribute(ConfigConstants.APPLICATION_NAME, application.getName());
        if(application.getHost() != null){
            applicationElement.setAttribute(ConfigConstants.HOST, application.getHost());
            applicationElement.setAttribute(ConfigConstants.PORT, String.valueOf(application.getPort()));
        }
        if(application.getURL() != null){
            applicationElement.setAttribute(ConfigConstants.URL, application.getURL());
        }
        if(application.getUsername() != null){
            applicationElement.setAttribute(ConfigConstants.USERNAME, application.getUsername());
        }
        if(application.getPassword() != null){
            String encryptedPassword = Crypto.encryptToString(application.getPassword());
            applicationElement.setAttribute(ConfigConstants.PASSWORD, encryptedPassword);
        }
        if(!application.isCluster()){
            applicationElement.setAttribute(ConfigConstants.APPLICATION_TYPE, application.getType());
        }
        final Map<String, String> params = application.getParamValues();
        if(params != null){
			for (Iterator<?> param = params.keySet().iterator(); param.hasNext();) {
                String paramName = (String)param.next();
                Element paramElement = new Element(ConfigConstants.PARAMETER);
                Element paramNameElement = new Element(ConfigConstants.PARAMETER_NAME);
                Element paramValueElement = new Element(ConfigConstants.PARAMETER_VALUE);
                paramNameElement.setText(paramName);
                paramValueElement.setText((String)params.get(paramName));
                paramElement.addContent(paramNameElement);
                paramElement.addContent(paramValueElement);
                applicationElement.addContent(paramElement);
            }
        }

        /* add mbeans */
        Element mbeansElement = createMBeansElement(application);
        applicationElement.addContent(mbeansElement);

        /* add alerts*/
        Element alertsElement = createAlertsElement(application);
        applicationElement.addContent(alertsElement);
        if(!application.isCluster()){
            /* add graphs */
            Element graphsElement = createGraphsElement(application);
            applicationElement.addContent(graphsElement);
        }

        /* dashboards */
        if(!application.isCluster()){
            Element dashboardsElement = createDashboardsElement(application);
            applicationElement.addContent(dashboardsElement);
        }

        return applicationElement;
    }

    /**
     * Store dashboards configured for a given application.
     *
     * @param application
     * @return
     */
    private Element createDashboardsElement(ApplicationConfig application){
        Element dashboards = new Element(ConfigConstants.DASHBOARDS);
        if(application.getDashboards() == null)
            return dashboards;
        for(String dashboardId : application.getDashboards()){
            Element dashboard = new Element(ConfigConstants.DASHBOARD);
            dashboard.setAttribute(ConfigConstants.DASHBOARD_ID, dashboardId);
            dashboards.addContent(dashboard);
        }
        return dashboards;
    }



    private Element createMBeansElement(ApplicationConfig application){
        Element mbeansElement = new Element(ConfigConstants.MBEANS);
        if(application.getMBeans() == null){
            return mbeansElement;
        }
        for(Iterator<?> mbeans = application.getMBeans().iterator();
            mbeans.hasNext();){
            MBeanConfig mbeanConfig = (MBeanConfig)mbeans.next();
            Element mbeanElement = new Element(ConfigConstants.MBEAN);
            mbeanElement.setAttribute(ConfigConstants.MBEAN_NAME, mbeanConfig.getName()) ;
            Element objectNameElement = new Element(ConfigConstants.MBEAN_OBJECT_NAME);
            objectNameElement.setText(mbeanConfig.getObjectName());
            mbeanElement.addContent(objectNameElement);
            mbeansElement.addContent(mbeanElement);
        }
        return mbeansElement;
    }

    private Element createAlertsElement(ApplicationConfig application){
        Element alertsElement = new Element(ConfigConstants.ALERTS);
        if(application.getAlerts()==null){
            return alertsElement;
        }
		for (Iterator<?> alerts = application.getAlerts().iterator(); alerts.hasNext();){
            AlertConfig alertConfig = (AlertConfig)alerts.next();
            Element alertElement = new Element(ConfigConstants.ALERT);
            alertElement.setAttribute(ConfigConstants.ALERT_ID, alertConfig.getAlertId());
            alertElement.setAttribute(ConfigConstants.ALERT_NAME, alertConfig.getAlertName());
            // add source
            AlertSourceConfig sourceConfig = alertConfig.getAlertSourceConfig();
            Element source = new Element(ConfigConstants.ALERT_SOURCE);
            source.setAttribute(ConfigConstants.ALERT_SOURCE_TYPE, sourceConfig.getSourceType());
            if(!sourceConfig.getSourceType().equals(AlertSourceConfig.SOURCE_TYPE_APPLICATION_DOWN)){
                source.setAttribute(ConfigConstants.ALERT_SOURCE_MBEAN, sourceConfig.getObjectName());
            }
            if(sourceConfig.getSourceType().equals(AlertSourceConfig.SOURCE_TYPE_NOTIFICATION)){
                source.setAttribute(ConfigConstants.ALERT_SOURCE_NOTIFICATION_TYPE, sourceConfig.getNotificationType());
            }else if(sourceConfig.getSourceType().equals(AlertSourceConfig.SOURCE_TYPE_GAUGE_MONITOR)){
                source.setAttribute(ConfigConstants.ALERT_ATTRIBUTE_NAME, sourceConfig.getAttributeName());
                source.setAttribute(ConfigConstants.ALERT_ATTRIBUTE_DATA_TYPE, sourceConfig.getAttributeDataTYpe());
                source.setAttribute(ConfigConstants.ALERT_ATTRIBUTE_LOW_THRESHOLD, sourceConfig.getLowThreshold().toString());
                source.setAttribute(ConfigConstants.ALERT_ATTRIBUTE_HIGH_THRESHOLD, sourceConfig.getHighThreshold().toString());
            }else if(sourceConfig.getSourceType().equals(AlertSourceConfig.SOURCE_TYPE_STRING_MONITOR)){
                source.setAttribute(ConfigConstants.ALERT_ATTRIBUTE_NAME, sourceConfig.getAttributeName());
                source.setAttribute(ConfigConstants.ALERT_STRING_ATTRIBUTE_VALUE, sourceConfig.getStringAttributeValue());
            }
            alertElement.addContent(source);

            // add delivery
            String[] alertDelivery = alertConfig.getAlertDelivery();
            for(int i=0;i<alertDelivery.length;i++){
                Element alertDel = new Element(ConfigConstants.ALERT_DELIVERY);
                alertDel.setAttribute(ConfigConstants.ALERT_DELIVERY_TYPE, alertDelivery[i]);
                if(alertDelivery[i].equals(AlertDeliveryConstants.ALERT_DELIVERY_TYPE_EMAIL)){
                    alertDel.setAttribute(ConfigConstants.ALERT_EMAIL_ADDRESS, alertConfig.getEmailAddress());
                }
                if(alertDelivery[i].equals(AlertDeliveryConstants.ALERT_DELIVERY_TYPE_SMS)){
                    alertDel.setAttribute(ConfigConstants.ALERT_SMS_MOBILE, alertConfig.getMobiles());
                }
                alertElement.addContent(alertDel);
            }


            alertsElement.addContent(alertElement);
        }
        return alertsElement;
    }

    private Element createGraphsElement(ApplicationConfig application){
        Element graphsElement = new Element(ConfigConstants.GRAPHS);
        if(application.getGraphs() == null){
            return graphsElement;
        }
        for(Iterator<GraphConfig> graphs = application.getGraphs().iterator();
            graphs.hasNext();){
            GraphConfig graphConfig = (GraphConfig)graphs.next();
            Element graphElement = new Element(ConfigConstants.GRAPH);
            graphElement.setAttribute(ConfigConstants.GRAPH_ID, graphConfig.getId());
            graphElement.setAttribute(ConfigConstants.GRAPH_NAME, graphConfig.getName());
            graphElement.setAttribute(ConfigConstants.GRAPH_POLLING_INTERVAL, Long.toString(graphConfig.getPollingInterval()));
            if(graphConfig.getYAxisLabel() != null){
                graphElement.setAttribute(ConfigConstants.GRAPH_Y_AXIS_LABEL, graphConfig.getYAxisLabel());
            }
            if(graphConfig.getScaleFactor() != null){
                graphElement.setAttribute(ConfigConstants.GRAPH_SCALE_FACTOR, graphConfig.getScaleFactor().toString());
            }
            if(graphConfig.isScaleUp() != null){
                graphElement.setAttribute(ConfigConstants.GRAPH_SCALE_UP, graphConfig.isScaleUp().toString());
            }

            for(Iterator<?> attributes = graphConfig.getAttributes().iterator(); attributes.hasNext();){
                GraphAttributeConfig attrConfig = (GraphAttributeConfig)attributes.next();
                Element attributeElement = new Element(ConfigConstants.GRAPH_ATTRIBUTE);
                attributeElement.setAttribute(ConfigConstants.GRAPH_ATTRIBUTE_MBEAN, attrConfig.getMBean());
                attributeElement.setAttribute(ConfigConstants.GRAPH_ATTRIBUTE_NAME, attrConfig.getAttribute());
                attributeElement.setAttribute(ConfigConstants.GRAPH_ATTRIBUTE_DISPLAY_NAME, attrConfig.getDisplayName());
                graphElement.addContent(attributeElement);
            }
            graphsElement.addContent(graphElement);
        }
        return graphsElement;
    }

}
