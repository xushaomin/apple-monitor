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
package com.appleframework.jmx.core.services.impl;

import com.appleframework.jmx.core.alert.delivery.ConsoleAlerts;
import com.appleframework.jmx.core.services.AlertService;
import com.appleframework.jmx.core.services.ServiceContext;
import com.appleframework.jmx.core.services.ServiceException;
import com.appleframework.jmx.core.alert.AlertInfo;

import java.util.*;

/**
 * 
 * Date: Aug 3, 2005
 * 
 * @author Rakesh Kalra
 */
public class AlertServiceImpl implements AlertService {

	public List<AlertInfo> getConsoleAlerts(ServiceContext context) throws ServiceException {

		List<AlertInfo> alerts = new LinkedList<AlertInfo>();
		// create a copy
		for (Iterator<?> it = ConsoleAlerts.getInstance().getAll().iterator(); it.hasNext();) {
			alerts.add((AlertInfo) it.next());
		}
		Collections.sort(alerts, new Comparator<AlertInfo>() {
			public int compare(AlertInfo info1, AlertInfo info2) {
				if (info1.getTimeStamp() == info2.getTimeStamp()) {
					return 0;
				} else if (info1.getTimeStamp() < info2.getTimeStamp()) {
					return -1;
				} else {
					return 1;
				}
			}
		});

		return alerts;
	}

	public void removeConsoleAlert(ServiceContext context, String alertId) {
		ConsoleAlerts.getInstance().remove(alertId);
	}
}
