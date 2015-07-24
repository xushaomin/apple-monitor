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

import javax.management.MBeanRegistration;
import javax.management.ObjectName;
import javax.management.MBeanServer;

import com.appleframework.jmx.core.util.Loggers;

import java.util.Map;
import org.apache.log4j.Logger;

/**
 * This is the base class for all jmanage connector MBean.
 * 
 * @author	Tak-Sang Chan
 */
public abstract class ConnectorSupport implements MBeanRegistration {

    protected final Logger logger = Loggers.getLogger(this.getClass());

    protected MBeanServer mbeanServer;
    protected ObjectName objectName;

    public ObjectName preRegister(MBeanServer server, ObjectName name) throws Exception {
        this.mbeanServer = server; 
        this.objectName = name;        
        return name;        
    }

    public void postRegister(Boolean registrationDone) {
        if (registrationDone.booleanValue()) {
            logger.info( this.getClass().getName() + ": Registered MBean " + this.objectName);
        }
        else {
            logger.info( this.getClass().getName() + ": Failed to register MBean " + this.objectName);
        }
    }

    public void preDeregister() throws Exception {
    }

    public void postDeregister() {
    }

    @SuppressWarnings("rawtypes")
	public void initialize(Map configParam) {
        // default to do nothing
        // override this method to get the configuration values
    }
    
}
