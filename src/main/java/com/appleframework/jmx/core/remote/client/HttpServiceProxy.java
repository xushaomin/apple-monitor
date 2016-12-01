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
package com.appleframework.jmx.core.remote.client;

import com.appleframework.jmx.core.util.JManageProperties;
import com.appleframework.jmx.core.remote.InvocationResult;
import com.appleframework.jmx.core.remote.RemoteInvocation;
import com.appleframework.jmx.core.services.ServiceException;
import com.appleframework.jmx.core.util.ErrorCodes;
import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.jmx.core.util.CoreUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;


/**
 * Acts as a Proxy for Service layer method calls. This is used by the
 * ServiceFactory when the factory runs in the Remote mode.
 *
 * @see com.appleframework.jmx.core.remote.server.ServiceCallHandler
 *
 * date:  Feb 24, 2005
 * @author	Rakesh Kalra
 */
public class HttpServiceProxy implements InvocationHandler {

    private static final Logger logger = Loggers.getLogger(HttpServiceProxy.class);

    private static String REQUEST_CONTENT_TYPE = "application/x-java-serialized-object; class=com.appleframework.jmx.core.remote.RemoteInvocation";

    private static URL remoteURL;

    static{
        /* set the default remote URL from jmanage.properties */
        if(JManageProperties.getJManageURL() != null)
            setRemoteURL(JManageProperties.getJManageURL());

        /* do we need to ignore bad cert (in case we are using SSL for remove
            invocation */
        if(JManageProperties.isIgnoreBadSSLCertificate()){
            System.setProperty("javax.net.ssl.trustStore", CoreUtils.getConfigDir() + "/cacerts");
            System.setProperty("javax.net.ssl.trustStorePassword", JManageProperties.getSSLTrustStorePassword());

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
            	@SuppressWarnings("unused")
				public boolean verify(String s, String s1) {
                    return true;
                }

                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
        }
    }

    public static void setRemoteURL(String remoteURL) {
        try {
            HttpServiceProxy.remoteURL = new URL(remoteURL + "/invokeService");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("Invoking service method: " + method.getDeclaringClass().getName() + ":" + method.getName());
        try {
            RemoteInvocation invocation = new RemoteInvocation(method, args);
            return invoke(invocation);
        } catch (ConnectException e) {
            throw new ServiceException(ErrorCodes.JMANAGE_SERVER_CONNECTION_FAILED, JManageProperties.getJManageURL());
        }
    }

    private Object invoke(RemoteInvocation invocation) throws Exception {

        HttpURLConnection conn = (HttpURLConnection) remoteURL.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("ContentType", REQUEST_CONTENT_TYPE);
        conn.setRequestMethod("POST");
        OutputStream os = conn.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        try {
            oos.writeObject(invocation);
            oos.flush();
        } catch (ObjectStreamException e) {
            throw new RuntimeException(e);
        }

        /* read the response */
        InputStream is = conn.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        InvocationResult result = (InvocationResult) ois.readObject();

        ois.read(); // jsse connection pooling hack
        ois.close();
        oos.close();

        Object output = result.get();
        if(output instanceof Exception){
            throw (Exception)output;
        }
        return output;
    }
}
