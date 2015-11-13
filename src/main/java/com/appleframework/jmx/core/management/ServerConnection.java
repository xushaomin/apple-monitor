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
package com.appleframework.jmx.core.management;

import java.util.Set;
import java.util.List;
import java.io.IOException;

/**
 * This interface is the abstraction between different MBeanServer
 * implementations and jmanage application. jManage application talks to
 * different MBeanServers via this connection.
 * <p>
 * We may be able to leverage this abstraction to talk to applications
 * which support some other management protocol (like SNMP) than JMX.
 *
 * date:  Aug 12, 2004
 * @author	Rakesh Kalra
 */
public interface ServerConnection {

    /**
     * Queries the management objects based on the given object name, containing
     * the search criteria.
     *
     * @param objectName
     * @return
     */
    public Set<ObjectName> queryNames(ObjectName objectName);

    /**
     * Invokes the given "operationName" on the object identified by
     * "objectName".
     *
     * @param objectName
     * @param operationName
     * @param params
     * @param signature
     * @return
     */
    public Object invoke(ObjectName objectName, String operationName, Object[] params, String[] signature);

    /**
     * Returns the information about the given objectName.
     *
     * @param objectName
     * @return
     */
    public ObjectInfo getObjectInfo(ObjectName objectName);

    /**
     * Gets the value for a single attribute.
     *
     * @param objectName
     * @param attributeName
     * @return attribute value
     */
    public Object getAttribute(ObjectName objectName, String attributeName);

    /**
     * Returns a list of ObjectAttribute objects containing attribute names
     * and values for the given attributeNames
     *
     * @param objectName
     * @param attributeNames
     * @return
     */
    public List<ObjectAttribute> getAttributes(ObjectName objectName, String[] attributeNames);

    /**
     * Saves the attribute values.
     *
     * @param objectName
     * @param attributeList list of ObjectAttribute objects
     */
    public List<ObjectAttribute> setAttributes(ObjectName objectName, List<ObjectAttribute> attributeList);

    public void addNotificationListener(ObjectName objectName,
                                        ObjectNotificationListener listener,
                                        ObjectNotificationFilter filter,
                                        Object handback);

    public void removeNotificationListener(ObjectName objectName,
                                           ObjectNotificationListener listener,
                                           ObjectNotificationFilter filter,
                                           Object handback);

    public void createMBean(String className, ObjectName name, Object[] params, String[] signature);

    public void unregisterMBean(ObjectName objectName);

    /**
     *
     * @param objectName
     * @return an object of type javax.management.ObjectName
     */
    public Object buildObjectName(String objectName);

    /**
     * checks if this connection is open
     * @return true if this connection is open
     */
    public boolean isOpen();

    /**
     * Closes the connection to the server
     */
    public void close() throws IOException;
    
    //
    public Object getServerConnection();
    
}
