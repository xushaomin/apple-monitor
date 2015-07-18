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


import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import org.apache.log4j.Logger;

import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.jmx.core.util.StringUtils;

/**
 * Holds classloaders for different application types. This is used so
 * that we don't end up creating classloaders for the same classpath
 * "incompatibleJMX" parameter.
 *
 * <p>
 * Date:  Sep 25, 2005
 * @author	Rakesh Kalra
 */
public class ClassLoaderRepository {

    private static final Logger logger = Loggers.getLogger(ClassLoaderRepository.class);

    /* key to ClassLoader mapping */
    private static Map<Key, ClassLoader> classLoaders = 
    	Collections.synchronizedMap(new HashMap<Key, ClassLoader>());

    public static ClassLoader getClassLoader(URL[] classpath, boolean isCompatibleJMX){

        Key key = new Key(classpath, isCompatibleJMX);
        ClassLoader classLoader = (ClassLoader)classLoaders.get(key);
        if(classLoader == null){
            logger.info("Creating new ClassLoader for classpath : " +
                    StringUtils.toString(classpath) + ", isCompatibleJMX=" + isCompatibleJMX);
            if(!isCompatibleJMX){
                classLoader = new JMXMeFirstClassLoader(classpath, ModuleConfig.class.getClassLoader());
            }else{
                classLoader = new URLClassLoader(classpath, ModuleConfig.class.getClassLoader());
            }
            classLoaders.put(key, classLoader);
        }
        return classLoader;
    }

    private static class Key {
        URL[] classpath;
        boolean isCompatibleJMX;
        Key(URL[] classpath, boolean isCompatibleJMX){
            this.classpath = classpath;
            this.isCompatibleJMX = isCompatibleJMX;
        }

        public boolean equals(Object obj){
            if(obj instanceof Key){
                Key key = (Key)obj;
                if(key.isCompatibleJMX == this.isCompatibleJMX && compare(key.classpath,  this.classpath)){
                    return true;
                }
            }
            return false;
        }

        public int hashCode(){
            return classpath.hashCode() * 31 + Boolean.valueOf(isCompatibleJMX).hashCode();
        }

        boolean compare(URL[] a, URL[] b){
            String strA = StringUtils.toString(a);
            String strB = StringUtils.toString(b);
            return strA.equals(strB);
        }
    }

    private static class JMXMeFirstClassLoader extends URLClassLoader{

        JMXMeFirstClassLoader(URL[] classpath, ClassLoader parent){
            super(classpath, parent);
        }

        protected synchronized Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException{

            if(name.startsWith("javax.management.")){
                // First, check if the class has already been loaded
	            Class<?> c = findLoadedClass(name);
                if(c == null){
                    try {
                        c = findClass(name);
                    } catch (ClassNotFoundException e) {
                        c = getParent().loadClass(name);
                    }
                }
                if (resolve) {
                    resolveClass(c);
                }
                return c;
            }
            return super.loadClass(name, resolve);
        }
    }
}

