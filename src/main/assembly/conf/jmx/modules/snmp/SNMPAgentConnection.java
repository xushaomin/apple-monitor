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

import com.appleframework.jmx.core.management.*;
import com.appleframework.jmx.core.util.CoreUtils;
import com.appleframework.jmx.core.util.Loggers;

import java.util.*;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.io.FileInputStream;

import snmp.*;

/**
 * @author shashank
 * @author Rakesh Kalra
 * Date: Jul 31, 2005
 */
public class SNMPAgentConnection implements ServerConnection{

    private static final Logger logger =
            Loggers.getLogger(SNMPAgentConnection.class);

    private final SNMPv1CommunicationInterface comInterface;
    private final String mBeanObjectName = "snmp:name=SNMPAgent";

    private static final Properties OIDToAttributeMap;
    private static final Map<String, String> attributeToOIDMap = new HashMap<String, String>();

    static{
        try {
            OIDToAttributeMap = new Properties();
            OIDToAttributeMap .load(new FileInputStream(CoreUtils.getConfigDir() +
                    "/" + "snmp-oids.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(Enumeration<?> en=OIDToAttributeMap.keys();en.hasMoreElements();){
            String OID = (String)en.nextElement();
            String attribute = (String)OIDToAttributeMap.get(OID);
            attributeToOIDMap.put(attribute, OID);
        }
        assert OIDToAttributeMap.size() == attributeToOIDMap.size():
                "duplicate attribute name found";
    }

    public SNMPAgentConnection(SNMPv1CommunicationInterface comInterface) {
        assert comInterface != null;
        this.comInterface = comInterface;
    }

    public Set<ObjectName> queryNames(ObjectName objectName) {
        // This call is to make sure that SNMP agent is accessible
        //  todo: optimize -- no need to do complete tree walk here
        // getMIBDetails(objectName);
        Set<ObjectName> mBeanSet = new HashSet<ObjectName>();
        mBeanSet.add(new ObjectName(mBeanObjectName));
        return mBeanSet;
    }

    public Object invoke(ObjectName objectName,
                         String operationName,
                         Object[] params,
                         String[] signature) {
        return null;
    }

    /**
     * TODO: It will be better to cache the ObjectInfo for this connection,
     * as we make two calls to the SNMP agent -- once in this method,
     * and then again in getAttributes() method.
     *
     * @param objectName
     * @return
     */
    public ObjectInfo getObjectInfo(ObjectName objectName) {
        SNMPVarBindList mibList = getMIBDetails(objectName, OIDToAttributeMap.keySet());
        ObjectInfo objectInfo = mibListToJMXMBean(mibList);
        return objectInfo;
    }

    /**
     * Gets the value for a single attribute.
     *
     * todo: optimize
     *
     * @param objectName
     * @param attributeName
     * @return attribute value
     */
    public Object getAttribute(ObjectName objectName, String attributeName) {
        List attrList = getAttributes(objectName, new String[]{attributeName});
        if(attrList.size() > 0){
            ObjectAttribute objAttr = (ObjectAttribute)attrList.get(0);
            return objAttr.getValue();
        }
        return null; // todo: null is probably not the right value here
    }

    public List getAttributes(ObjectName objectName, String[] attributeNames) {

        Set<String> OIDs = new HashSet<String>();
        for(int i=0;i<attributeNames.length; i++){
            OIDs.add(attributeToOIDMap.get(attributeNames[i]));
        }
        SNMPVarBindList mibList = getMIBDetails(objectName, OIDs);
        final int mibVarCount = mibList.size();
        List<ObjectAttribute> attributeList = new ArrayList<ObjectAttribute>();
        for(int index=0; index<mibVarCount; index++){
            SNMPSequence pair = (SNMPSequence)mibList.getSNMPObjectAt(index);
            SNMPObjectIdentifier snmpOID = (SNMPObjectIdentifier)pair.getSNMPObjectAt(0);
            SNMPObject snmpValue = pair.getSNMPObjectAt(1);
            attributeList.add(new ObjectAttribute((String)OIDToAttributeMap.get(snmpOID.toString()),
                    snmpValue.toString()));
        }
        return attributeList;
    }

    public List setAttributes(ObjectName objectName, List attributeList) {
        return null;
    }

    public void addNotificationListener(ObjectName objectName,
                                        ObjectNotificationListener listener,
                                        ObjectNotificationFilter filter,
                                        Object handback){
        throw new RuntimeException("Notifications not supported");
    }

    public void removeNotificationListener(ObjectName objectName,
                                           ObjectNotificationListener listener,
                                           ObjectNotificationFilter filter,
                                           Object handback){
        throw new RuntimeException("Notifications not supported");
    }

    public void createMBean(String className,
                            ObjectName name,
                            Object[] params,
                            String[] signature){
        throw new RuntimeException("createMBean not supported");
    }

    public void unregisterMBean(ObjectName objectName){
        throw new RuntimeException("unregisterMBean not supported");
    }

    public void close() throws IOException {
        comInterface.closeConnection();
    }

    public Object buildObjectName(String objectName){
        try {
            return new javax.management.ObjectName(objectName);
        } catch (javax.management.MalformedObjectNameException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * checks if this connection is open
     * @return true if this connection is open
     */
    public boolean isOpen() {
        // todo: requires proper implementation before notifications
        //   are enabled
        return true;
    }

    /**
     * Builds list of SNMP attributes available as MIB info into a JMX object
     * attributes.
     *
     * @param mibList
     * @return
     */
    private ObjectInfo mibListToJMXMBean(SNMPVarBindList mibList){
        final int mibVarCount = mibList.size();
        ObjectAttributeInfo[] objectAttributes = new ObjectAttributeInfo[mibVarCount];
        ObjectConstructorInfo[] objectConstructorInfo = new ObjectConstructorInfo[0];
        ObjectOperationInfo[] objectOperationInfo = new ObjectOperationInfo[0];
        ObjectNotificationInfo[] objectNotificationInfo = new ObjectNotificationInfo[0];

        for(int index=0; index<mibVarCount; index++){
            SNMPSequence pair = (SNMPSequence)mibList.getSNMPObjectAt(index);
            SNMPObjectIdentifier snmpOID = (SNMPObjectIdentifier)pair.getSNMPObjectAt(0);
            SNMPObject snmpValue = pair.getSNMPObjectAt(1);
            ObjectAttributeInfo objectAttributeInfo =
                    new ObjectAttributeInfo((String)OIDToAttributeMap.get(snmpOID.toString()),
                            "OID:" + snmpOID,  // TODO: if snmp-oids.properties is an xml file, description can be added there
                            getAttributeType(snmpValue, snmpOID.toString() ), false, true, false);
            objectAttributes[index] = objectAttributeInfo;
        }

        ObjectInfo objectInfo = new ObjectInfo(new ObjectName(mBeanObjectName),
                "SNMPAgent", "SNMPAgent", objectAttributes, objectConstructorInfo,
                objectOperationInfo, objectNotificationInfo);
        return objectInfo;
    }

    /**
     * TODO Add support for all TYPES
     * @param snmpValue
     * @return
     */
    private String getAttributeType(SNMPObject snmpValue, String OID){
        final String attributeType = snmpValue.getClass().getName();
        if(snmp.SNMPOctetString.class.getName().equals(attributeType)){
            return "String";
        }else if(snmp.SNMPInteger.class.getName().equals(attributeType)){
            return "int";
        }else if(snmp.SNMPNull.class.getName().equals(attributeType)){
            return "null";
        }else{
            //System.out.println("Unknown type:" + attributeType + " for OID:" + OID);
            return "String";
            //throw new RuntimeException("Unsupported data type");
        }
    }

    /**
     *
     * @param objectName
     * @return
     */
    private SNMPVarBindList getMIBDetails(ObjectName objectName, Set OIDs){
        SNMPVarBindList varBindList = new SNMPVarBindList();
        for(Iterator<?> it=OIDs.iterator(); it.hasNext();){
            String OID = (String)it.next();
            try {
                SNMPSequence sequence = getValue(OID);
                if(!snmp.SNMPUnknownObject.class.equals(sequence.getSNMPObjectAt(1).getClass())){
                    varBindList.addSNMPObject(sequence);
                }else{
                    logger.info("OID not found:" + OID);
                }
            } catch (SNMPGetException e) {
                logger.info("Error getting OID:" + OID + ". msg=" + e.getMessage());
            } catch (SNMPBadValueException e) {
                logger.info("SNMPBadValueException: OID:" + OID + ". msg=" + e.getMessage());
            } catch (IOException e){
                logger.error("Error getting value for OID:" + OID + ". msg=" + e.getMessage());
            }
        }
        return varBindList;
    }

    private SNMPSequence getValue(String OID) throws IOException, SNMPGetException, SNMPBadValueException {
        SNMPVarBindList varBindList = comInterface.getMIBEntry(OID);
        return (SNMPSequence)varBindList.getSNMPObjectAt(0);
    }
}