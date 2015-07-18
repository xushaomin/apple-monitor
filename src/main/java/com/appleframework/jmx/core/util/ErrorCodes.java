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

/**
 *
 * date:  Feb 13, 2005
 * @author  Rakesh Kalra, Shashank Bellary
 */
public interface ErrorCodes {

    String UNKNOWN_ERROR = "unknown.error";
    String ERROR_REQUIRED = "errors.required";
    String INVALID_CREDENTIALS = "invalid.login";
    String ACCOUNT_LOCKED = "account.locked";
    String INVALID_LOGIN_ATTEMPTS = "invalid.login.attempt.count";

    String INVALID_APPLICATION_ID = "invalid.appId";
    String INVALID_MBEAN_ATTRIBUTE = "invalid.mbean.attribute";
    String INVALID_MBEAN_OPERATION = "invalid.mbean.operation";
    String READ_ONLY_MBEAN_ATTRIBUTE = "readOnly.mbean.attribute";

    String OPERATION_NOT_SUPPORTED_FOR_CLUSTER = "cluster.unsupported.operation";

    String INVALID_OLD_PASSWORD = "invalid.oldPassword";
    String PASSWORD_MISMATCH = "mismatch.password";
    String CONNECTION_FAILED = "app.connection.failed";

    String CLUSTER_NO_APPLICATIONS = "cluster.noApplicationsDefined";

    String ERRONEOUS_APPS = "erroneous.apps";
    String JMANAGE_SERVER_CONNECTION_FAILED = "jmanage.connection.failed";

    String APPLICATION_ID_ALREADY_EXISTS = "application.id.already.exists";
    String INVALID_CHAR_APP_NAME = "error.appNameMask";
}