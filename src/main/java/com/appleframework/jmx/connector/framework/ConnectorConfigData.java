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

import java.util.*;


/**
 * 
 * An object of this class represent an instance of the connector.xml and
 * the corresponding class loader to create the MBean.
 * 
 * @author	Tak-Sang Chan
 *
 */
public class ConnectorConfigData {

    private final String id;
    private ClassLoader classLoader;
    private String connectorName;

    private List<String> fieldNames = new ArrayList<String>();
    
    private Map<String, ConnectorConfigField> fields = 
        new HashMap<String, ConnectorConfigField>();

    /**
     * Connector id corresponds to the archive name
     * @param id
     */
    public ConnectorConfigData(String id) {
        this.id = id;
    }

    public ConnectorConfigData(String id, ClassLoader cls) {
        this.id = id;
        this.setClassLoader(cls);
    }

    public String getId() {
        return id;
    }

    public ConnectorConfigField getAttribute(String name) {
        return (ConnectorConfigField) fields.get(name);
    }

    public void addField(String name, ConnectorConfigField attribute) {
        fieldNames.add(name);
        fields.put(name, attribute);
    }
    
    public String[] getFieldNames() {
        String[] names = new String[fieldNames.size()];
        fieldNames.toArray(names);
        return names;
    }

    public String[] getFieldDefaultValues() {
        String[] values = new String[fieldNames.size()];
        for (int i = 0; i < fieldNames.size(); i++) {
            ConnectorConfigField attr = (ConnectorConfigField) fields.get(fieldNames.get(i));
            values[i] = attr.getDefaultValue() != null ? attr.getDefaultValue() : "";
        }
        return values;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }
}
