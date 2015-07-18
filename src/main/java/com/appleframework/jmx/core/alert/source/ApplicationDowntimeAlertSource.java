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

import java.util.EventObject;

import com.appleframework.jmx.core.alert.AlertHandler;
import com.appleframework.jmx.core.alert.AlertInfo;
import com.appleframework.jmx.core.alert.AlertSource;
import com.appleframework.jmx.core.config.AlertSourceConfig;
import com.appleframework.jmx.event.EventListener;
import com.appleframework.jmx.event.EventSystem;
import com.appleframework.jmx.monitoring.downtime.event.ApplicationDownEvent;

/**
 *
 * @author Rakesh Kalra
 */
public class ApplicationDowntimeAlertSource implements AlertSource{

    private AlertHandler handler;
    private final AlertSourceConfig sourceConfig;
    
    public ApplicationDowntimeAlertSource(AlertSourceConfig sourceConfig){
        assert sourceConfig != null;
        this.sourceConfig = sourceConfig;
    }
    
    public void register(AlertHandler handler, String alertId, String alertName) {
        this.handler = handler;
        EventSystem.getInstance().addListener(new ApplicationDowntimeEventListener(), ApplicationDownEvent.class);
    }

    public void unregister() {
        this.handler = null;
    }
    
    private class ApplicationDowntimeEventListener implements EventListener {
        public void handleEvent(EventObject event) {
            if(event instanceof ApplicationDownEvent &&
                    sourceConfig.getApplicationConfig().equals(
                            ((ApplicationDownEvent)event).getApplicationConfig())){
                handler.handle(new AlertInfo());
            }
        }
    }
}
