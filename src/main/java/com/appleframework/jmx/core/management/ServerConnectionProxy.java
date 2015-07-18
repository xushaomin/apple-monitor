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

import com.appleframework.jmx.core.management.metadata.Repository;
import com.appleframework.jmx.core.util.Loggers;

import java.util.List;
import java.util.LinkedList;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * ServerConnectionProxy updates the context classloader before calling
 * any method on the wrapped ServerConnection object. It later sets the
 * classloader back to the original classloader.
 *
 * date:  Aug 19, 2004
 * @author	Rakesh Kalra
 */
public class ServerConnectionProxy implements InvocationHandler {

    private static final Logger logger = Loggers.getLogger(ServerConnectionProxy.class);

    private ServerConnection connection;
    private ClassLoader classLoader;

    public ServerConnectionProxy(ServerConnection connection, ClassLoader classLoader){
        this.connection = connection;
        this.classLoader = classLoader;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            /* temporarily change the thread context classloader */
            Thread.currentThread().setContextClassLoader(classLoader);
            /* invoke the method on the wrapped ServerConnection */
            if(method.getName().equals("getAttributes")){
                assert args.length == 2;
                return getAttributes((ObjectName)args[0], (String[])args[1]);
            }else{
                Object result = method.invoke(connection, args);
                if(method.getName().equals("getObjectInfo")){
                    return Repository.applyMetaData((ObjectInfo)result, (ServerConnection)proxy);
                }
                return result;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        } finally {
            /* change the thread context classloader back to the
                    original classloader*/
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
    }

    /**
     * Returns a list of ObjectAttribute objects containing attribute names
     * and values for the given attributeNames
     *
     * @param objectName
     * @param attributeNames
     * @return
     */
    private List<ObjectAttribute> getAttributes(ObjectName objectName, String[] attributeNames) {
        /* some attribute values may not be serializable, hence may fail,
            hence we need to get one attribute at a time */
        List<ObjectAttribute> attributeList = new LinkedList<ObjectAttribute>();
        for(int i=0; i<attributeNames.length; i++){
            attributeList.add(getAttribute(objectName, attributeNames[i]));
        }
        return attributeList;
    }

    private ObjectAttribute getAttribute(ObjectName objectName, String attributeName){
        try {
            // TODO: It will be better to have a getAttribute method which
            // just works on a single attribute
            List<ObjectAttribute> attrList = connection.getAttributes(objectName, new String[]{attributeName});
            if(attrList.size() > 0)
                return (ObjectAttribute)attrList.get(0);
        } catch (Exception e) {
            String msg = "Error retriving attribute=" + attributeName + ", objectName=" 
            		+ objectName + ", error=" + e.getMessage();
            logger.warn( msg);
            logger.info( msg, e);
            return new ObjectAttribute(attributeName, ObjectAttribute.STATUS_ERROR, e.getMessage());
        }
        return new ObjectAttribute(attributeName, ObjectAttribute.STATUS_NOT_FOUND, null);
    }
}
