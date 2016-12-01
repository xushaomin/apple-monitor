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
import com.appleframework.jmx.core.services.impl.ServiceContextImpl;
import com.appleframework.jmx.core.data.OperationResultData;

/**
 * Factory to create ServiceContext object for invoking service methods
 * in a remote jmanage instance.
 * <p>
 * The first thing to do is to set the URL of the remote jManage instance:
 * <p>
 * <code>
 * RemoteServiceContextFactory.setJManageURL("http://localhost:9090");
 * </code>
 * <p>
 * Next an instance of ServiceContext is required to invoke a service operation:
 * <p>
 * <code>
 * ServiceContext context = RemoteServiceContextFactory.getServiceContext(
 *                 "admin", "123456",
                "testApp1", "jmanage:name=DataFormat,type=test");
 * </code>
 * <p>
 * And finaly get a service object and execute a method:
 * <p>
 * <code>
 * MBeanService mbeanService = ServiceFactory.getMBeanService(); <br/>
 * OperationResultData[] result =
 *               mbeanService.invoke(context, "retrieveXMLData", new String[]{});
 * </code>
 * <p>
 * Date:  Nov 22, 2005
 * @author	Rakesh Kalra
 */
public class RemoteServiceContextFactory {

    static{
        ServiceFactory.init(ServiceFactory.MODE_REMOTE);
    }

    /**
     * Set the URL at which jManage is running. This needs to be done
     * before calling a service layer method.
     *
     * @param jmanageURL remote jManage URL, e.g., http://localhost:9090
     */
    public static void setJManageURL(String jmanageURL){
        HttpServiceProxy.setRemoteURL(jmanageURL);
    }

    /**
     * Get a ServiceContext for invoking a Service function.
     * The ServiceContext is built based on the given username,
     * applicationName and the MBean name.
     *
     * @param username      user that will be used for invoking service function
     * @param password      password for the given user
     * @param applicationName   application name (optional)
     * @param mbeanName     MBean name (optional)
     * @return  instance of ServiceContext which can be used to invoke
     *              service layer methods
     */
    public static ServiceContext getServiceContext(String username,
                                                   String password,
                                                   String applicationId,
                                                   String mbeanName){
        ServiceContextImpl context = new ServiceContextImpl();
        /* set application name */
        context.setApplicationId(applicationId);
        /* set mbean name */
        context.setMBeanName(mbeanName);
        return context;
    }

    /**
     * runs a test against the jmanage test application.
     */
    public static void main(String[] args){
        RemoteServiceContextFactory.setJManageURL("http://localhost:9090");
        ServiceContext context = RemoteServiceContextFactory.getServiceContext(
                "admin", "123456",
                "testApp1", "jmanage:name=DataFormat,type=test");
        MBeanService mbeanService = ServiceFactory.getMBeanService();
        OperationResultData[] result =
                mbeanService.invoke(context, "retrieveXMLData", new String[]{});
        /* this is not a cluster, so the number of results must be 1 */
        assert result.length == 1;

        System.out.println(result[0].isError()?"Error": "OK");
        System.out.println(result[0].getDisplayOutput());
    }
}
