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
package com.appleframework.jmx.core.auth;

import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.jmx.core.util.CoreUtils;

import java.util.Properties;
import org.apache.log4j.Logger;
import java.io.InputStream;
import java.io.FileInputStream;

/**
 * @author shashank
 * Date: Sep 27, 2005
 */
public class ExternalUserRolesConfig extends Properties{

	private static final long serialVersionUID = 1L;

	private static final Logger logger =
            Loggers.getLogger(ExternalUserRolesConfig.class);

    private String EXTERNAL_USER_ROLES_CONFIG_FILE = CoreUtils.getConfigDir() +
            "/external-user-roles.properties";

    private final String ASTERIX = "*";

    /*  The only instance   */
    private static ExternalUserRolesConfig instance =
            new ExternalUserRolesConfig();

    /**
     *
     */
    private ExternalUserRolesConfig(){
      super();
      try{
        InputStream property =
                new FileInputStream(EXTERNAL_USER_ROLES_CONFIG_FILE);
        load(property);
      }catch(Exception e){
          logger.error("Error reading " + EXTERNAL_USER_ROLES_CONFIG_FILE, e);
          CoreUtils.exitSystem();
      }
    }

    public static ExternalUserRolesConfig getInstance(){
        return instance;
    }

    public String getUserRole(String username){
        String role = instance.getProperty(username);
        return role != null ? role : instance.getProperty(ASTERIX);
    }
}
