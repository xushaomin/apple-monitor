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
package com.appleframework.jmx.core.modules;

import com.appleframework.jmx.core.management.*;
import com.appleframework.jmx.core.management.ObjectName;
import com.appleframework.jmx.core.management.MalformedObjectNameException;
import com.appleframework.jmx.core.util.Loggers;

import javax.management.*;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;
import java.util.*;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;

/**
 * Date: Sep 3, 2004 11:19:06 PM
 * @author Shashank Bellary
 * @author Rakesh Kalra
 */
public abstract class JMXServerConnection implements ServerConnection{

    private static final Logger logger = Loggers.getLogger(JMXServerConnection.class);

    private Object mbeanServer;
    // this is required as the mbeanServer object may be of a class
    //   which is private inner class (e.g. in JSR160)
    private Class<?> mbeanServerClass;

    public JMXServerConnection(){}

    public JMXServerConnection(Object mbeanServer, Class<?> mbeanServerClass){
        assert mbeanServer != null;
        assert mbeanServerClass != null;
    	this.mbeanServer = mbeanServer;
        this.mbeanServerClass = mbeanServerClass;
    }

    /**
     * Queries the MBeanServer for the given object name pattern.
     *
     * @param objectName the ObjectName pattern
     * @return Set of ObjectName objects
     */
    public Set<ObjectName> queryNames(ObjectName objectName){

        Class<?>[] methodSignature = new Class[]{javax.management.ObjectName.class, javax.management.QueryExp.class};
        Object[] methodArgs = new Object[]{toJMXObjectName(objectName), null};
        Set<ObjectName> mbeans = (Set)callMBeanServer("queryNames", methodSignature, methodArgs);
        return toJmanageObjectNameInstance(mbeans);
    }


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
    public Object invoke(ObjectName objectName,
                         String operationName,
                         Object[] params,
                         String[] signature) {

        Class<?>[] methodSignature = new Class[]{javax.management.ObjectName.class,
                                           String.class,
                                           new Object[0].getClass(),
                                           new String[0].getClass()};
        Object[] methodArgs = new Object[]{toJMXObjectName(objectName),
                                           operationName,
                                           params,
                                           signature};
        return callMBeanServer("invoke", methodSignature, methodArgs);
    }

    /**
     * Returns the information about the given objectName.
     *
     * @param objectName
     * @return
     */
    public ObjectInfo getObjectInfo(ObjectName objectName) {
        Class<?>[] methodSignature = new Class[]{javax.management.ObjectName.class};
        Object[] methodArgs = new Object[]{toJMXObjectName(objectName)};
        MBeanInfo mbeanInfo = (MBeanInfo)callMBeanServer("getMBeanInfo", methodSignature, methodArgs);
        return toObjectInfo(objectName, mbeanInfo);
    }

    /**
     * Gets the value for a single attribute.
     *
     * @param objectName
     * @param attributeName
     * @return attribute value
     */
    public Object getAttribute(ObjectName objectName, String attributeName){
        Class<?>[] methodSignature = new Class[]{javax.management.ObjectName.class, String.class};
        Object[] methodArgs = new Object[]{toJMXObjectName(objectName), attributeName};
        return callMBeanServer("getAttribute", methodSignature, methodArgs);
    }

    /**
     * Returns a list of ObjectAttribute objects containing attribute names
     * and values for the given attributeNames
     *
     * @param objectName
     * @param attributeNames
     * @return
     */
    public List<ObjectAttribute> getAttributes(ObjectName objectName, String[] attributeNames) {
        Class<?>[] methodSignature = new Class[]{javax.management.ObjectName.class, new String[0].getClass()};
        Object[] methodArgs = new Object[]{toJMXObjectName(objectName), attributeNames};
        AttributeList attrList = (AttributeList)callMBeanServer("getAttributes", methodSignature, methodArgs);
        return toObjectAttributeList(attrList);
    }

