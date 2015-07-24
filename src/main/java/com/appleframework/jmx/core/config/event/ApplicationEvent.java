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

import java.util.EventObject;

import com.appleframework.jmx.core.config.ApplicationConfig;

/**
 * Base class for Application related events.
 * 
 * @author rkalra
 */
public abstract class ApplicationEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private final long time;

	public ApplicationEvent(ApplicationConfig config) {
		this(config, System.currentTimeMillis());
	}

	public ApplicationEvent(ApplicationConfig config, long time) {
		super(config);
		this.time = time;
	}

	public ApplicationConfig getApplicationConfig() {
		return (ApplicationConfig) super.getSource();
	}

	public Long getTime() {
		return time;
	}
}
