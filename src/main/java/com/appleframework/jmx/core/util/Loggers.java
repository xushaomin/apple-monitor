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

import org.apache.log4j.Logger;

/**
 * A helper class that is used for getting the logger for making log calls.
 *
 * date:  Aug 17, 2004
 * @author	Rakesh Kalra
 */
public class Loggers {

    /**
     * Returns a logger based on the package name. Every package has a shared
     * logger.
     * <p>
     * The logger in jmanage code, must be retrieved by calling this method.
     *
     * @param clazz     the class that wants to use the logger
     * @return  Logger instance for the package containing the class
     */
	public static Logger getLogger(Class<?> clazz){
        if(clazz == null){
            // this condition will happen if the class has not been initialized
            return Logger.getRootLogger();
        }
        return Logger.getLogger(clazz.getPackage().getName());
    }
}
