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
package com.appleframework.jmx.core.modules.jsr160;


import javax.management.remote.JMXServiceURL;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;

import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.management.ConnectionFailedException;
import com.appleframework.jmx.core.management.ServerConnection;
import com.appleframework.jmx.core.management.ServerConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * date:  Aug 12, 2004
 * @author	Rakesh Kalra
 */
public class JSR160ServerConnectionFactory implements ServerConnectionFactory {

    /**
     * @return  instance of ServerConnection corresponding to this jsr160
     *              module.
     */
    public ServerConnection getServerConnection(ApplicationConfig config) throws ConnectionFailedException {
        try {
            /* Create an RMI connector client */
            HashMap<String, Object> env = new HashMap<String, Object>();
            String[] credentials = new String[] {config.getUsername(), config.getPassword()};
            env.put("jmx.remote.credentials", credentials);

            Map<String, String> params = config.getParamValues();
            final String jndiFactory = (String)params.get(JSR160ApplicationConfig.JNDI_FACTORY);
            final String jndiURL = (String)params.get(JSR160ApplicationConfig.JNDI_URL);

            if(jndiFactory != null)
                env.put(JSR160ApplicationConfig.JNDI_FACTORY, jndiFactory);
            if(jndiURL != null)
                env.put(JSR160ApplicationConfig.JNDI_URL, jndiURL);

            JMXServiceURL url = new JMXServiceURL(config.getURL());
            JMXConnector jmxc = JMXConnectorFactory.connect(url, env);
            return new JSR160ServerConnection(jmxc, jmxc.getMBeanServerConnection());
        } catch (Throwable e) {
            throw new ConnectionFailedException(e);
        }
    }
}
