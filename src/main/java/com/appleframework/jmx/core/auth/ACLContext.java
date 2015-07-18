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
package com.appleframework.jmx.core.auth;

import com.appleframework.jmx.core.util.Expression;

/**
 * ACLContext is used to create dynamic ACLs. ACLContext is appended to the ACL after an "@"
 * character. It contains the application, mbean and the attribute/operation on which the ACL
 * is being applied.
 *  
 * Date:  Apr 8, 2005
 * @author	Rakesh Kalra
 */
public class ACLContext {

    private static final String WILDCARD = Expression.WILDCARD;

    private String appName = WILDCARD;
    /* object name or configured name */
    private String mbeanName = WILDCARD;
    /* this could be attribute or operation name */
    private String targetName = WILDCARD;

    public ACLContext(String aclContext){
        Expression expr = new Expression(aclContext);
        if(expr.getAppName() != null){
            appName = expr.getAppName();
        }
        if(expr.getMBeanName() != null){
            // todo: we need to convert the MBean name to canonical name
            //   for comparision. It will be good to further improve
            //    the comparision by allowing wild card comparisions.
            mbeanName = expr.getMBeanName();
        }
        if(expr.getTargetName() != null){
            targetName = expr.getTargetName();
        }
    }

    public ACLContext(String appName, String mbeanName, String targetName) {
        if(appName != null){
            this.appName = appName;
        }
        if(mbeanName != null){
            this.mbeanName = mbeanName;
        }
        if(targetName != null){
            this.targetName = targetName;
        }
    }

    public boolean equals(Object obj){
        if(obj instanceof ACLContext){
            ACLContext context = (ACLContext)obj;
            return compare(context.appName, this.appName) &&
                   compare(context.mbeanName, this.mbeanName) &&
                   compare(context.targetName, this.targetName);
        }
        return false;
    }
    
    public String toString(){
        return new Expression(appName, mbeanName, targetName).toString();
    }

    private boolean compare(String a, String b){
        boolean equals = false;
        if(a == null && b == null){
            /* both are null */
            equals = true;
        }else if(a == null || b == null){
            /* one of them is not null */
            equals = false;
        }else if(a.equals(WILDCARD) || b.equals(WILDCARD)){
            /* one of them is wild card */
            equals = true;
        }else if(a.equals(b)){
            /* they are the same */
            equals = true;
        }
        return equals;
    }
}
