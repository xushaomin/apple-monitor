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
package com.appleframework.jmx.webui.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.monitoring.downtime.ApplicationDowntimeHistory;
import com.appleframework.jmx.monitoring.downtime.ApplicationDowntimeService;
import com.appleframework.jmx.monitoring.downtime.DowntimeRecorder;

/**
 * 
 * @author Rakesh Kalra
 */
@Repository("applicationViewHelper")
public class ApplicationViewHelper {
    
    private static final SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa"); 
    
    @Resource
    private ApplicationDowntimeService applicationDowntimeService;
    
    @Resource
    private DowntimeRecorder downtimeRecorder;

    public boolean isApplicationUp(ApplicationConfig appConfig) {
        boolean isUp = true;
        if (appConfig.isCluster()) {
            for (ApplicationConfig childAppConfig : appConfig.getApplications()) {
                if (!downtimeRecorder.isApplicationUp(childAppConfig)) {
                    // once an application is detected that is down, there is no need to proceed further
                    isUp = false;
                    break;
                }
            }
        }
        else {
            isUp = downtimeRecorder.isApplicationUp(appConfig);
        }
        return isUp;
    }
    
    public String getRecordingSince(ApplicationConfig appConfig){
        ApplicationDowntimeHistory history = downtimeRecorder.getDowntimeHistory(appConfig);
        return formatter.format(new Date(history.getRecordingSince()));
    }
}
