/**
 * Copyright 2004-2006 jManage.org
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
package com.appleframework.jmx.event;

import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

import com.appleframework.jmx.core.util.Loggers;

/**
 * EventSystem is the central point where all events are recieved and distributed to the listeners.
 * 
 * @author rkalra
 */
public class EventSystem {

    private static final Logger logger = Loggers.getLogger(EventSystem.class);
    
    private static final EventSystem system = new EventSystem();

    public static EventSystem getInstance() {
        return system;
    }

    private final List<EventListenerWrapper> eventListenerWrappers = new LinkedList<EventListenerWrapper>();

    public void addListener(EventListener listener, Class<? extends EventObject> eventObjectType) {
        eventListenerWrappers.add(new EventListenerWrapper(listener, eventObjectType));
    }

	public void fireEvent(EventObject eventObject) {
		logger.info("Event fired: " + eventObject.toString());
		for (EventListenerWrapper wrapper : eventListenerWrappers) {
			if (wrapper.matchesEventObjectType(eventObject.getClass())) {
				try {
					wrapper.getEventListener().handleEvent(eventObject);
				} catch (Throwable t) {
					logger.error("Error in event listener", t);
				}
			}
		}
	}

    private static class EventListenerWrapper {
    	
        private EventListener eventListener;
        private Class<? extends EventObject> eventObjectType;

        EventListenerWrapper(EventListener eventListener, Class<? extends EventObject> eventObjectType) {
            this.eventListener = eventListener;
            this.eventObjectType = eventObjectType;
        }

        EventListener getEventListener() {
            return eventListener;
        }

        /**
         * @return true if this.eventObjectType is null or this.eventObjectType is same as or
         *         superclass of eventObjectType
         */
        boolean matchesEventObjectType(Class<? extends EventObject> eventObjectType) {
            return this.eventObjectType == null || this.eventObjectType.isAssignableFrom(eventObjectType);
        }
    }
}
