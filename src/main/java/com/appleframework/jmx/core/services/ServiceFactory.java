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
package com.appleframework.jmx.core.services;

import com.appleframework.jmx.core.remote.client.HttpServiceProxy;

import java.util.Map;
import java.util.Hashtable;
import java.lang.reflect.Proxy;

/**
 * ServiceFactory is used to create instance of Service interfaces. This
 * factory needs to be initialized by calling the <code>init()</code> method.
 * The factory runs in local or remote mode. When in local mode, local
 * Service objects are created. When running in remote mode, an instance
 * of HttpServiceProxy is returned, which uses java serialization over HTTP
 * to talk to the jmanage web server.
 * <p>
 * When creating the local instance of Service interfaces, ServiceFactory
 * determines the implementation class name by appending "Impl" to the
 * Service interface name..
 *
 * @see com.appleframework.jmx.core.remote.client.HttpServiceProxy
 * date:  Jan 18, 2005
 * @author	Rakesh Kalra
 */
public class ServiceFactory {

    private static Map<Class<?>, Object> serviceClassToObjectMap = new Hashtable<Class<?>, Object>();

    public static final Integer MODE_LOCAL = new Integer(0);
    public static final Integer MODE_REMOTE = new Integer(1);

    private static Integer mode;

    /**
     * Initializes the ServiceFactory. This method can only be called once.
     * Initializing the factory in local mode, will result in local service
     * method calls. Initializing the factory in remove mode, will result in
     * remote service method calls.
     *
     * @param mode  the mode this factory should run in: local or remote
     */
    public static void init(Integer mode){
        assert ServiceFactory.mode == null : "ServiceFactory already initialized";
        assert mode.equals(MODE_LOCAL) || mode.equals(MODE_REMOTE) : "Invalid mode:" + mode;
        ServiceFactory.mode = mode;
    }

    /**
     * Gets the service object based on the factory mode.
     *
     * @param serviceInterface the Service interface.
     * @return  instance of service interface
     */
    public static Object getService(Class<?> serviceInterface){
        Object service = serviceClassToObjectMap.get(serviceInterface);
        if(service == null){
            if(mode.equals(MODE_LOCAL)){
                service = createService(serviceInterface);
            }else{
                service = createServiceProxy(serviceInterface);
            }
            serviceClassToObjectMap.put(serviceInterface, service);
        }
        return service;
    }

    public static ConfigurationService getConfigurationService() {
        return (ConfigurationService)getService(ConfigurationService.class);
    }

    public static MBeanService getMBeanService() {
        return (MBeanService)getService(MBeanService.class);
    }

    public static AlertService getAlertService() {
        return (AlertService)getService(AlertService.class);
    }

    private static Object createService(Class<?> serviceClass){
        final String implClassName = serviceClass.getName() + "Impl";
        try {
            Class<?> clazz = Class.forName(implClassName);
            return clazz.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Service impl. not found:" + implClassName, e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Service impl.:" + implClassName, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Service impl.:" + implClassName, e);
        }
    }

    private static Object createServiceProxy(Class<?> serviceClass){
        return Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class[]{serviceClass}, new HttpServiceProxy());
    }
}
