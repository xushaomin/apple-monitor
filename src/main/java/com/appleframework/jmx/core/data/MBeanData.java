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
package com.appleframework.jmx.core.data;

/**
 *
 * date:  Feb 21, 2005
 * @author	Rakesh Kalra
 */
public class MBeanData implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
	
	private String name;
    /* configured name (if configured) */
    private String configuredName;

    public MBeanData(){}

    public MBeanData(String name){
        this(name, null);
    }

    public MBeanData(String objectName, String configuredName) {
        this.name = objectName;
        this.configuredName = configuredName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfiguredName() {
        return configuredName;
    }

    public void setConfiguredName(String configuredName) {
        this.configuredName = configuredName;
    }
}