    /**
     * Saves the attribute values.
     *
     * @param objectName
     * @param attributeList list of ObjectAttribute objects
     */
    public List<ObjectAttribute> setAttributes(ObjectName objectName, List<ObjectAttribute> attributeList) {
        Class<?>[] methodSignature = new Class[]{javax.management.ObjectName.class, javax.management.AttributeList.class};
        Object[] methodArgs = new Object[]{toJMXObjectName(objectName), toJMXAttributeList(attributeList)};
        AttributeList output = (AttributeList)callMBeanServer("setAttributes", methodSignature, methodArgs);
        return toObjectAttributeList(output);
    }

    // maps for storing notification objects to jmx notification
    // object relationships
    protected Map<ObjectNotificationListener, Object> notifications = 
    	new HashMap<ObjectNotificationListener, Object>();
    protected Map<ObjectNotificationFilter, NotificationFilter> notifFilters = 
    	new HashMap<ObjectNotificationFilter, NotificationFilter>();

    public void addNotificationListener(ObjectName objectName,
                                        ObjectNotificationListener listener,
                                        ObjectNotificationFilter filter,
                                        Object handback){

        NotificationListener notifListener =
                toJMXNotificationListener(listener);
        notifications.put(listener, notifListener);
        NotificationFilter notifFilter =
                toJMXNotificationFilter(filter);
        notifFilters.put(filter, notifFilter);

        Class<?>[] methodSignature = new Class[]{javax.management.ObjectName.class,
                                           NotificationListener.class,
                                           NotificationFilter.class,
                                           Object.class};
        Object[] methodArgs = new Object[]{toJMXObjectName(objectName),
                                           notifListener,
                                           notifFilter,
                                           handback};
        callMBeanServer("addNotificationListener", methodSignature, methodArgs);
    }

    public void removeNotificationListener(ObjectName objectName,
                                           ObjectNotificationListener listener,
                                           ObjectNotificationFilter filter,
                                           Object handback){

        NotificationListener notifListener =
                (NotificationListener)notifications.remove(listener);
        NotificationFilter notifFilter =
                (NotificationFilter)notifFilters.remove(filter);
        assert notifListener != null;
        assert notifFilter != null;

        Class<?>[] methodSignature = new Class[]{javax.management.ObjectName.class,
                                           NotificationListener.class,
                                           NotificationFilter.class,
                                           Object.class};
        Object[] methodArgs = new Object[]{toJMXObjectName(objectName),
                                           notifListener,
                                           notifFilter,
                                           handback};
        callMBeanServer("removeNotificationListener", methodSignature, methodArgs);
    }

    // todo: this method will need to throw InstanceAlreadyExistsException
    public void createMBean(String className,
                            ObjectName name,
                            Object[] params,
                            String[] signature){
        Class<?>[] methodSignature = new Class[]{String.class,
                                           javax.management.ObjectName.class,
                                           new Object[0].getClass(),
                                           new String[0].getClass()};
        Object[] methodArgs = new Object[]{className, toJMXObjectName(name),
                                           params, signature};
        callMBeanServer("createMBean", methodSignature, methodArgs);
    }

    public void unregisterMBean(ObjectName objectName){

        Class<?>[] methodSignature = new Class[]{javax.management.ObjectName.class};
        Object[] methodArgs = new Object[]{toJMXObjectName(objectName)};
        callMBeanServer("unregisterMBean", methodSignature, methodArgs);
    }

