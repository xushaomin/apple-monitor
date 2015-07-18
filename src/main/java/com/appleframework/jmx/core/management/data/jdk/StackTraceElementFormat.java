/**
* Copyright (c) 2004-2005 jManage.org
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
package com.appleframework.jmx.core.management.data.jdk;

import com.appleframework.jmx.core.management.data.DataFormat;

import javax.management.openmbean.CompositeData;

/**
 * Formatter for java.lang.StackTraceElement. It recieved CompositeData
 * representation of StackTraceElement. Returns a string like:
 * <br/><br/>
 *       <i>java.lang.Thread.sleep(Native Method)</i>
 *       <i>JConsoleTest.main(JConsoleTest.java:13)</i>
 * <p>
 * Date:  Dec 9, 2005
 * @author	Rakesh Kalra
 */
public class StackTraceElementFormat implements DataFormat {

    public String format(Object data) {
        CompositeData[] compositeData = (CompositeData[])data;
        StringBuffer stackTrace = new StringBuffer();
        for(int i=0; i<compositeData.length; i++){
            if(i > 0)
                stackTrace.append("\n");
            stackTrace.append(format(compositeData[i]));
        }
        return stackTrace.toString();
    }

    private String format(CompositeData compositeData){
        StringBuffer stackTraceElement = new StringBuffer("    ");
        stackTraceElement.append((String)compositeData.get("className"));
        stackTraceElement.append('.');
        stackTraceElement.append(compositeData.get("methodName"));
        stackTraceElement.append('(');
        Boolean nativeMethod = (Boolean)compositeData.get("nativeMethod");
        if(!nativeMethod.booleanValue()){
            stackTraceElement.append(compositeData.get("fileName"));
            stackTraceElement.append(':');
            stackTraceElement.append(compositeData.get("lineNumber"));
        }else{
            stackTraceElement.append("Native Method");
        }
        stackTraceElement.append(')');
        return stackTraceElement.toString();
    }
}
