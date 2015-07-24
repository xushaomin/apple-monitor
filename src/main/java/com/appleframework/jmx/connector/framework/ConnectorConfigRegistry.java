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

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import com.appleframework.jmx.core.util.Loggers;

import java.util.*;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.net.URLClassLoader;
import java.net.URL;

/**
 * 
 * This class serves as the repository for ConnectorConfigData objects.
 * It calls the loader to get the connector metadata and converts the
 * data into ConnectorConfigData objects.
 * 
 * @author	Tak-Sang Chan
 */
public class ConnectorConfigRegistry {

    private static final Logger logger = Loggers.getLogger(ConnectorConfigRegistry.class);
    
    private static final String ELEM_FIELDS = "fields";    
    private static final String ATTR_NAME = "name";    
    private static final String ATTR_DEFAULT_VALUE = "defaultValue";
    
    /**
     * Repository for the connector config data objects, sorted by
     * the jar/directory names.
     */
    private static Map<String, ConnectorConfigData> registry = 
        new TreeMap<String, ConnectorConfigData>();

    private ConnectorConfigData configData;

    public static void init() {
        new ConnectorConfigRegistry();
    }

    public static ConnectorConfigData getConnectorConfigData(String id) {
        return (ConnectorConfigData) registry.get(id);
    }

    public static String[] getConnectorIdList() {
        String[] ids = new String[registry.keySet().size()];
        registry.keySet().toArray(ids);
        return ids;
    }

    public static String[] getConnectorNames() {
        String names[] = new String[registry.values().size()];
        int i = 0;
        for(ConnectorConfigData configData : registry.values()) {            
            names[i] = configData.getConnectorName();
            i++;
        }
        return names;
    }

 
    private ConnectorConfigRegistry() {
        try {
            ConnectorLoader cld = new ConnectorLoader();

            for (int i = 0; i < cld.getCount(); i++) {
                String id = cld.getArchiveNames()[i];
                configData = new ConnectorConfigData(id);

                URLClassLoader cl = new URLClassLoader(new URL[] {cld.getUrls()[i]});
                configData.setClassLoader(cl);

                Document dom = new SAXBuilder().build(cld.getConfigInputStreams()[i]);
                init(dom);

                registry.put(id, configData);
            }
        }
        catch (JDOMException ex) {
            logger.error("Error loading connector.xml");
            throw new RuntimeException(ex);
        } catch (IOException e) {
        	logger.error("Error loading connector.xml");
            throw new RuntimeException(e);
		}
    }

    @SuppressWarnings("unchecked")
    private void init(Document config) {

        Element rootElement = config.getRootElement();
        String name = rootElement.getAttributeValue(ATTR_NAME);
        configData.setConnectorName(name);

        List<Element> fields = rootElement.getChild(ELEM_FIELDS).getChildren();
        initFields(fields);
    }

    private void initFields(List<Element> attributeList) {
        for (Element elemAttr : attributeList) {            
            String name = elemAttr.getAttributeValue(ATTR_NAME);
            String defaultValue = elemAttr.getAttributeValue(ATTR_DEFAULT_VALUE);

            ConnectorConfigField configAttr = new ConnectorConfigField(name, defaultValue);
            configData.addField(name, configAttr);
        }
    }
}