    public Object buildObjectName(String objectName){
        try {
            return new javax.management.ObjectName(objectName);
        } catch (javax.management.MalformedObjectNameException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * checks if this connection is open.
     * It simply calls getMBeanCount() method on MBeanServer Connection.
     * If there is no errors, then the connection is assumed to be open.
     *
     * @return true if this connection is open
     */
    public boolean isOpen(){
        try {
            Class<?>[] methodSignature = new Class[0];
            Object[] methodArgs = new Object[0];
            callMBeanServer("getMBeanCount", methodSignature, methodArgs);
            return true;
        } catch (Exception e) {
            logger.info( "Connection to the server is lost. " +
                    "Error message: " + e.getMessage());
            return false;
        }
    }

    /**
     * Closes the connection to the server
     */
    public void close() throws IOException{
        logger.info("Noop close operation.");
    }

    ///////////////////////////////////////////////////////////////////////////
    // Utility methods

    private Object callMBeanServer(String methodName,
                                   Class<?>[] params,
                                   Object[] args){

        try {
            Method method = mbeanServerClass.getMethod(methodName, params);
            return method.invoke(mbeanServer, args);
        } catch (InvocationTargetException e) {
            if(e.getCause() != null &&
                    e.getCause() instanceof RuntimeException){
                throw (RuntimeException)e.getCause();
            }
            throw new RuntimeException(e.getCause());
        } catch (Throwable e){
            if(e instanceof RuntimeException){
                throw (RuntimeException)e;
            }else{
                throw new RuntimeException(e);
            }
        }
    }

    protected static javax.management.ObjectName
            toJMXObjectName(ObjectName objectName){
        try {
            return new javax.management.ObjectName(objectName.toString());
        } catch (javax.management.MalformedObjectNameException e) {
            throw new MalformedObjectNameException(e);
        }
    }

    protected static ObjectName toJmanageObjectName(
            javax.management.ObjectName objectName){
        return new ObjectName(objectName.toString(),
                objectName.getCanonicalName());
    }

    /**
     * Converts a Set of javax.management.ObjectName to
     * com.appleframework.jmx.core.management.ObjectName
     */
	protected static Set<ObjectName> toJmanageObjectNameInstance(Set<ObjectName> mbeans){
        final Set<ObjectName> output = new HashSet<ObjectName>(mbeans.size());
        for(Iterator<?> it=mbeans.iterator(); it.hasNext();){
            javax.management.ObjectName objName = (javax.management.ObjectName)it.next();
            output.add(toJmanageObjectName(objName));
        }
        return output;
    }

    protected static ObjectInfo toObjectInfo(ObjectName objectName,
                                             MBeanInfo mbeanInfo){

        ObjectAttributeInfo[] attributes = toObjectAttributes(mbeanInfo.getAttributes());
        ObjectConstructorInfo[] constructors = toObjectConstructors(mbeanInfo.getConstructors());
        ObjectOperationInfo[] operations = toObjectOperations(mbeanInfo.getOperations());
        ObjectNotificationInfo[] notifications = toObjectNotifications(mbeanInfo.getNotifications());
        return new ObjectInfo(objectName, mbeanInfo.getClassName(),
                mbeanInfo.getDescription(), attributes,
                constructors, operations, notifications);
    }

    protected static ObjectAttributeInfo[] toObjectAttributes(MBeanAttributeInfo[] attributes){
        ObjectAttributeInfo[] objAttributes = new ObjectAttributeInfo[attributes.length];
        for(int i=0; i < attributes.length; i++) {
            objAttributes[i] = toObjectAttributeInfo(attributes[i]);
        }
        return objAttributes;
    }

    protected static ObjectAttributeInfo toObjectAttributeInfo(MBeanAttributeInfo attribute){

        return new ObjectAttributeInfo(attribute.getName(),
                attribute.getDescription(),
                attribute.getType(),
                attribute.isWritable(),
                attribute.isReadable(),
                attribute.isIs());
    }

    protected static ObjectConstructorInfo[] toObjectConstructors(MBeanConstructorInfo[] constructors){
        ObjectConstructorInfo[] objCtors = new ObjectConstructorInfo[constructors.length];
        for(int i=0; i < constructors.length; i++) {
            objCtors[i] = toObjectConstructorInfo(constructors[i]);
        }
        return objCtors;
    }

    protected static ObjectConstructorInfo toObjectConstructorInfo(MBeanConstructorInfo constructor){
        return new ObjectConstructorInfo(constructor.getName(),
                constructor.getDescription(),
                toObjectParameters(constructor.getSignature()));
    }

    protected static ObjectOperationInfo[] toObjectOperations(MBeanOperationInfo[] operations){
        ObjectOperationInfo[] objOperations = new ObjectOperationInfo[operations.length];
        for(int i=0; i < operations.length; i++) {
            objOperations[i] = toObjectOperationInfo(operations[i]);
        }
        return objOperations;
    }

    protected static ObjectOperationInfo toObjectOperationInfo(MBeanOperationInfo operation){
        return new ObjectOperationInfo(operation.getName(),
                operation.getDescription(),
                toObjectParameters(operation.getSignature()),
                operation.getReturnType(),
                operation.getImpact());
    }

    protected static ObjectNotificationInfo[] toObjectNotifications(MBeanNotificationInfo[] notifications){
        ObjectNotificationInfo[] objNotifications = new ObjectNotificationInfo[notifications.length];
        for(int i=0; i < notifications.length; i++) {
            objNotifications[i] = toObjectNotificationInfo(notifications[i]);
        }
        return objNotifications;
    }

    protected static ObjectNotificationInfo toObjectNotificationInfo(MBeanNotificationInfo notification){
        return new ObjectNotificationInfo(notification.getNotifTypes(),
                notification.getName(),
                notification.getDescription());
    }

    protected static ObjectParameterInfo[] toObjectParameters(MBeanParameterInfo[] parameters){
        ObjectParameterInfo[] objParameters = new ObjectParameterInfo[parameters.length];
        for(int i=0; i < parameters.length; i++) {
            objParameters[i] = toObjectParameterInfo(parameters[i]);
        }
        return objParameters;
    }

    protected static ObjectParameterInfo toObjectParameterInfo(MBeanParameterInfo parameter){

        return new ObjectParameterInfo(parameter.getName(),
                parameter.getDescription(), parameter.getType());
    }

    protected static List<ObjectAttribute> toObjectAttributeList(AttributeList attrList){
        final List<ObjectAttribute> objAttrList = new ArrayList<ObjectAttribute>(attrList.size());
        for(Iterator<?> it=attrList.iterator(); it.hasNext(); ){
            Attribute attr = (Attribute)it.next();
            objAttrList.add(toObjectAttribute(attr));
        }
        return objAttrList;
    }

    protected static ObjectAttribute toObjectAttribute(Attribute attr){
        Object value = attr.getValue();
        if(value != null){
            // todo: ideally this needs to be done only if the JMX classes
                //  are not compatible
            if(value instanceof CompositeData){
                value = JMXInterfaceProxy.newProxyInstance(CompositeData.class, value);
            }else if(value instanceof TabularData){
                value = JMXInterfaceProxy.newProxyInstance(TabularData.class, value);
            }
        }
        return new ObjectAttribute(attr.getName(), value);
    }

    protected static AttributeList toJMXAttributeList(List<ObjectAttribute> objAttrs){
        AttributeList attrList = new AttributeList(objAttrs.size());
        for(Iterator<?> it=objAttrs.iterator(); it.hasNext(); ){
            attrList.add(toJMXAttribute((ObjectAttribute)it.next()));
        }
        return attrList;
    }

    protected static Attribute toJMXAttribute(ObjectAttribute objAttr){
        return new Attribute(objAttr.getName(), objAttr.getValue());
    }

    protected static ObjectNotification toObjectNotification(Notification n){

        return new ObjectNotification(n.getType(), n.getSource(),
                n.getSequenceNumber(), n.getTimeStamp(),
                n.getMessage(), n.getUserData());
    }

    protected static NotificationListener toJMXNotificationListener(
            final ObjectNotificationListener listener){

        return new NotificationListener(){
            public void handleNotification(Notification notification, Object handback) {
                listener.handleNotification(toObjectNotification(notification), handback);
            }
        };
    }

    protected static NotificationFilter toJMXNotificationFilter(final ObjectNotificationFilter filter){
		NotificationFilterSupport notificationFilter = new NotificationFilterSupport();
		for (Iterator<?> it = filter.getEnabledTypes().iterator(); it.hasNext();) {
			notificationFilter.enableType((String) it.next());
		}
		return notificationFilter;
    }
}
