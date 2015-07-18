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

/**
 *
 * date:  Aug 13, 2004
 * @author	Rakesh Kalra
 */
public class ObjectFeatureInfo implements java.io.Serializable {

	private static final long serialVersionUID = -2969100630194827734L;
	protected String name;
    protected String description;

    public ObjectFeatureInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        if(description == null || description.trim().length() == 0)
            return "No description available.";
        return description;
    }

    public String getName() {
        return name;
    }

    protected String getDisplayType(String type){
        if(type != null && type.startsWith("[")){
            /* convert to readable name. e.g. [Ljava.lang.String to
                java.lang.String[]*/
            String arrayBraces = "";
            while(type.startsWith("[")){
                type = type.substring(1);
                arrayBraces += "[]";
            }
            type = getArrayDisplayType(type) + arrayBraces;
        }
        return type;
    }

    /**
     * Decodes the element type
     *
     * <blockquote><table summary="Element types and encodings">
     * <tr><th> Element Type <th> Encoding
     * <tr><td> boolean      <td align=center> Z
     * <tr><td> byte         <td align=center> B
     * <tr><td> char         <td align=center> C
     * <tr><td> class or interface  <td align=center> L<i>classname;</i>
     * <tr><td> double       <td align=center> D
     * <tr><td> float        <td align=center> F
     * <tr><td> int          <td align=center> I
     * <tr><td> long         <td align=center> J
     * <tr><td> short        <td align=center> S
     * </table></blockquote>
     *
     * @param arrayType encoded element type without the beginning "["
     * @return
     */
    private String getArrayDisplayType(String arrayType){
        String dataType;
        if(arrayType.equals("Z")){
            dataType = "boolean";
        }else if(arrayType.equals("B")){
            dataType = "byte";
        }else if(arrayType.equals("C")){
            dataType = "char";
        }else if(arrayType.equals("D")){
            dataType = "double";
        }else if(arrayType.equals("F")){
            dataType = "float";
        }else if(arrayType.equals("I")){
            dataType = "int";
        }else if(arrayType.equals("J")){
            dataType = "long";
        }else if(arrayType.equals("S")){
            dataType = "short";
        }else if(arrayType.startsWith("L")){
            dataType = arrayType.substring(1, arrayType.length() - 1);
        }else{
            throw new RuntimeException("Invalid arrayType:" + arrayType);
        }
        return dataType;
    }
}
