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

import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.appleframework.jmx.core.io.ClassPathResource;

import java.math.BigInteger;
import java.math.BigDecimal;


/**
 *
 * date:  Jun 22, 2004
 * @author	Rakesh Kalra
 */
public class CoreUtils {

    private static final Logger logger = Loggers.getLogger(CoreUtils.class);

    private static String rootDir = null;
    private static String dataDir;
    private static String CONFIG_PATH = "jmx";

	static {
		
    	ClassPathResource classPathResource = new ClassPathResource(CONFIG_PATH);
		try {
			File configFile = classPathResource.getFile();
			rootDir = configFile.getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//rootDir = System.getProperty(SystemProperties.JMX_ROOT);
		assert rootDir != null;

		logger.info("jmx.root=" + rootDir);

		/* create data dir */
		dataDir = getRootDir() + "/data";
		File dataDirFile = new File(dataDir);
		if (!dataDirFile.exists()) {
			dataDirFile.mkdirs();
		}
	}
        
    public static String getRootDir(){
        return rootDir;
    }

    public static String getConnectorDir() {
        return getRootDir() + File.separatorChar + "connector";    
    }

    public static String getConfigDir(){
        return getRootDir() + "/config";
    }

    public static String getWebDir(){
        return getRootDir() + "/web";
    }

    public static String getDashboardsDir(){
        return getRootDir() + File.separatorChar + "dashboards";
    }

    public static String getModuleDir(String moduleId){
        return getRootDir() + "/modules/" + moduleId;
    }

    public static String getApplicationDir(String appId){
        return getRootDir() + "/applications/" + appId;
    }

    public static String getLogDir(){
        return getRootDir() + "/logs";
    }

    public static String getDataDir() {
        return dataDir;
    }

    public static void copyProperties(Object dest, Object source) {
        try {
            BeanUtils.copyProperties(dest, source);
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void exitSystem(){
        logger.error("Shutting down application");
        System.exit(1);
    }

    public static Number valueOf(String value, String dataType){
        if(dataType.equals("java.lang.Integer")|| dataType.equals("int")){
            return new Integer(value);
        }
        if(dataType.equals("java.lang.Double") || dataType.equals("double")){
            return new Double(value);
        }
        if(dataType.equals("java.lang.Long") || dataType.equals("long")){
            return new Long(value);
        }
        if(dataType.equals("java.lang.Float") || dataType.equals("float")){
            return new Double(value);
        }
        if(dataType.equals("java.lang.Short") || dataType.equals("short")){
            return new Short(value);
        }
        if(dataType.equals("java.lang.Byte") || dataType.equals("byte")){
            return new Byte(value);
        }
        if(dataType.equals("java.math.BigInteger")){
            return new BigInteger(value);
        }
        if(dataType.equals("java.math.BigDecimal")){
            return new BigDecimal(value);
        }
        return null;
    }
}