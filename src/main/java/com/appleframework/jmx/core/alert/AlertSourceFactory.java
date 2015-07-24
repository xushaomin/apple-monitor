/**
 * Copyright (c) 2004-2005 jManage.org
 *
 * This is a free software; you can redistribute it and/or
 * modify it under the terms of the license at
 * http://www.jmanage.org.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appleframework.jmx.core.alert;

import com.appleframework.jmx.core.config.AlertSourceConfig;
import com.appleframework.jmx.core.alert.source.*;

/**
 * 
 * Date: Jul 31, 2005
 * 
 * @author Rakesh Kalra
 */
public class AlertSourceFactory {

	public static AlertSource getAlertSource(AlertSourceConfig sourceConfig) {
		final String sourceType = sourceConfig.getSourceType();
		if (sourceType.equals(AlertSourceConfig.SOURCE_TYPE_NOTIFICATION)) {
			return new NotificationAlertSource(sourceConfig);
		} else if (sourceType.equals(AlertSourceConfig.SOURCE_TYPE_GAUGE_MONITOR)) {
			return new GaugeAlertSource(sourceConfig);
		} else if (sourceType.equals(AlertSourceConfig.SOURCE_TYPE_STRING_MONITOR)) {
			return new StringAlertSource(sourceConfig);
		} else if (sourceType.equals(AlertSourceConfig.SOURCE_TYPE_APPLICATION_DOWN)) {
			return new ApplicationDowntimeAlertSource(sourceConfig);
		}
		assert false : "Unknown alert source type: " + sourceType;
		return null;
	}
}
