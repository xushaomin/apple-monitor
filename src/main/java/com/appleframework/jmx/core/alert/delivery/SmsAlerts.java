/**
 * Copyright (c) 2004-2005 jManage.org. All rights reserved.
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
package com.appleframework.jmx.core.alert.delivery;

import com.appleframework.jmx.core.util.CoreUtils;
import com.appleframework.jmx.core.alert.AlertInfo;

import java.io.File;

/**
 * Queue for Sms Alerts. This queue is used when jManage is not able to
 * deliver emails.
 * 
 * <p>
 * Date: Nov 6, 2005
 * 
 * @author Rakesh Kalra
 */
public class SmsAlerts extends PersistedAlerts {

	private static final String EMAIL_ALERTS_FILE = CoreUtils.getDataDir() + File.separator + "sms-alerts.xml";

	private static final SmsAlerts instance = new SmsAlerts();

	public static SmsAlerts getInstance() {
		return instance;
	}

	private SmsAlerts() {
		new SmsDeliveryThread().start();
	}

	protected String getPersistedFileName() {
		return EMAIL_ALERTS_FILE;
	}

	private class SmsDeliveryThread extends Thread {
		SmsDelivery delivery = new SmsDelivery();
		public void run() {
			while (true) {
				AlertInfo alertInfo = SmsAlerts.this.remove();
				if (alertInfo != null) {
					// todo: if deliver method can return boolean on success
					// we can change this to process all queued alerts
					delivery.deliver(alertInfo);
				}
				try {
					/* sleep for a minute */
					sleep(60 * 1000);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
