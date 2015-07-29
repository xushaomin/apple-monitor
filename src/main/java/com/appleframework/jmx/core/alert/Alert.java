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
import com.appleframework.jmx.core.util.Loggers;

import java.util.List;
import org.apache.log4j.Logger;


/**
 *
 * Date:  Jul 4, 2005
 * @author	Rakesh Kalra
 */
public class Alert implements AlertHandler {

    private static Logger logger = Loggers.getLogger(Alert.class);

    private final AlertConfig alertConfig;
    private final AlertSource source;
    private final List<AlertDelivery> deliveries;

    public Alert(AlertConfig alertConfig){
        assert alertConfig != null;
        this.alertConfig = alertConfig;
        this.source = AlertSourceFactory.getAlertSource(alertConfig.getAlertSourceConfig());
        this.deliveries = AlertDeliveryFactory.getAlertDeliveries(alertConfig);
    }

    public void register(){
        source.register(this, alertConfig.getAlertId(), alertConfig.getAlertName());
    }

    public void unregister(){
        source.unregister();
    }

    public void handle(AlertInfo alertInfo) {
        alertInfo.setAlertConfig(this.alertConfig);
        for (AlertDelivery delivery : deliveries) {
        	try {
                delivery.deliver(alertInfo);
            } catch (Exception e) {
                logger.error( "Error while deliverying alert", e);
                // continue deliverying thru other modes (if any)
            }
		}
    }

    public int hashCode(){
        return alertConfig.getAlertId().hashCode();
    }

    public boolean equals(Object obj){
        if(obj != null && obj instanceof Alert){
            Alert alert = (Alert)obj;
            return alert.alertConfig.getAlertId().equals(alertConfig.getAlertId());
        }
        return false;
    }
}
