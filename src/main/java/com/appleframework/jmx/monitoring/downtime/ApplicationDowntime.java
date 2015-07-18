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

/**
 * An instance of ApplicationDowntime stores a time-period when the application
 * went down. This information is persisted in the database.
 * 
 * @author Rakesh Kalra
 */
public class ApplicationDowntime {

    private Long startTime;

    private Long endTime;

    public Long getEndTime() {
        return endTime;
    }

    protected void setEndTime(Long endTime) {
        assert endTime.longValue() >= startTime.longValue();
        this.endTime = endTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    protected void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public long getTime() {
        assert getStartTime() != null;
        if(getEndTime() == null){
            return System.currentTimeMillis() - getStartTime(); 
        }
        return getEndTime() - getStartTime();
    }
}
