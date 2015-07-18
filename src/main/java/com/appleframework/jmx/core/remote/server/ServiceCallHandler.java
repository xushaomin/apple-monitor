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
package com.appleframework.jmx.core.remote.server;

import com.appleframework.jmx.core.auth.UnAuthorizedAccessException;
import com.appleframework.jmx.core.remote.InvocationResult;
import com.appleframework.jmx.core.remote.RemoteInvocation;
import com.appleframework.jmx.core.services.ServiceContextImpl;
import com.appleframework.jmx.core.services.ServiceException;
import com.appleframework.jmx.core.services.ServiceFactory;
import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.jmx.core.util.ErrorCodes;
import com.appleframework.jmx.core.management.ConnectionFailedException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * ServiceCallHandler handles remote requests
 * for executing Service layer methods. These remote requests originate from
 * HttpServiceProxy.
 * <p>
 * There are certain assumptions being made for the Service layer methods:
 * <ul>
 * <li>All service layer methods take ServiceContext as the first argument.
 * </ul>
 *
 * @see com.appleframework.jmx.core.remote.client.HttpServiceProxy
 * date:  Jan 17, 2005
 * @author	Rakesh Kalra
 */
public class ServiceCallHandler {

    private static final Logger logger =
            Loggers.getLogger(ServiceCallHandler.class);

    public static InvocationResult execute(RemoteInvocation invocation){
        try {
            Object result = executeInternal(invocation.getClassName(),
                    invocation.getMethodName(),
                    invocation.getSignature(),
                    invocation.getArgs());
            return new InvocationResult(result);
        } catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if(t != null){
                if(t instanceof ServiceException || t instanceof UnAuthorizedAccessException){
                    return new InvocationResult(t);
                }else if(t instanceof ConnectionFailedException){
                    return new InvocationResult(
                            new ServiceException(ErrorCodes.CONNECTION_FAILED));
                }
            }
            logger.error( "Error while invoking: " +
                    invocation.getClassName() +"->"+ invocation.getMethodName(),
                    e);
            throw new RuntimeException(e);
        } catch(Exception e){
            logger.error( "Error while invoking: " +
                    invocation.getClassName() +"->"+ invocation.getMethodName(),
                    e);
            throw new RuntimeException(e);
        }
    }

    private static Object executeInternal(String className,
                                          String methodName,
                                          Class<?>[] parameterTypes,
                                          Object[] args)
        throws Exception {

        /* take the service context */
        ServiceContextImpl serviceContext = (ServiceContextImpl)args[0];
        try {
            /* authenticate the request */
            /* invoke the method */
            Class<?> serviceClass = Class.forName(className);
            Method method = serviceClass.getMethod(methodName, parameterTypes);
            Object serviceObject = ServiceFactory.getService(serviceClass);
            /* invoke the method */
            return method.invoke(serviceObject, args);
        } finally {
            serviceContext.releaseResources();
        }
    }

}
