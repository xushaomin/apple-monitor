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

import com.appleframework.jmx.core.util.JManageProperties;

import javax.management.MalformedObjectNameException;
import java.util.StringTokenizer;

/**
 *
 * date:  Aug 12, 2004
 * @author	Rakesh Kalra
 */
public class ObjectName implements java.io.Serializable {

	private static final long serialVersionUID = 3221022327464786502L;
	private final String objectName;
    private final String canonicalName;

    public ObjectName(String objectName){
        this.objectName = objectName;
        try {
            this.canonicalName =
                    new javax.management.ObjectName(objectName).getCanonicalName();
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectName(String objectName, String canonicalName){
        this.objectName = objectName;
        this.canonicalName = canonicalName;
    }

    public String getCanonicalName(){
        return canonicalName;
    }

    public String toString(){
        return objectName;
    }

    public String getDisplayName(){
        return JManageProperties.isDisplayCanonicalName()?
                canonicalName : objectName;
    }

    public static String getShortName(String objectName){
        String shortName = objectName;
        if(shortName.length() > 90){
            shortName = shortName.substring(0, 90) + "...";
        }
        return shortName;
    }

    /**
     * If the object name is longer than X characters, this method
     * takes the first X characters, adds "..." and returns the result.
     * @return
     */
    public String getShortName(){
        return getShortName(getDisplayName());
    }

    /**
     * Wraps a long object name by added \n and \t characters.
     *
     * @return
     */
    public String getWrappedName(){
        String wrappedName = getDisplayName();
        if(wrappedName.length() > 80){
            StringTokenizer tokenizer = new StringTokenizer(wrappedName, ",");
            StringBuffer buff = new StringBuffer(wrappedName.length());
            int multiplier = 1;
            while(tokenizer.hasMoreTokens()){
                String nextToken = tokenizer.nextToken();
                if((buff.length() + nextToken.length())  > 80 * multiplier){
                    buff.append("\n\t");
                    multiplier ++;
                }
                buff.append(nextToken);
                if(tokenizer.hasMoreTokens()){
                    buff.append(",");
                }
            }
            wrappedName = buff.toString();
        }
        return wrappedName;
    }

    public boolean equals(Object obj){
        if(obj instanceof ObjectName){
            ObjectName on = (ObjectName)obj;
            return on.canonicalName.equals(this.canonicalName);
        }
        return false;
    }
    
    public int hashCode(){
        return canonicalName.hashCode();
    }
}
