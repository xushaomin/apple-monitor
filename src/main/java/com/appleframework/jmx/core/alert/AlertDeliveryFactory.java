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

import com.appleframework.jmx.core.config.AlertConfig;

import java.util.*;

/**
 * 
 * 
 * Date: Jul 31, 2005
 * 
 * @author Rakesh Kalra
 */
public class AlertDeliveryFactory {

	private static Map<String, Object> typeToAlertDeliveryMap = new HashMap<String, Object>();

	static {
		List<?> deliveryTypes = AlertSystemConfig.getInstance().getDeliveryTypes();
		for (Iterator<?> it = deliveryTypes.iterator(); it.hasNext();) {
			AlertSystemConfig.DeliveryType deliveryType = (AlertSystemConfig.DeliveryType) it.next();
			typeToAlertDeliveryMap.put(deliveryType.getType(), instantiate(deliveryType.getClassName()));
		}
	}

	private static AlertDelivery instantiate(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return (AlertDelivery) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static AlertDelivery getAlertDelivery(String deliveryType) {
		AlertDelivery delivery = (AlertDelivery) typeToAlertDeliveryMap.get(deliveryType);
		if (delivery == null) {
			throw new RuntimeException("Invalid deliveryType=" + deliveryType);
		}
		return delivery;
	}

	public static List<AlertDelivery> getAlertDeliveries(AlertConfig alertConfig) {
		String[] deliveryTypes = alertConfig.getAlertDelivery();
		List<AlertDelivery> deliveries = new ArrayList<AlertDelivery>(deliveryTypes.length);
		for (int i = 0; i < deliveryTypes.length; i++) {
			deliveries.add(getAlertDelivery(deliveryTypes[i]));
		}
		return deliveries;
	}
}
