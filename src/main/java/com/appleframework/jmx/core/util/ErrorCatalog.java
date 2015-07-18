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

import java.util.Properties;
import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;

/**
 *
 * date:  Feb 15, 2005
 * @author	Rakesh Kalra
 */
public class ErrorCatalog {

    private static final Logger logger = Loggers.getLogger(ErrorCatalog.class);

    private static final Properties errorMap;

    static{
        final String errorProperties = CoreUtils.getConfigDir() +
                "/errors.properties";
        errorMap = new Properties();
        try {
            errorMap.load(new FileInputStream(errorProperties));
        } catch (IOException e) {
            logger.warn( "Error reading " +
                  errorProperties + ". error: " + e.getMessage());
        }
    }

    public static String getMessage(String errorCode){
        return getMessage(errorCode, null);
    }

    public static String getMessage(String errorCode, Object value0){
        return getMessage(errorCode, new Object[]{value0});
    }

    public static String getMessage(String errorCode, Object[] values){
        String message = errorMap.getProperty(errorCode,
                "ErrorCode=" + errorCode);
        if(values != null){
            message = MessageFormat.format(message, values);
        }
        return message;
    }
}
