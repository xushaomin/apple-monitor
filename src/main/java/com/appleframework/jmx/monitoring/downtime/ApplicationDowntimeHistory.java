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

import org.apache.log4j.Logger;

import com.appleframework.jmx.core.util.Loggers;

/**
 * 
 * @author Rakesh Kalra
 */
public class ApplicationDowntimeHistory {

	private static final Logger logger = Loggers.getLogger(ApplicationDowntimeHistory.class);

	private final long recordingSince;
	private long totalDowntime;
	// if not null - indicates that the application is down and stores the time
	// since the application is down.
	private Long downtimeBegin;

	ApplicationDowntimeHistory(long recordingSince) {
		this.recordingSince = recordingSince;
	}

	public void setTotalDowntime(long totalDowntime) {
		this.totalDowntime = totalDowntime;
	}

	public void applicationWentDown(long time) {
		if (downtimeBegin == null) {
			downtimeBegin = time;
		} else {
			// this could happen if the application is edited while it is down.
			logger.info("Downtime event fired again. Ignoring.");
		}
	}

	public void applicationCameUp(long time) {
		assert downtimeBegin != null;
		totalDowntime += (time - downtimeBegin);
		downtimeBegin = null;
	}

	public Long getDowntimeBegin() {
		return downtimeBegin;
	}

	public double getUnavailablePercentage() {
		final long currentTime = System.currentTimeMillis();
		final long totalRecordingTime = currentTime - recordingSince;
		long currentTotalDowntime = totalDowntime;
		if (downtimeBegin != null) {
			currentTotalDowntime += (currentTime - downtimeBegin);
		}
		return (currentTotalDowntime * 100.0d) / totalRecordingTime;
	}

	public long getRecordingSince() {
		return recordingSince;
	}

	public boolean isApplicationUp() {
		return downtimeBegin == null;
	}
}
