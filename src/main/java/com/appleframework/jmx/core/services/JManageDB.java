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
package com.appleframework.jmx.core.services;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 *
 * date:  Apr 27, 2006
 * @author	Vinit Srivastava
 */

public interface JManageDB {
    public void stop();
    public boolean isAvailable();
    public List <Map> getAttributeList();
    public Connection getConnection();
}

