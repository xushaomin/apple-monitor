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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Date: Mar 14, 2005 12:21:45 AM
 * @author Shashank Bellary 
 */
public interface ACLConstants {
    public static final String ACL_EDIT_USERS = "acl.edit.jmanage.users";
    public static final String ACL_ADD_USERS = "acl.add.jmanage.users";
    public static final String ACL_VIEW_USER_ACTIVITY = "acl.view.jmanage.user.activity";
    public static final String ACL_ADD_APPLICATIONS = "acl.add.jmanage.applications";
    public static final String ACL_EDIT_APPLICATIONS = "acl.edit.jmanage.applications";
    public static final String ACL_ADD_MBEAN_CONFIG = "acl.add.jmanage.mbean.config";
    public static final String ACL_EDIT_MBEAN_CONFIG = "acl.edit.jmanage.mbean.config";
    public static final String ACL_EDIT_JMANAGE_CONFIG = "acl.edit.jmanage.config";
    public static final String ACL_VIEW_APPLICATIONS =
            "acl.view.jmanage.applications";
    public static final String ACL_VIEW_MBEANS = "acl.view.jmanage.mbeans";
    public static final String ACL_VIEW_MBEAN_ATTRIBUTES =
            "acl.view.jmanage.mbean.attributes";
    public static final String ACL_UPDATE_MBEAN_ATTRIBUTES =
            "acl.update.jmanage.mbean.attributes";
    public static final String ACL_EXECUTE_MBEAN_OPERATIONS =
            "acl.execute.jmanage.mbean.operations";
    public static final String ACL_VIEW_MBEAN_NOTIFICATIONS =
            "acl.view.jmanage.mbean.notifications";
    public static final String ACL_ADD_ALERT = "acl.add.alert";
    public static final String ACL_EDIT_ALERT = "acl.edit.alert";
    public static final String ACL_ADD_GRAPH = "acl.add.graph";
    public static final String ACL_EDIT_GRAPH = "acl.edit.graph";
    public static final String ACL_ADD_DASHBOARD = "acl.add.dashboard";
    public static final String ACL_EDIT_DASHBOARD = "acl.edit.dashboard";
    
    public static final Set<String> ADMIN_ACLS = new HashSet<String>(Arrays.asList(new String[]{
            ACL_EDIT_USERS,
            ACL_ADD_USERS,
            ACL_VIEW_USER_ACTIVITY}));
}
