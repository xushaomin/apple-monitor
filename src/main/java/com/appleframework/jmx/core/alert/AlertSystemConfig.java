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

import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import com.appleframework.jmx.core.util.CoreUtils;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;

/**
 * Contains the configuration for the Alerts sub-system. The Alerts sub-system
 * can be configured using alert-config.xml file.
 *
 * Date:  Aug 2, 2005
 * @author	Rakesh Kalra
 */
public class AlertSystemConfig {

    private static final String ALERT_CONFIG_FILE = 
    		CoreUtils.getConfigDir() + File.separator +
            "system" + File.separator + "alert-config.xml";

    private static final AlertSystemConfig instance = new AlertSystemConfig();

    public static AlertSystemConfig getInstance(){
        return instance;
    }

    // list of DeliveryType objects
    private List<DeliveryType> deliveryTypes = new LinkedList<DeliveryType>();


    private AlertSystemConfig(){
        init();
    }

	private void init() {
		Document config = null;

		try {
			config = new SAXBuilder().build(ALERT_CONFIG_FILE);
		} catch (JDOMException e) {
			throw new RuntimeException();
		} catch (IOException e) {
			throw new RuntimeException();
		}

		Element deliveryTypesElement = config.getRootElement().getChild("deliveryTypes");
		assert deliveryTypesElement != null;
		for (Iterator<?> it = deliveryTypesElement.getChildren("delivery").iterator(); it.hasNext();) {
			Element deliveryType = (Element) it.next();
			deliveryTypes.add(new DeliveryType(deliveryType
					.getAttributeValue("type"), deliveryType
					.getAttributeValue("className")));
		}
	}

    public List<DeliveryType> getDeliveryTypes(){
        return deliveryTypes;
    }

    public static class DeliveryType {
    	
        private final String type;
        private final String className;
        public DeliveryType(String type, String className){
            this.type = type;
            this.className = className;
        }

        public String getType(){
            return type;
        }

        public String getClassName(){
            return className;
        }
    }
}
