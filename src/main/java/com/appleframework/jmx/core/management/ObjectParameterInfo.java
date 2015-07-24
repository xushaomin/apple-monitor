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

import java.lang.reflect.Array;
import java.util.Set;

import com.appleframework.jmx.core.management.metadata.ExpressionProcessor;
import com.appleframework.jmx.core.util.StringUtils;

/**
 *
 * date:  Aug 13, 2004
 * @author	Rakesh Kalra
 */
public class ObjectParameterInfo extends ObjectFeatureInfo {

	private static final long serialVersionUID = -4150497689414589913L;
	private String type;
	private String legalValuesAsString;
    private Object[] legalValues;
    private String units;
    
    public ObjectParameterInfo(String name, String description, String type) {
        super(name, description);
        this.type = type;
    }

    public ObjectParameterInfo(String name, String description, String type, String legalValues) {
        this(name, description, type);
        this.legalValuesAsString = legalValues;
    }
    
    public String getType(){
        return type;
    }

    public String getDisplayType(){
        return getDisplayType(type);
    }
    
    public String getLegalValuesAsString(){
        return legalValuesAsString;
    }
    
    public Object[] getLegalValues(){
        return legalValues;
    }
    
    public void setUnits(String units) {
        this.units = units;
    }

    public String getUnits() {
        return units;
    }
    
    public boolean equals(Object obj){
        if(obj instanceof ObjectParameterInfo){
            ObjectParameterInfo parameterInfo = (ObjectParameterInfo)obj;
            return parameterInfo.type.equals(this.type);
        }
        return false;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void applyMetaData(ObjectParameterInfo metaParameterInfo, ExpressionProcessor exprProcessor) {
        if(metaParameterInfo.getName() != null){
            name = metaParameterInfo.getName();
        } 
        if(metaParameterInfo.getDescription() != null){
            description = metaParameterInfo.getDescription();
        } 
        if(metaParameterInfo.getUnits() != null){
            units = metaParameterInfo.getUnits();
        }
        if(metaParameterInfo.legalValuesAsString != null){
            Object output = exprProcessor.evaluate(metaParameterInfo.legalValuesAsString);
            if(output != null){
                if(output.getClass().isArray()){
                    legalValues = arrayToObjectArray(output);
                }else if(output instanceof Set){
                    legalValues = setToArray((Set)output);
                }else{
                    // assume that we have a csv
                    legalValues = StringUtils.csvToStringArray(output.toString());
                }
            }
        }
    }
    
    private Object[] arrayToObjectArray(Object data){
        int length = Array.getLength(data);
        Object[] output = new Object[length];
        for(int i=0;i<length;i++){
            output[i] = Array.get(data, i);
        }
        return output;
    }
    
    private Object[] setToArray(Set<Object> data){
        Object[] output = new Object[data.size()];
        int index = 0;
        for(Object item: data){
            output[index++] = item;
        }
        return output;
    }
}
