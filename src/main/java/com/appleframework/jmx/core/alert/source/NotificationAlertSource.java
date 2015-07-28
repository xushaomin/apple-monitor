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
package com.appleframework.jmx.core.alert.source;

import com.appleframework.jmx.core.config.AlertSourceConfig;
import com.appleframework.jmx.core.management.*;
import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.jmx.core.alert.AlertInfo;

import org.apache.log4j.Logger;

/**
 * 
 * Date: Jul 31, 2005
 * 
 * @author Rakesh Kalra
 */
public class NotificationAlertSource extends MBeanAlertSource {

	private static final Logger logger = Loggers.getLogger(NotificationAlertSource.class);

	private ObjectNotificationListener listener;
	private ObjectNotificationFilterSupport filter;

	public NotificationAlertSource(AlertSourceConfig sourceConfig) {
		super(sourceConfig);
	}

	public void registerInternal() {

		/* start looking for this notification */
		listener = new ObjectNotificationListener() {
			public void handleNotification(ObjectNotification notification, Object handback) {
				try {
					NotificationAlertSource.this.handler.handle(new AlertInfo(notification));
				} catch (Exception e) {
					logger.error("Error while handling alert", e);
				}
			}
		};
		filter = new ObjectNotificationFilterSupport();
		filter.enableType(sourceConfig.getNotificationType());
		connection.addNotificationListener(new ObjectName(sourceConfig.getObjectName()), listener, filter, null);
	}

	protected void unregisterInternal() {
		assert connection != null;
		try {
			/* remove notification listener */
			connection.removeNotificationListener(new ObjectName(sourceConfig.getObjectName()), listener, filter, null);
		} catch (Exception e) {
			logger.error("Error while Removing Notification Listener. error: " + e.getMessage());
		}
		listener = null;
		filter = null;
	}
}
