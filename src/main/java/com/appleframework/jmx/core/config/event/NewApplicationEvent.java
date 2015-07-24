/**
 * Copyright (c) 2004-2006 jManage.org
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
package com.appleframework.jmx.core.config.event;

import com.appleframework.jmx.core.config.ApplicationConfig;

/**
 * This event is fired when a new application is added to the system.
 * 
 * @author rkalra
 */
public class NewApplicationEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public NewApplicationEvent(ApplicationConfig config) {
		super(config);
	}
}
