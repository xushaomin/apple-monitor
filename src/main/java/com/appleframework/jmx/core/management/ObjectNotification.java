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

/**
 * Contains information about the notification sent by the application.
 *
 * Date:  Jul 1, 2005
 * @author	Rakesh Kalra
 */
public class ObjectNotification {

    /**
     * The notification type
     */
    private String type = null;

    /**
     * The sequence number of the notification
     */
    private long sequenceNumber = 0;

    /**
     * The message of the notification
     */
    private String message = null;

    /**
     * The time of the notification
     */
    private long timeStamp;

    /**
     * The user data of the notification
     */
    private Object userData = null;

    /**
     * The source of the notification
     */
    private Object source = null;


    public ObjectNotification(String type, Object source, long sequenceNumber, long timeStamp, 
    		String message, Object userData) {
		this.type = type;
		this.source = source;
		this.sequenceNumber = sequenceNumber;
		this.timeStamp = timeStamp;
		if (message == null || message.length() == 0) {
			this.message = type;
		} else {
			this.message = message;
		}

		this.userData = userData;
    }

    public String getType() {
        return type;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public String getMessage() {
        return message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public Object getUserData() {
        return userData;
    }

    public Object getMySource() {
        return source;
    }
}
