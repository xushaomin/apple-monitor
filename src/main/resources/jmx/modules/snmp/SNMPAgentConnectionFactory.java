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
package com.appleframework.jmx.core.modules.snmp;

import com.appleframework.jmx.core.management.ServerConnectionFactory;
import com.appleframework.jmx.core.management.ServerConnection;
import com.appleframework.jmx.core.management.ConnectionFailedException;
import com.appleframework.jmx.core.config.ApplicationConfig;
import snmp.SNMPv1CommunicationInterface;

import java.net.InetAddress;

/**
 * @author shashank
 * Date: Jul 31, 2005
 */
public class SNMPAgentConnectionFactory implements ServerConnectionFactory{

    /**
     *
     * @param config
     * @return
     * @throws ConnectionFailedException
     */
    public ServerConnection getServerConnection(ApplicationConfig config)
            throws ConnectionFailedException {
        try{
            InetAddress hostAddress = InetAddress.getByName(config.getHost());
            SNMPv1CommunicationInterface commIntf =
                    new SNMPv1CommunicationInterface(1, hostAddress, "public",
                            config.getPort().intValue());
            SNMPAgentConnection connection = new SNMPAgentConnection(commIntf);
            return connection;
        }catch(Throwable e){
            throw new ConnectionFailedException(e);
        }
    }
}
