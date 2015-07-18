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
package com.appleframework.jmx.core.config.event;

import com.appleframework.jmx.core.config.ApplicationConfig;

/**
 * Used to communicate changes to the basic application configuration changes. 
 * The changes to application child elements such as Alerts, Dashboards, etc, 
 * do not result in this event.
 *  
 * @author rkalra
 */
public class ApplicationChangedEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    public ApplicationChangedEvent(ApplicationConfig config){
        super(config);
    }
}
  