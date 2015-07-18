/**
* Copyright (c) 2004-2005 jManage.org
*
* This is a free software; you can redistribute it and/or
* modify it under the terms of the license at
* http://www.jmanage.org.
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.appleframework.jmx.core.config;

import com.appleframework.jmx.core.util.CoreUtils;

import java.net.URL;
import java.io.File;

/**
 *
 * <p>
 * Date:  Sep 25, 2005
 * @author	Rakesh Kalra
 */
public class ApplicationType {

    private String id;
    private String name;
    private ModuleConfig module;
    private boolean isCompatibleJMX;
    private String defaultHost;
    private String defaultPort;
    private String defaultURL;

    private ClassLoader classLoader;

    public ApplicationType(String id,
                           String name,
                           ModuleConfig module,
                           boolean isCompatibleJMX,
                           String defaultHost,
                           String defaultPort,
                           String defaultURL){
        this.id = id;
        this.name = name;
        this.module = module;
        this.isCompatibleJMX = isCompatibleJMX;
        this.defaultHost = defaultHost;
        this.defaultPort = defaultPort;
        this.defaultURL = defaultURL;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ModuleConfig getModule() {
        return module;
    }

    public boolean isCompatibleJMX() {
        return isCompatibleJMX;
    }

    public String getDefaultHost() {
        return defaultHost;
    }

    public String getDefaultPort() {
        return defaultPort;
    }

    public String getDefaultURL() {
        return defaultURL;
    }

    public ClassLoader getClassLoader() {
        if(classLoader == null){
            classLoader = ClassLoaderRepository.getClassLoader(getClassPath(), isCompatibleJMX);
        }
        return classLoader;
    }

    public URL[] getClassPath(){
        URL[] classpath = module.getModuleClassPath();
        File appDir = new File(CoreUtils.getApplicationDir(id));
        if(appDir.isDirectory()){
            URL[] appClasspath = ConfigUtils.getClassPath(appDir);
            /* add the two paths together */
            URL[] moduleClasspath = classpath;
            classpath = new URL[appClasspath.length + moduleClasspath.length];
            int i=0;
            for(i=0; i<appClasspath.length;i++){
                classpath[i] = appClasspath[i];
            }
            for(int j=0; j<moduleClasspath.length; j++, i++){
                classpath[i] = appClasspath[j];
            }
        }
        return classpath;
    }
}
