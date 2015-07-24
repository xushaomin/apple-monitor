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

import java.util.List;

import com.appleframework.jmx.core.management.ObjectAttribute;

/**
 *
 * Date:  Mar 15, 2005
 * @author	Rakesh Kalra
 */
public class AttributeListData implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
	
	private final String appName;
    private final List<ObjectAttribute> attributeList;
    private boolean error = false;

    public AttributeListData(String appName){
        this(appName, null);
        error = true;
    }

    public AttributeListData(String appName, List<ObjectAttribute> attributeList){
        this.appName = appName;
        this.attributeList = attributeList;
    }

    public String getAppName() {
        return appName;
    }

    public List<ObjectAttribute> getAttributeList() {
        return attributeList;
    }

    public boolean isError(){
        return error;
    }
}
