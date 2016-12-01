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

import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.jmx.core.util.CoreUtils;

import java.util.Properties;
import org.apache.log4j.Logger;
import java.io.*;

/**
 * JManageProperties provides an interface to read configuration parameters from
 * jmanage.properties file.
 *
 * Date: Dec 4, 2004 2:38:01 AM
 * @author Shashank Bellary
 * @author Rakesh Kalra
 */
public class JManageProperties extends Properties{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Loggers.getLogger(JManageProperties.class);

    private String JMANAGE_PROPERTY_FILE = CoreUtils.getConfigDir() + "/jmanage.properties";

    /* see jmanage.properties for documentation of these properties */
    public static String LOGIN_MAX_ATTEMPTS = "login.maxAttempts";
    private static String JMANAGE_HASH_ALGORITHM = "jmanage.hash.algorithm";

    private static String JETTY_STOP_PORT = "STOP.PORT";

    /* CLI properties */
    private static String JMANAGE_URL = "jmanage.url";
    private static String CLI_SSL_IGNORE_BAD_SSL_CERT =
            "jmanage.cli.ssl.ignoreBadSSLCertificate";
    private static String CLI_SSL_TRUST_STORE_PWD =
            "jmanage.cli.ssl.trustStorePassword";

    /*Email properties*/
    private static String ALERT_EMAIL_FROM_NAME = "alert.email.from.name";
    private static String  EMAIL_HOST = "email.host";
    private static String  ALERT_EMAIL_FROM_EMAIL = "alert.email.from.email";

    /* display properties */
    private static String DISPLAY_MBEAN_CANONICAL_NAME = "jmanage.objectName.displayCanonicalName";

    /* HTML input type for boolean attributes */
    private static String BOOLEAN_INPUT_TYPE = "jmanage.html.booleanInputType";

    /* Option to auto-login the admin user. This is useful when security is not important. */
    private static String AUTO_LOGIN_ADMIN_USER = "auto.login.adminUser";
    
    /*  The only instance   */
    private static JManageProperties jManageProperties = new JManageProperties();

    /**
     * The only constructor, which is private.
     *
     * TODO: I think we should remove this ctor and initialize in static block.
     * All get methods on this class can be static - rk
     */
    private JManageProperties(){
      super();
      try{
        InputStream property = new FileInputStream(JMANAGE_PROPERTY_FILE);
        load(property);
      }catch(Throwable e){
          logger.warn( "Error reading " + JMANAGE_PROPERTY_FILE + ". error: " + e.getMessage());
      }
    }

    /**
     * Gets an instance of the JManageProperties class. This is the only way
     * that any class can get and access a JManageProperties object, since the
     * constructor is private
     **/
    public static JManageProperties getInstance() {
      return jManageProperties;
    }

    public static String getJManageURL(){
        return jManageProperties.getProperty(JMANAGE_URL);
    }

    public static String getHashAlgorithm(){
        return jManageProperties.getProperty(JMANAGE_HASH_ALGORITHM, "SHA-1");
    }

    public static String getStopPort(){
        return jManageProperties.getProperty(JETTY_STOP_PORT, "9099");
    }

    public static boolean isIgnoreBadSSLCertificate(){
        String value =
                jManageProperties.getProperty(CLI_SSL_IGNORE_BAD_SSL_CERT, "false");
        return value.equalsIgnoreCase("true");
    }

    public static String getSSLTrustStorePassword(){
        return jManageProperties.getProperty(CLI_SSL_TRUST_STORE_PWD, "changeit");
    }

    public void storeMaxLoginAttempts(int maxLoginAttempt){
                this.setProperty(LOGIN_MAX_ATTEMPTS,
                Integer.toString(maxLoginAttempt));
        try{
           FileOutputStream fileOutputStream =
                 new FileOutputStream(JMANAGE_PROPERTY_FILE);
            this.store(fileOutputStream, null);
        } catch( Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String getAlertEmailFromName(){
        return jManageProperties.getProperty(ALERT_EMAIL_FROM_NAME);
    }
    public static String getEmailHost(){
        return jManageProperties.getProperty(EMAIL_HOST);
    }
    public static String getAlertEmailFrom(){
        return jManageProperties.getProperty(ALERT_EMAIL_FROM_EMAIL);
    }
    public static boolean isBooleanInputTypeRadio(){
        return "radio".equals(jManageProperties.getProperty(BOOLEAN_INPUT_TYPE));
    }
    public static boolean isBooleanInputTypeCheckbox(){
        return "checkbox".equals(jManageProperties.getProperty(BOOLEAN_INPUT_TYPE));
    }
    public static boolean isBooleanInputTypeSelect() {
        return "select".equals(jManageProperties.getProperty(BOOLEAN_INPUT_TYPE));
    }

    public static boolean isAutoLoginAdminUser(){
        return "true".equals(jManageProperties.getProperty(AUTO_LOGIN_ADMIN_USER));
    }
    
    /**
     * Indicates if the canonical name of the mbean should be displayed
     * @return
     */
    public static boolean isDisplayCanonicalName(){
        return "true".equals(
                jManageProperties.getProperty(DISPLAY_MBEAN_CANONICAL_NAME));
    }
}
