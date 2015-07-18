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

import org.apache.commons.beanutils.ConvertUtils;
import com.appleframework.jmx.core.management.data.DataFormatUtil;

/**
 *
 * date:  Aug 13, 2004
 * @author	Rakesh Kalra
 */
public class ObjectAttribute implements java.io.Serializable {

	private static final long serialVersionUID = -8917759113886604463L;

	public static final int STATUS_OK = 0;
    public static final int STATUS_ERROR = 1;
    public static final int STATUS_NOT_FOUND = 2;

    private String name;
    private Object value;
    private int status = STATUS_OK;
    private String errorString;

    public ObjectAttribute(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public ObjectAttribute(String name, int status, String errorString){
        this.name = name;
        this.status = status;
        this.errorString = errorString;
    }

    public boolean equals(Object o) {
        if(o instanceof ObjectAttribute){
            ObjectAttribute attr = (ObjectAttribute)o;
            if(attr.name.equals(this.name) && attr.value.equals(this.value)){
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public int getStatus() {
        return status;
    }

    public String getErrorString() {
        return errorString;
    }

    public String getDisplayValue(){
        String attrValue = null;
        if(getStatus() == STATUS_OK){
            attrValue = DataFormatUtil.format(getValue());
        }else if(getStatus() == ObjectAttribute.STATUS_NOT_FOUND){
            // todo: this will not work for CLI
            attrValue = "&lt;not found&gt;";
        }else{
            // todo: this will not work for CLI
            attrValue = "&lt;error&gt;";
        }
        return attrValue;
    }
    
    public String getEditableValue(){
        String attrValue = null;
        if(getStatus() == STATUS_OK){
            attrValue = ConvertUtils.convert(getValue());
            if(attrValue == null){
                return "null";
            }
        }else if(getStatus() == ObjectAttribute.STATUS_NOT_FOUND){
            // todo: this will not work for CLI
            attrValue = "&lt;not found&gt;";
        }else{
            // todo: this will not work for CLI
            attrValue = "&lt;error&gt;";
        }
        return attrValue;
    }
}
