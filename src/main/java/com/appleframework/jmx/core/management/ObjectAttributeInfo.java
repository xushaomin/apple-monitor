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

import com.appleframework.jmx.core.management.metadata.ExpressionProcessor;

/**
 *
 * date:  Aug 13, 2004
 * @author	Rakesh Kalra
 */
public class ObjectAttributeInfo extends ObjectFeatureInfo {

	private static final long serialVersionUID = 5807944864795458052L;

	private String attributeType;
    private boolean isWrite;
    private boolean isRead;
    private boolean isIs;
    private String units;

    public ObjectAttributeInfo(String name,
                               String description,
                               String attributeType,
                               boolean isWrite,
                               boolean isRead,
                               boolean isIs) {
        super(name, description);
        this.attributeType = attributeType;
        this.isWrite = isWrite;
        this.isRead = isRead;
        this.isIs = isIs;
    }

    public String getType() {
        return attributeType;
    }

    public String getDisplayType(){
        return getDisplayType(attributeType);
    }

    public boolean isIs() {
        return isIs;
    }

    public boolean isReadable() {
        return isRead;
    }

    public boolean isWritable() {
        return isWrite;
    }

    public String getReadWrite(){
        String readWrite = "";
        if(isReadable()){
            readWrite += "R";
        }
        if(isWritable()){
            readWrite += "W";
        }
        return readWrite;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getUnits() {
        return units;
    }
    
    public boolean equals(Object obj){
        if(obj instanceof ObjectAttributeInfo){
            return name.equals(((ObjectAttributeInfo)obj).name);
        }
        return false;
    }
    
    public int hashCode(){
        return name.hashCode();
    }

    public void applyMetaData(ObjectAttributeInfo metaAttributeInfo, ExpressionProcessor exprProcessor) {
        if(metaAttributeInfo.getDescription() != null){
            description = metaAttributeInfo.getDescription();
        }
        if(metaAttributeInfo.getUnits() != null){
            units = metaAttributeInfo.getUnits();
        }
    }
}