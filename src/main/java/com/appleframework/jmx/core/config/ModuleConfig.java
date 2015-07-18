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
package com.appleframework.jmx.core.config;

import com.appleframework.jmx.core.config.MetaApplicationConfig;
import com.appleframework.jmx.core.util.CoreUtils;

import java.net.URL;
import java.io.File;

/**
 *
 * date:  Aug 13, 2004
 * @author	Rakesh Kalra
 */
public class ModuleConfig {

    private String id;
    private MetaApplicationConfig metaConfig;
    private String connectionFactory;

    public ModuleConfig(String id, MetaApplicationConfig metaConfig, String connectionFactory)
        throws ModuleNotFoundException {
        this.id = id;
        this.metaConfig = metaConfig;
        this.connectionFactory = connectionFactory;
    }

    public MetaApplicationConfig getMetaApplicationConfig() {
        return metaConfig;
    }

    public String getConnectionFactory() {
        return connectionFactory;
    }

    public boolean isAvailable(){
        final String moduleDirPath = CoreUtils.getModuleDir(id);
        final File moduleDir = new File(moduleDirPath);
        return moduleDir.isDirectory();
    }

    public URL[] getModuleClassPath() throws ModuleNotFoundException {
        final String moduleDirPath = CoreUtils.getModuleDir(id);
        final File moduleDir = new File(moduleDirPath);
        if(!moduleDir.isDirectory()){
            throw new ModuleNotFoundException(id);
        }
        return ConfigUtils.getClassPath(moduleDir);
    }

    public static class ModuleNotFoundException extends RuntimeException{
		private static final long serialVersionUID = -8450974092075042186L;
		ModuleNotFoundException(String module){
            super("Module=" + module);
        }
    }
}
