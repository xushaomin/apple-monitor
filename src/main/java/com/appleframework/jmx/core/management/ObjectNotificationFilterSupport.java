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
package com.appleframework.jmx.core.management;

import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

/**
 *
 * Date:  Jul 31, 2005
 * @author	Rakesh Kalra
 */
public class ObjectNotificationFilterSupport implements ObjectNotificationFilter{

    private Set<String> notificationTypes = new HashSet<String>();

    public void enableType(String type){
        assert type != null;
        notificationTypes.add(type);
    }

    /**
     * This method is called before a notification is sent to see whether
     * the listener wants the notification.
     *
     * @param notification the notification to be sent.
     * @return true if the listener wants the notification, false otherwise
     */
    public boolean isNotificationEnabled(ObjectNotification notification) {
        return notificationTypes.contains(notification.getType());
    }

    /**
     *
     * @return a list of notification types that are enabled.
     */
    public Collection<String> getEnabledTypes() {
        return notificationTypes;
    }
}
