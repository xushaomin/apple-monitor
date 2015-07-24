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
package com.appleframework.jmx.core.alert.delivery;

import com.appleframework.jmx.core.util.CoreUtils;

import java.io.*;

/**
 * 
 * Date: Aug 2, 2005
 * 
 * @author Rakesh Kalra
 */
public class ConsoleAlerts extends PersistedAlerts {

	private static final String CONSOLE_ALERTS_FILE = CoreUtils.getDataDir() + File.separator + "console-alerts.xml";

	private static final ConsoleAlerts instance = new ConsoleAlerts();

	public static ConsoleAlerts getInstance() {
		return instance;
	}

	private ConsoleAlerts() {
	}

	protected String getPersistedFileName() {
		return CONSOLE_ALERTS_FILE;
	}
}
