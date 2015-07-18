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
package com.appleframework.jmx.core.management.metadata;

import java.util.HashMap;
import java.util.Map;

import com.appleframework.jmx.core.management.ObjectName;
import com.appleframework.jmx.core.management.ServerConnection;

/**
 *
 * @author Rakesh Kalra
 */
public class ExpressionProcessor {

    private final Map<String, Object> expressionToValueMap = new HashMap<String, Object>();
    private final ServerConnection connection ;    
    private final ObjectName objectName;  
    
    public ExpressionProcessor(ServerConnection connection, ObjectName objectName){
        this.connection = connection;
        this.objectName = objectName;
    }
    
    public Object evaluate(String expr){
        if(expressionToValueMap.containsKey(expr)){
            return expressionToValueMap.get(expr);
        }else{
            Object output = evaluateInternal(expr);
            expressionToValueMap.put(expr, output);
            return output;
        }
    }
    
    private Object evaluateInternal(String expr){
        if(expr.startsWith("${")){
            // TODO: we only support attribute names right now
            String attributeName = expr.substring(2, expr.length() - 1);
            return connection.getAttribute(objectName, attributeName);
        }else{
            // it is not an expression
            return expr;
        }
    }
}
