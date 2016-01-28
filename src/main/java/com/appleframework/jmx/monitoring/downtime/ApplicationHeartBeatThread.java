/**
 * Copyright 2004-2006 jManage.org
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
package com.appleframework.jmx.monitoring.downtime;

import java.io.IOException;
import org.apache.log4j.Logger;

import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.config.event.ApplicationEvent;
import com.appleframework.jmx.core.management.ServerConnection;
import com.appleframework.jmx.core.management.ServerConnector;
import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.jmx.event.EventSystem;
import com.appleframework.jmx.monitoring.downtime.event.ApplicationDownEvent;
import com.appleframework.jmx.monitoring.downtime.event.ApplicationUpEvent;

/**
 * ApplicationHeartBeatThread tracks downtime of a single application.
 * This information is captured as a list of ApplicationDowntime objects.
 * 
 * @author Rakesh Kalra
 */
public class ApplicationHeartBeatThread extends Thread {

    private static final Logger logger = Loggers.getLogger(ApplicationHeartBeatThread.class);

    private final ApplicationConfig appConfig;

    private boolean end = false;
    
    private boolean wasOpen = true;


    protected ApplicationHeartBeatThread(ApplicationConfig appConfig) {
        super("ApplicationHeartBeatThread:" + appConfig.getName());
        this.appConfig = appConfig;
    }

    protected ApplicationHeartBeatThread(ApplicationEvent appEvent) {
        this(appEvent.getApplicationConfig());
        /* check if the application is up */
        wasOpen = isOpen();
        if(!wasOpen){
            EventSystem.getInstance().fireEvent(new ApplicationDownEvent(appConfig, appEvent.getTime()));    
        }
    }
    
    @Override
    public void run() {
        logger.debug("Thread started: " + this.getName());
        while (!end) {
            checkApplicationStatus();
            try {
                sleep(60000);
            } catch (InterruptedException e) {
                logger.warn("InterruptedException: " + e.getMessage());
            }
        }
        logger.debug("Thread finished: " + this.getName());
    }

    protected void end() {
        end = true;
    }

    private void checkApplicationStatus() {
        final boolean isOpen = isOpen();
        if(wasOpen && !isOpen){
            // application went down
            wasOpen = false;
            // application went down
            EventSystem.getInstance().fireEvent(new ApplicationDownEvent(appConfig));
        } else if(!wasOpen && isOpen){
            // application came pack up
            wasOpen = true;
            // application came pack up
            EventSystem.getInstance().fireEvent(new ApplicationUpEvent(appConfig));
        }
    }
    
    private boolean isOpen(){
        ServerConnection connection = null;
        try {
            connection = ServerConnector.getServerConnection(appConfig);
            return connection.isOpen();
        }catch(Exception e){
            logger.info("Application is down: " + appConfig.getName() + " at " + appConfig.getHost() + ":" + appConfig.getPort());
            return false;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (IOException e) {
                logger.warn(e.getMessage());
            }
        }
    }

    public ApplicationConfig getApplicationConfig() {
        return appConfig;
    }
}
