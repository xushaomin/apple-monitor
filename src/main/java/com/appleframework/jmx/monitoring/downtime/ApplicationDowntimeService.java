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

import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.config.ApplicationConfigManager;
import com.appleframework.jmx.core.config.event.ApplicationChangedEvent;
import com.appleframework.jmx.core.config.event.ApplicationEvent;
import com.appleframework.jmx.core.config.event.ApplicationRemovedEvent;
import com.appleframework.jmx.core.config.event.NewApplicationEvent;
import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.jmx.event.EventListener;
import com.appleframework.jmx.event.EventSystem;

/**
 * This service acts as the facade to the downtime tracking sub-system in
 * jManage. It exposes start() and stop() methods to manage it's lifecycle.
 * 
 * @author Rakesh Kalra
 */
@Service("applicationDowntimeService")
public class ApplicationDowntimeService {

    private static final Logger logger = Loggers.getLogger(ApplicationDowntimeService.class);
        
    private final List<ApplicationHeartBeatThread> threads = new LinkedList<ApplicationHeartBeatThread>();
    
    @Resource
    private DowntimeRecorder downtimeRecorder;
    
    @PostConstruct
	public void start() {
        for (ApplicationConfig appConfig : ApplicationConfigManager.getAllApplications()) {
            // only add non-cluster applications
            if(!appConfig.isCluster())
                addApplication(appConfig);
        }
        
        // TODO: perfect dependency to be injected via Spring framework --rk
        EventSystem eventSystem = EventSystem.getInstance();
         
        /* Add the recorder to record the downtimes to the DB */
        eventSystem.addListener(downtimeRecorder, ApplicationEvent.class);

        /* application event listener to add */
		eventSystem.addListener(new EventListener() {
			public void handleEvent(EventObject event) {
				if (!(event instanceof ApplicationEvent)) {
					throw new IllegalArgumentException("event must be of type ApplicationEvent");
				}
				if (event instanceof NewApplicationEvent) {
					addApplication((NewApplicationEvent) event);
				} else if (event instanceof ApplicationChangedEvent) {
					applicationChanged((ApplicationChangedEvent) event);
				} else if (event instanceof ApplicationRemovedEvent) {
					removeApplication(((ApplicationRemovedEvent) event).getApplicationConfig());
				}
			}
		}, ApplicationEvent.class);
        
        logger.info("ApplicationDowntimeService started.");
    }

    public void stop() {
        for(ApplicationHeartBeatThread thread: threads){
            thread.end();
        }
        threads.clear();
    }

    public DowntimeRecorder getDowntimeRecorder(){
        return downtimeRecorder;
    }
    
    public void addOrUpdateApplication(ApplicationConfig appConfig) {
    	boolean isExistThread = false;
		for (ApplicationHeartBeatThread thread : threads) {
			if (thread.getApplicationConfig().getAppId().equals(appConfig.getAppId())) {
				isExistThread = true;
				break;
			}
		}
		//应用已经存在
		if(isExistThread == true) {
			removeApplication(appConfig);
			addApplication(appConfig);
			
			ApplicationChangedEvent event = new ApplicationChangedEvent(appConfig);
			applicationChanged(event);
		}
		else {
			addApplication(appConfig);
			
			//事件通知
			ApplicationEvent event = new NewApplicationEvent(appConfig);
			addApplication(event);
		}
    }
    
    private void addApplication(ApplicationConfig appConfig) {
        ApplicationHeartBeatThread thread = new ApplicationHeartBeatThread(appConfig);
        threads.add(thread);
        thread.start();
    }

    private void addApplication(ApplicationEvent appEvent) {
        ApplicationHeartBeatThread thread = new ApplicationHeartBeatThread(appEvent);
        threads.add(thread);
        thread.start();
    }
    
    private void applicationChanged(ApplicationEvent appEvent) {
        removeApplication(appEvent.getApplicationConfig());
        addApplication(appEvent);
    }
    
    private void removeApplication(ApplicationConfig appConfig) {
		ApplicationHeartBeatThread associatedThread = null;
		for (ApplicationHeartBeatThread thread : threads) {
			if (thread.getApplicationConfig().getAppId().equals(appConfig.getAppId())) {
				associatedThread = thread;
				break;
			}
		}
		if (associatedThread == null) {
			logger.warn("Thread not found for application: " + appConfig);
		} else {
			// end the thread
			associatedThread.end();
			// remove it from the list
			threads.remove(associatedThread);
		}
    }

	public List<ApplicationHeartBeatThread> getThreads() {
		return threads;
	}
    
}
