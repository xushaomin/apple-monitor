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

import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.appleframework.jmx.database.entity.AppDowntimeEntity;
import com.appleframework.jmx.database.entity.AppDowntimeHistoryEntity;
import com.appleframework.jmx.database.service.AppDowntimeHistoryService;
import com.appleframework.jmx.database.service.AppDowntimeService;
import com.appleframework.jmx.event.EventListener;
import com.appleframework.jmx.monitoring.downtime.event.ApplicationDownEvent;
import com.appleframework.jmx.monitoring.downtime.event.ApplicationUpEvent;

/**
 * Records application downtime in the database.
 * 
 * TODO: This reads all applications from the DB. This should be changed to just read the
 * current applications - rk
 * 
 * 
 * @author Rakesh Kalra
 */
@Service("downtimeRecorder")
public class DowntimeRecorder implements EventListener {

    private static final Logger logger = Loggers.getLogger(DowntimeRecorder.class);
    
    private final Map<ApplicationConfig, ApplicationDowntimeHistory> downtimesMap = 
        new HashMap<ApplicationConfig, ApplicationDowntimeHistory>();
    
    @Resource
    private AppDowntimeService appDowntimeService;
    
    @Resource
    private AppDowntimeHistoryService appDowntimeHistoryService;
    
	public void init() {
        // load applications from DB
        initDowntimeMapFromDB();
        // now add new applications to the DB
        final long recordingSince = System.currentTimeMillis();
        
        List<ApplicationConfig> allApplications = ApplicationConfigManager.getAllApplications();
        for (ApplicationConfig appConfig : allApplications) {
            if(!downtimesMap.containsKey(appConfig)){
            	try {
            		addApplicationToDB(appConfig.getApplicationId(), recordingSince);
				} catch (Exception e) {
					logger.error(e);
				}
                downtimesMap.put(appConfig, new ApplicationDowntimeHistory(recordingSince));    
            }
        }
    }
    
    public boolean isApplicationUp(ApplicationConfig appConfig){
        if(appConfig == null){
            throw new NullPointerException("missing appConfig");
        }
        ApplicationDowntimeHistory history = getDowntimeHistory(appConfig);
        assert history != null;
        return history.isApplicationUp();
    }
    
    public ApplicationDowntimeHistory getDowntimeHistory(ApplicationConfig appConfig){
        if(appConfig == null){
            throw new NullPointerException("appConfig must be specified");
        }
        ApplicationDowntimeHistory history = downtimesMap.get(appConfig);
        assert history != null;
        return history;
    }

    private void addApplication(NewApplicationEvent event) {
        ApplicationConfig appConfig = event.getApplicationConfig();
      	logger.info("Added application " + appConfig.getName() + " to the downtimesMap.");
        
        final long recordingSince = event.getTime();
        addApplicationToDB(appConfig.getApplicationId(), recordingSince);
        downtimesMap.put(appConfig, new ApplicationDowntimeHistory(recordingSince));
    }
    
    private void updateApplication(ApplicationChangedEvent event) {
        ApplicationConfig appConfig = event.getApplicationConfig();
      	logger.info("Added application " + appConfig.getName() + " to the downtimesMap.");
        
        final long recordingSince = event.getTime();
        try {
            addApplicationToDB(appConfig.getApplicationId(), recordingSince);
		} catch (Exception e) {
			logger.error(e);
		}
        downtimesMap.put(appConfig, new ApplicationDowntimeHistory(recordingSince));
    }
    
    private void removeApplication(ApplicationRemovedEvent event) {
        ApplicationConfig appConfig = event.getApplicationConfig();
      	logger.info("removed application " + appConfig.getName() + " to the downtimesMap.");
        downtimesMap.remove(appConfig);
    }

