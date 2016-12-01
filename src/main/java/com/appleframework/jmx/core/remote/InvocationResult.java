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
package com.appleframework.jmx.core.remote;

import java.io.Serializable;

/**
 *
 * date: Feb 24, 2005
 * 
 * @author Rakesh Kalra
 */
public class InvocationResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private Object result;
	private Throwable t;

	public InvocationResult(Object result) {
		this.result = result;
	}

	public InvocationResult(Throwable t) {
		assert t != null;
		this.t = t;
	}

	public Object get() {
		if (t != null) {
			return t;
		} else {
			return result;
		}
	}

}
