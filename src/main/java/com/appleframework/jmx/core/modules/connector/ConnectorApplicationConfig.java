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
package com.appleframework.jmx.core.modules.connector;

import com.appleframework.jmx.core.config.ApplicationConfig;
import java.util.Map;

/**
 * @author	Tak-Sang Chan
 */
public class ConnectorApplicationConfig extends ApplicationConfig {

    public String getConnectorId() {
        Map paramValues = getParamValues();
        return (String) paramValues.get("connectorId");
    }
    
    public String getHost() {
        return null;
    }

    public Integer getPort() {
        return null;
    }
    
    public String getURL() {
        return "";
    }
    
}
