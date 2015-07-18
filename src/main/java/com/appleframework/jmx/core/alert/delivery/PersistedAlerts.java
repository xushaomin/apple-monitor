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

import com.appleframework.jmx.core.alert.AlertInfo;

import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.Collection;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.*;

/**
 *
 * <p>
 * Date:  Nov 6, 2005
 * @author	Rakesh Kalra
 */
public abstract class PersistedAlerts {

	// Alert Id to AlertInfo map
    private Map<String, AlertInfo> alerts = Collections.synchronizedMap(new HashMap<String, AlertInfo>());

    protected PersistedAlerts(){
        // read from file
        read();
    }

    public void add(AlertInfo alertInfo){
        Object prevValue = alerts.put(alertInfo.getAlertId(), alertInfo);
        assert prevValue == null;
        save();
    }

    public void remove(String alertId){
        alerts.remove(alertId);
        save();
    }

    public AlertInfo get(String alertId){
        return (AlertInfo)alerts.get(alertId);
    }

    public Collection<?> getAll(){
        return alerts.values();
    }

    public void removeAll(){
        alerts.clear();
        save();
    }

    /**
     * Removes the next element from the Map
     * @return next element if any, null otherwise
     */
    public AlertInfo remove(){
        AlertInfo alertInfo = null;
        synchronized(alerts){
            if(alerts.size() > 0){
                String alertId = (String)alerts.keySet().iterator().next();
                alertInfo = (AlertInfo)alerts.remove(alertId);
                save();
            }
        }
        return alertInfo;
    }

    protected abstract String getPersistedFileName();

    private void save(){
        try {
            XMLEncoder encoder = new XMLEncoder(
                    new BufferedOutputStream(new FileOutputStream(getPersistedFileName())));
            Map<String, AlertInfo> persistedAlerts = new HashMap<String, AlertInfo>();
            persistedAlerts.putAll(alerts);
            encoder.writeObject(persistedAlerts);
            encoder.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void read(){
        try {
            File file = new File(getPersistedFileName());
            if(file.exists()){
                XMLDecoder decoder = new XMLDecoder(
                                    new BufferedInputStream(
                                        new FileInputStream(getPersistedFileName())));
                Map<String, AlertInfo> persistedAlerts = (Map)decoder.readObject();
                alerts.putAll(persistedAlerts);
                decoder.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
