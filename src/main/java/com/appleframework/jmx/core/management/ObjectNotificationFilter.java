/**
 * Copyright 2004-2005 jManage.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appleframework.jmx.core.management;

import java.util.Collection;

/**
 *
 * Date:  Jul 1, 2005
 * @author	Rakesh Kalra
 */
public interface ObjectNotificationFilter {

    /**
     * This method is called before a notification is sent to see whether
     * the listener wants the notification.
     *
     * @param notification the notification to be sent.
     * @return true if the listener wants the notification, false otherwise
     */
    public boolean isNotificationEnabled(ObjectNotification notification);

    /**
     *
     * @return a list of notification types that are enabled.
     */
    public Collection<String> getEnabledTypes();
}