    public void handleEvent(EventObject event) {
        if(!(event instanceof ApplicationEvent)){
            throw new IllegalArgumentException("event must of type ApplicationEvent");
        }
        // Handle new application event
        if(event instanceof NewApplicationEvent){
            addApplication((NewApplicationEvent)event);
            return;
        }
        
        if(event instanceof ApplicationChangedEvent){
            updateApplication((ApplicationChangedEvent)event);
            return;
        }
        
        if(event instanceof ApplicationRemovedEvent){
            removeApplication((ApplicationRemovedEvent)event);
            return;
        }
        // Note that application removed event is not being handled. We probably don't care about
        //   the downtime of a removed application.
        
        ApplicationEvent appEvent = (ApplicationEvent)event;
        ApplicationDowntimeHistory downtimeHistory = getDowntimeHistory(appEvent.getApplicationConfig());
        assert downtimeHistory != null;
        
        ApplicationConfig applicationConfig = appEvent.getApplicationConfig();
        //处理UP和DOWN事件
        if(appEvent instanceof ApplicationUpEvent){
            // application must have went down earlier
        	assert downtimeHistory.getDowntimeBegin() != null;
            downtimeHistory.applicationCameUp(appEvent.getTime());
        } else if(event instanceof ApplicationDownEvent){
            downtimeHistory.applicationWentDown(appEvent.getTime());
            // log the downtime to the db
            recordDowntimeHistory(applicationConfig.getApplicationId(), downtimeHistory.getDowntimeBegin(), appEvent.getTime());
        }
        updateDowntimeMap(applicationConfig, downtimeHistory);
        
        recordDowntime(applicationConfig.getApplicationId(), downtimeHistory.getRecordingSince(), downtimeHistory.isDown());
    }

    public double getUnavailablePercentage(ApplicationConfig appConfig) {
        final ApplicationDowntimeHistory history = getDowntimeHistory(appConfig);
        return history.getUnavailablePercentage();
    }
    
    private void addApplicationToDB(String applicationId, long recordingSince){
    	Integer id = Integer.parseInt(applicationId);
    	appDowntimeService.saveOrUpdate(id, recordingSince);
    }

    private void recordDowntimeHistory(String applicationId, long downtimeBegin, long downtimeEnd){
    	Integer id = Integer.parseInt(applicationId);
    	appDowntimeHistoryService.saveOrUpdate(id, downtimeBegin, downtimeEnd);
    }
    
    private void recordDowntime(String applicationId, long downtimeBegin, boolean isDown){
    	Integer id = Integer.parseInt(applicationId);
    	appDowntimeService.saveOrUpdate(id, downtimeBegin, isDown);
    }

    private void initDowntimeMapFromDB(){
        try{
            List<AppDowntimeEntity> list = appDowntimeService.findAll();
            for (AppDowntimeEntity appDowntime : list) {
            	String applicationId = appDowntime.getId().toString();
                long recordingSince = appDowntime.getRecordingStart().getTime();
                
                ApplicationConfig appConfig = 
                        ApplicationConfigManager.getApplicationConfig(applicationId);
                    if(appConfig == null){
                        logger.info( "Application with Id " + applicationId + " no longer exists");
                        continue;
                    }
                    ApplicationDowntimeHistory history = new ApplicationDowntimeHistory(recordingSince);
                    history.setTotalDowntime(getTotalDowntime(applicationId));
                    downtimesMap.put(appConfig, history);
			}
           
        } catch (Exception e) {
        	logger.error(e);
		}
    }
    
    private void updateDowntimeMap(ApplicationConfig appConfig, ApplicationDowntimeHistory history){
        try{
        	downtimesMap.put(appConfig, history);
        } catch (Exception e) {
        	logger.error(e);
		}
    }

    
    private long getTotalDowntime(String applicationId) {
        try {
        	List<AppDowntimeHistoryEntity> historyList = appDowntimeHistoryService.findListByAppId(Integer.parseInt(applicationId));
            long totalDowntime = 0;
            for (AppDowntimeHistoryEntity history : historyList) {
                Date startTime = history.getStartTime();
                Date endTime = history.getEndTime();
                if(endTime == null){
                    //TODO: we don't take into account the time when jmanage didn't do any recording
                    //    this should be recorded as different time.
                    logger.warn("No end time found for applicationId:" + applicationId 
                            + " startTime:" + startTime 
                            + ". This record has been ignored");
                }
                totalDowntime += (endTime.getTime() - startTime.getTime());
            }
            return totalDowntime;
        } catch(Exception e){    
            throw new RuntimeException(e);
        }
    }

	public Map<ApplicationConfig, ApplicationDowntimeHistory> getDowntimesMap() {
		return downtimesMap;
	}
    
}