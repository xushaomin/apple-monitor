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

import com.appleframework.jmx.core.util.CoreUtils;

/**
 * Date : Jun 27, 2004 1:50:07 PM
 * @author Shashank
 */
public interface AuthConstants {
    public String USER_CONFIG_FILE_NAME = CoreUtils.getConfigDir()
            +  "/jmanage-users.xml";
    public String AUTH_CONFIG_INDEX = "JManageAuth";
    public String AUTH_CONFIG_FILE_NAME = CoreUtils.getConfigDir()
            + "/jmanage-auth.conf";
    public String AUTH_CONFIG_SYS_PROPERTY = "java.security.auth.login.config";
    public String ROLE_CONFIG_FILE_NAME = CoreUtils.getConfigDir()
            + "/jmanage-user-roles.xml";
    /*  'jmanage-users.xml' file related constants  */
    public String JM_USERS = "jmanage-users";
    public String USER = "user";
    public String NAME = "name";
    public String PASSWORD = "password";
    public String STATUS = "status";
    public String LOCK_COUNT = "lockCount";
    public String ROLE = "role";

    public String USER_ADMIN = "admin";

}
