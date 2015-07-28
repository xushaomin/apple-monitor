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
package com.appleframework.jmx.core.alert.source;

import java.io.IOException;
import org.apache.log4j.Logger;

import com.appleframework.jmx.core.alert.AlertHandler;
import com.appleframework.jmx.core.alert.AlertSource;
import com.appleframework.jmx.core.config.AlertSourceConfig;
import com.appleframework.jmx.core.management.ServerConnection;
import com.appleframework.jmx.core.management.ServerConnector;
import com.appleframework.jmx.core.util.Loggers;

/**
 *
 * @author Rakesh Kalra
 */
public abstract class MBeanAlertSource implements AlertSource {

    private static final Logger logger = Loggers.getLogger(AlertSource.class);

    protected final AlertSourceConfig sourceConfig;
    protected AlertHandler handler;
    protected ServerConnection connection;
    protected String alertId;
    protected String alertName;
    private boolean stopThreads = false;


    public MBeanAlertSource(AlertSourceConfig sourceConfig){
        assert sourceConfig != null;
        this.sourceConfig = sourceConfig;
    }

    /** Called by the ConnectionMonitor thread, when the connection goes down*/
    private void connectionClosed(){
        try {
            this.unregisterInternal();
        } catch (Exception e) {
            logger.error("Error unregistering for monitoring. error=" + e.getMessage());
        }
        /* close the connection */
        closeConnection();

        /* need to re-establish the connection when the server comes up */
        new EstablishConnection().start();
    }

    private void closeConnection(){
        try {
           connection.close();
        } catch (IOException e) {
           logger.error("Error while closing connection. error: " + e.getMessage());
        }
    }

    private void connectionOpen(){
        try {
            /* start monitoring */
            registerInternal();
            logger.info("Registered for alert: " + alertName +
                    ", application: " + sourceConfig.getApplicationConfig().getName());
        } catch (Exception e) {
            /* something happen while registering for alert. There is no point
                retrying. Just log the error and continue */
            logger.error("Error registering alert: " + alertName, e);
            /* close the connection that was openend*/
            closeConnection();
            return;
        }
        /* start the connection monitoring thread */
        new ConnectionMonitor().start();
    }

    public void register(AlertHandler handler, String alertId, String alertName){
        assert this.handler == null;
        assert connection == null;

        this.handler = handler;
        this.alertId = alertId;
        this.alertName = alertName;

        /* need to establish the connection to the server*/
        new EstablishConnection().start();
    }

    public void unregister(){
        stopThreads = true;
        unregisterInternal();

        /* close the connection */
        try {
           connection.close();
        } catch (IOException e) {
           logger.error("Error while closing connection. error: " + e.getMessage());
        }

        logger.info("Un-registered for alert: " + alertName + ", application: " +
                    sourceConfig.getApplicationConfig().getName());

        connection = null;
        handler = null;
    }

    protected abstract void registerInternal();

    protected abstract void unregisterInternal();

    private class ConnectionMonitor extends Thread{

        public void run(){
            while(!stopThreads){
                if(!connection.isOpen()){
                    /* connection went down */
                    logger.warn("Connection lost to application: " + sourceConfig.getApplicationConfig().getName());
                    MBeanAlertSource.this.connectionClosed();
                    return;
                }
                try {
                    sleep(500);
                } catch (InterruptedException e) {}
            }
        }
    }

    private class EstablishConnection extends Thread{

        public void run(){
            while(!stopThreads){

                try {
                    /* get connection */
                    connection = ServerConnector.getServerConnection(sourceConfig.getApplicationConfig());
                    if(connection.isOpen()){
                        /* connection is now open */
                        logger.info("Established connection to application: " + sourceConfig.getApplicationConfig().getName());
                        MBeanAlertSource.this.connectionOpen();
                        return;
                    }
                } catch (Exception e) {
                    logger.info("Failed to establish connection to application: "
                            + sourceConfig.getApplicationConfig().getName() + ". message=" + e.getMessage());
                }

                try {
                    sleep(30 * 1000);
                } catch (InterruptedException e) {}
            }
        }
    }
}
