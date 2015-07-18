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
package com.appleframework.jmx.core.util;

import java.util.StringTokenizer;

/**
 * Provides a mechanism to specify application name, mbean name and
 * attribute or operation name as a single String expression. The expression
 * looks like the following:
 * <br/>
 * &lt;appName&gt;/&lt;mbeaName&gt;/&lt;attrName&gt;
 * <p>
 * The mbeanName could be the "configured" mbean name or the object name.
 * This depends on the context in which Expression object is used.
 * <p>
 * This object is widely used in the jManage application specifically in
 * the command line UI and the access control system.
 *
 * Date:  Feb 26, 2005
 * @author	Rakesh Kalra
 */
public class Expression {

    public static final String WILDCARD = "*";
    public static final String DELIMITER = "/";

    private final String exprString;
    private String appName;
    private String mbeanName;
    /* this could be attribute or operation name */
    private String targetName;

    public Expression(String appName, String mbeanName, String targetName){

        this.appName = appName!=null && appName.length()>0?appName:WILDCARD;
        this.mbeanName = mbeanName!=null && mbeanName.length()>0?mbeanName:WILDCARD;
        this.targetName = targetName!=null && targetName.length()>0?targetName:WILDCARD;
        StringBuffer buff = new StringBuffer(this.appName);
        buff.append(DELIMITER);
        buff.append("\"");
        buff.append(this.mbeanName);
        buff.append("\"");
        buff.append(DELIMITER);
        buff.append(targetName);
        this.exprString = buff.toString();
    }

    public Expression(String exprString){
        this(exprString, (Expression)null);
    }

    public Expression(String exprString, Expression context){

        this.exprString = exprString;
        StringTokenizer tokenizer = new CustomStringTokenizer(exprString);
        if(context != null){
            this.appName = context.getAppName();
            this.mbeanName = context.getMBeanName();
            this.targetName = context.getTargetName();
            int tokenCount = 0;
            // we only expect max 3 tokens
            String[] tokens = new String[3];
            for(int i=0;tokenizer.hasMoreTokens() && i<3;i++){
                tokenCount ++;
                tokens[i] = tokenizer.nextToken();
            }
            if(tokenizer.hasMoreTokens()){
                throw new RuntimeException("invalid expression");
            }
            switch(tokenCount){
                case 1:
                    targetName = tokens[0];
                    break;
                case 2:
                    mbeanName = tokens[0];
                    targetName = tokens[1];
                    break;
                case 3:
                    appName = tokens[0];
                    mbeanName = tokens[1];
                    targetName = tokens[2];
                    break;
            }
        }else{
            if(tokenizer.hasMoreTokens())
                appName = tokenizer.nextToken();
            if(tokenizer.hasMoreTokens())
                mbeanName = tokenizer.nextToken();
            if(tokenizer.hasMoreTokens())
                targetName = tokenizer.nextToken();
        }

    }

    public String getAppName() {
        return appName;
    }

    public String getMBeanName() {
        return mbeanName;
    }

    public String getTargetName() {
        return targetName;
    }

    public String toString(){
        return exprString;
    }

    public String getHtmlEscaped(){
        return exprString.replaceAll("\"","&quot;");
    }

    /**
     * Handles the case where the delimiter "/" is within the expression.
     * Note that this tokenizer doesn't return the right value for
     * countTokens()
     */

    private class CustomStringTokenizer extends StringTokenizer{

        public CustomStringTokenizer(String expr){
            super(expr, DELIMITER);
        }

        /**
         * Handles "/" within the expression.
         */
        public String nextToken(){
            String token = super.nextToken();
            if(token.startsWith("\"")){
                if(token.endsWith("\"")){
                    // token ends with double quotes. just drop the double quotes
                    token = token.substring(1, token.length() -1);
                }else{
                    /* token starts with double quotes, but doesn't end with it.
                        Keep getting next token,
                        till we find ending double quotes */
                    StringBuffer buff = new StringBuffer(token.substring(1));

                    while(true){
                        String nextToken = super.nextToken();
                        buff.append(DELIMITER);
                        if(nextToken.endsWith("\"")){
                            buff.append(nextToken.substring(0, nextToken.length()-1));
                            break;
                        }else{
                            buff.append(nextToken);
                        }
                    }
                    token = buff.toString();
                }
            }
            return token;
        }

        public int countTokens(){
            throw new RuntimeException("countTokens() is not supported");
        }
    }
}
