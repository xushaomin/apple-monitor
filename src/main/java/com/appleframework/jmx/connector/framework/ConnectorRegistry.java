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
package com.appleframework.jmx.connector.framework;

import org.apache.commons.modeler.Registry;
import org.apache.commons.modeler.ManagedBean;
import com.appleframework.jmx.core.config.ApplicationConfigManager;
import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.config.ClassLoaderRepository;
import com.appleframework.jmx.core.util.CoreUtils;
import com.appleframework.jmx.core.util.Loggers;

import javax.management.MBeanServerFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBean;
import java.util.*;
import org.apache.log4j.Logger;
import java.net.URL;
import java.io.InputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * MBeanServer repository for each connector application. 
 * 
 * @author	Tak-Sang Chan
 */
public class ConnectorRegistry extends Registry {

    private static final Logger logger = Loggers.getLogger(ConnectorRegistry.class);    
    private static final String DOMAIN_CONNECTOR = "connector";
    private static final String MBEANS_DESCRIPTOR = "META-INF/mbeans-descriptors.xml";
    private static final String FACTORY_METHOD = "getInstance";

    private static Map<String, ConnectorRegistry> entries = new HashMap<String, ConnectorRegistry>();

    private MBeanServer server;
    
    /**
     * Load mbeans of configured connectors into the registry.  The method
     * is call during startup.
     * 
     * @throws Exception
     */
    public static void load() throws Exception {
        List<ApplicationConfig> applications = ApplicationConfigManager.getApplications();
        for(ApplicationConfig config : applications) {
            if (config.isCluster()) {
                List<ApplicationConfig> clusterApps = config.getApplications();
                for(ApplicationConfig clusterConfig : clusterApps) {
                    load(clusterConfig);
                }
            }
            else {
                load(config);
            }
        }
    }
    
    public synchronized static ConnectorRegistry getInstance(ApplicationConfig config) throws Exception {
        String appId = config.getApplicationId();
        ConnectorRegistry instance = (ConnectorRegistry) entries.get(appId);
        if (instance == null) {
            instance = create(config);
        }

        return instance;
    }
    
    public static void remove(ApplicationConfig config) throws Exception {
        String appId = config.getApplicationId();
        ConnectorRegistry registry = entries.remove(appId);
        if (registry != null) {
            registry.unregisterAllMBeans(appId);
        }
    }
    
    @SuppressWarnings("deprecation")
	private static ConnectorRegistry create(ApplicationConfig config) throws Exception {

        Map<String, String> paramValues = config.getParamValues();
        String appId = config.getApplicationId();
        String connectorId = (String) paramValues.get("connectorId");

        File file1 = new File(CoreUtils.getConnectorDir() + File.separatorChar + connectorId);

        URL[] urls = new URL[]{file1.toURL()};
        ClassLoader cl = ClassLoaderRepository.getClassLoader(urls, true);

        //URLClassLoader cl = new URLClassLoader(urls,
        //        ConnectorMBeanRegistry.class.getClassLoader());

        Registry.MODELER_MANIFEST = MBEANS_DESCRIPTOR;
        URL res = cl.getResource(MBEANS_DESCRIPTOR);

        logger.info("Application ID   : " + appId);        
        logger.info("Connector Archive: " + file1.getAbsoluteFile());                        
        logger.info("MBean Descriptor : " + res.toString());

        //Thread.currentThread().setContextClassLoader(cl);
        //Registry.setUseContextClassLoader(true);

        ConnectorRegistry registry = new ConnectorRegistry();
        registry.loadMetadata(cl);

        String[] mbeans = registry.findManagedBeans();

        MBeanServer server = MBeanServerFactory.newMBeanServer(DOMAIN_CONNECTOR);

        for (int i = 0; i < mbeans.length; i++) {
            ManagedBean managed = registry.findManagedBean(mbeans[i]);
            String clsName = managed.getType();
            String domain = managed.getDomain();
            if (domain == null) {
                domain = DOMAIN_CONNECTOR;
            }

            Class<?> cls = Class.forName(clsName, true, cl);            
            Object objMBean = null;

            // Use the factory method when it is defined.
            Method[] methods = cls.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(FACTORY_METHOD) && Modifier.isStatic(method.getModifiers())) {
                    objMBean = method.invoke(null);
                    logger.info( "Create MBean using factory method.");
                    break;
                }
            }
            
            if (objMBean == null) {
                objMBean = cls.newInstance();
            }
            
            // Call the initialize method if the MBean extends ConnectorSupport class
            if (objMBean instanceof ConnectorSupport) {
                Method method = cls.getMethod("initialize", new Class[] {Map.class});
                Map<String, String> props = config.getParamValues();
                method.invoke(objMBean, new Object[] {props});
            }
            ModelMBean mm = managed.createMBean(objMBean);

            String beanObjName = domain + ":name=" + mbeans[i];
            server.registerMBean(mm, new ObjectName(beanObjName));
        }

        registry.setMBeanServer(server);
        entries.put(appId, registry);
        return registry;
    }
    
    public void setMBeanServer(MBeanServer server) {
        this.server = server;
    }

    public MBeanServer getMBeanServer() {
        return this.server;
    }
    
    private void unregisterAllMBeans(String appId) throws Exception {
        Set<ObjectName> objNames = this.server.queryNames(new ObjectName("*:appId=" + appId + ",*"), null);
        for(ObjectName name : objNames) {
            logger.info("Unregistered MBean: " + name);
            this.server.unregisterMBean(name);
        }
    }
    
    @SuppressWarnings("deprecation")
    private void loadMetadata(ClassLoader cl) throws Exception {
        // Fix a bug in the Registry class
        try {
            Enumeration<?> en = cl.getResources(MODELER_MANIFEST);
            while (en.hasMoreElements()) {
                URL url = (URL) en.nextElement();
                InputStream is = url.openStream();
                loadDescriptors("MbeansDescriptorsDOMSource", is, null);
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }
    
    private static void load(ApplicationConfig config) throws Exception {
        String appType = config.getType();
        if (appType.equals("connector")) {
            create(config);
        }
    }
    
}
