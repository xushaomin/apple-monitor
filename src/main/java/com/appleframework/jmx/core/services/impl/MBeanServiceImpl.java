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
package com.appleframework.jmx.core.services.impl;

import org.apache.commons.beanutils.ConvertUtils;
import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.data.AttributeListData;
import com.appleframework.jmx.core.data.MBeanData;
import com.appleframework.jmx.core.data.OperationResultData;
import com.appleframework.jmx.core.management.*;
import com.appleframework.jmx.core.services.MBeanService;
import com.appleframework.jmx.core.services.ServiceContext;
import com.appleframework.jmx.core.services.ServiceException;
import com.appleframework.jmx.core.services.ServiceUtils;
import com.appleframework.jmx.core.util.*;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenType;
import java.lang.reflect.Constructor;
import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * date:  Feb 21, 2005
 * @author	Rakesh Kalra, Shashank Bellary
 */
@Service("mbeanService")
public class MBeanServiceImpl implements MBeanService {

    private static final Logger logger = Loggers.getLogger(MBeanService.class);

    private static final String DEFAULT_FILTER = "*:*";
    private static final char COMPOSITE_ATTR_SEPARATOR = '.';

    private static final ObjectName DEFAULT_FILTER_OBJECT_NAME = new ObjectName(DEFAULT_FILTER);

	public List<MBeanData> queryMBeans(ServiceContext context, String filter) throws ServiceException {
		ServerConnection serverConnection = context.getServerConnection();
		ObjectName filterObjectName = null;
		if (filter == null) {
			filterObjectName = DEFAULT_FILTER_OBJECT_NAME;
		} else {
			filterObjectName = new ObjectName(filter);
		}
		Set<ObjectName> mbeans = serverConnection.queryNames(filterObjectName);
		ArrayList<MBeanData> mbeanDataList = new ArrayList<MBeanData>(mbeans.size());
		for (ObjectName objName : mbeans) {
			mbeanDataList.add(new MBeanData(objName.getDisplayName()));
		}
		return mbeanDataList;
	}

	public Map<String, Set<String>> queryMBeansOutputMap(ServiceContext context, String filter,
                                    String[] dataTypes, String applyAttribFilter){
		List<MBeanData> mbeanDataList = null;
		if ("false".equals(applyAttribFilter)) {
			mbeanDataList = queryMBeans(context, filter);
		} else {
			mbeanDataList = queryMBeansWithAttributes(context, filter, dataTypes);
		}

        Map<String, Set<String>> domainToObjectNameListMap = new TreeMap<String, Set<String>>();
        ObjectNameTuple tuple = new ObjectNameTuple();
		for (Iterator<?> it = mbeanDataList.iterator(); it.hasNext();) {
			MBeanData mbeanData = (MBeanData) it.next();
			tuple.setObjectName(mbeanData.getName());
			String domain = tuple.getDomain();
			String name = tuple.getName();
			Set<String> objectNameList = domainToObjectNameListMap.get(domain);
			if (objectNameList == null) {
				objectNameList = new TreeSet<String>();
				domainToObjectNameListMap.put(domain, objectNameList);
			}
			objectNameList.add(name);
		}
        return domainToObjectNameListMap;
    }

	private List<MBeanData> queryMBeansWithAttributes(ServiceContext context, String filter,
                                         String[] dataTypes) throws ServiceException{
        ServerConnection serverConnection = context.getServerConnection();
        List<MBeanData> mbeans = queryMBeans(context, filter);
        List<MBeanData> mbeanToAttributesList = new ArrayList<MBeanData>();
		for (Iterator<?> itr = mbeans.iterator(); itr.hasNext();) {
            MBeanData mbeanData = (MBeanData)itr.next();
            ObjectName objName = new ObjectName(mbeanData.getName());
            try {
                ObjectInfo objInfo = serverConnection.getObjectInfo(objName);
                ObjectAttributeInfo[] objAttrInfo = objInfo.getAttributes();
                if(objAttrInfo!=null && objAttrInfo.length > 0){
                    if(dataTypes!=null && dataTypes.length > 0){
                        if(checkAttributeDataType(
                        		serverConnection, objName, objAttrInfo, dataTypes, context.getApplicationConfig(), null)){
                            mbeanToAttributesList.add(mbeanData);
                        }
                    }else{
                        mbeanToAttributesList.add(mbeanData);
                    }
                }
            } catch (Exception e) {
                /* if there is an error while getting MBean Info, continue
                    looking further */
                String errorMessage = "Error getting ObjectInfo for: " + objName + ", error=" + e.getMessage();
                logger.warn( errorMessage);
                logger.info(errorMessage, e);
            }
        }
         return mbeanToAttributesList;
    }

    /**
     *
     * @param objAttrInfo
     * @param dataTypes
     * @param appConfig
     * @param attributesList (optional) if specified, it will be populated
     *              with ObjectAttributeInfo objects that match the dataTypes
     *              specified
     * @return
     */
	private boolean checkAttributeDataType(ServerConnection connection,
                                           ObjectName objectName,
                                           ObjectAttributeInfo[] objAttrInfo,
                                           String[] dataTypes,
                                           ApplicationConfig appConfig,
                                           List<ObjectAttributeInfo> attributesList){

        boolean result = false;
        outerloop:
        for(int i=0; i<objAttrInfo.length;i++){
            ObjectAttributeInfo attrInfo = objAttrInfo[i];
            for(int j=0; j<dataTypes.length; j++){
                Class<?> attrInfoType = getClass(attrInfo.getType(), appConfig.getApplicationClassLoader());
                Class<?> dataType = getClass(dataTypes[j], this.getClass().getClassLoader());
                if(attrInfoType != null && dataType.isAssignableFrom(attrInfoType)){
                    result = true;
                    if(attributesList != null){
                        /* special handling for CompositeData */
                        if(attrInfoType.getName().equals("javax.management.openmbean.CompositeData")){
                            handleCompositeData(connection, objectName, attrInfo, dataTypes, attributesList);
                        }else{
                            attributesList.add(attrInfo);
                        }
                    }else{
                        // we found one. return true
                        break outerloop;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Expands a CompositeData object into individual attributes with the
     * naming convention:
     * <p>
     * attributeName.itemName
     * <p>
     * The items should conform to given "dataType" array. These individual
     * attributes are added to the <code>attributeList</code>
     * @param attributesList    ObjectAttributeInfo instances are added to this
     *                          list
     */
	private void handleCompositeData(ServerConnection connection,
                                     ObjectName objectName,
                                     ObjectAttributeInfo attrInfo,
                                     String[] dataTypes,
                                     List<ObjectAttributeInfo> attributesList){

        CompositeType type = getCompositeType(connection, objectName, attrInfo);
		for (Iterator<?> it = type.keySet().iterator(); it.hasNext();) {
            String itemName = (String)it.next();
            OpenType<?> itemType = type.getType(itemName);
            Class<?> itemTypeClass = getClass(itemType.getClassName(), this.getClass().getClassLoader());
			for (int j = 0; j < dataTypes.length; j++) {
				Class<?> dataType = getClass(dataTypes[j], this.getClass().getClassLoader());
                if(dataType.isAssignableFrom(itemTypeClass)){
                    attributesList.add(
                            new ObjectAttributeInfo(
                                    attrInfo.getName() + COMPOSITE_ATTR_SEPARATOR + itemName,
                                    type.getDescription(itemName),
                                    itemType.getClassName(), false, true, false));
                }
            }
        }
    }

    // todo: is there a simpler way to get CompositeType (without getting the value)
    private CompositeType getCompositeType(ServerConnection connection,
                                           ObjectName objectName,
                                           ObjectAttributeInfo attrInfo){
        CompositeData compositeData =
                (CompositeData)connection.getAttribute(objectName, attrInfo.getName());
        return compositeData.getCompositeType();
    }

    private Class<?> getClass(String type, ClassLoader classLoader){
        if(type.equals("boolean"))
             return Boolean.class;
        if(type.equals("byte"))
             return Byte.TYPE;
        if(type.equals("char"))
             return Character.class;
        if(type.equals("double"))
             return Double.class;
        if(type.equals("float"))
             return Float.class;
        if(type.equals("int"))
             return Integer.class;
        if(type.equals("long"))
             return Long.class;
        if(type.equals("short"))
             return Short.class;
        if(type.equals("void"))
             return Void.class;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(type, true, classLoader);

        } catch(ClassNotFoundException e){
            logger.info("Error finding class of type=" + type + ", error=" + e.getMessage());
        }
        return clazz;
    }

    private static class ObjectNameTuple{
        String domain;
        String name;

        void setObjectName(String canonicalName){
            int index = canonicalName.indexOf(":");
            domain = canonicalName.substring(0, index);
            name = canonicalName.substring(index + 1);
        }

        String getName(){
            return name;
        }
        String getDomain(){
            return domain;
        }
    }

    public ObjectInfo getMBeanInfo(ServiceContext context) throws ServiceException {
        canAccessThisMBean(context);
        ServerConnection serverConnection =
                ServiceUtils.getServerConnectionEvenIfCluster(context.getApplicationConfig());
        ObjectInfo objectInfo = serverConnection.getObjectInfo(context.getObjectName());
        return objectInfo;
    }

    /**
     * @return list of all attribute values
     */
    public AttributeListData[] getAttributes(ServiceContext context) throws ServiceException {
        canAccessThisMBean(context);
        ServerConnection serverConnection = null;
        try {
            serverConnection = ServiceUtils.getServerConnectionEvenIfCluster(context.getApplicationConfig());
            ObjectInfo objInfo = serverConnection.getObjectInfo(context.getObjectName());
            assert objInfo != null;
            ObjectAttributeInfo[] attributes = objInfo.getAttributes();
            List<String> attributeNames = new LinkedList<String>();
            for (int i = 0; i < attributes.length; i++) {
                if(attributes[i].isReadable()){
                    attributeNames.add(attributes[i].getName());
                }
            }
            String[] attributeArray = StringUtils.listToStringArray(attributeNames);
            return getAttributes(context, attributeArray, true);
        } finally {
            ServiceUtils.close(serverConnection);
        }
    }

    /**
     *
     * @return list of attribute values for given attributes
     */
    public AttributeListData[] getAttributes(ServiceContext context, String[] attributes, boolean handleCluster)
            throws ServiceException {
		canAccessThisMBean(context);
		ApplicationConfig appConfig = context.getApplicationConfig();
		ObjectName objectName = context.getObjectName();

		AttributeListData[] resultData = null;
        if(appConfig.isCluster()){
            if(!handleCluster){
                throw new ServiceException(ErrorCodes.OPERATION_NOT_SUPPORTED_FOR_CLUSTER);
            }
            /* we need to perform this operation for all servers in this cluster */
            resultData = new AttributeListData[appConfig.getApplications().size()];
            int index = 0;
            for(ApplicationConfig childAppConfig: appConfig.getApplications()){
				try {
					resultData[index] = getAttributes(context, childAppConfig, objectName, attributes);
				} catch (ConnectionFailedException e) {
					resultData[index] = new AttributeListData(childAppConfig.getName());
				}
            }
        } else{
            resultData = new AttributeListData[1];
            resultData[0] = getAttributes(context, appConfig, objectName, attributes);
        }
        return resultData;
    }

    /**
     * Returns ObjectAttribute instance containing the value of the given
     * attribute. This method also handles CompositeData item with the
     * attribute naming convention:
     * <p>
     * Building.NumberOfFloors
     * <p>
     * where "Building" is the composite attribute name and "NumberOfFloors"
     * is the item name in the "Building" composite type.
     *
     * @param context
     * @param attribute
     * @return
     * @throws ServiceException
     */
    public ObjectAttribute getObjectAttribute(ServiceContext context, String attribute) throws ServiceException{
        assert context.getObjectName() != null;
        assert context.getApplicationConfig() != null;
        canAccessThisMBean(context);
        // we don't support clustering here
        assert !context.getApplicationConfig().isCluster();

        ServerConnection connection = context.getServerConnection();
        List<ObjectAttribute> attrList = connection.getAttributes(context.getObjectName(), new String[]{attribute});
        ObjectAttribute attrValue = (ObjectAttribute)attrList.get(0);
        if(attrValue.getStatus() != ObjectAttribute.STATUS_NOT_FOUND){
            return attrValue;
        }

        /* see if this is a composite attribute */
        String attributeName = null;
        String itemName = null;
        int index = attribute.indexOf(COMPOSITE_ATTR_SEPARATOR);
        if(index == -1)
            return attrValue;

        // composite data attribute
        itemName = attribute.substring(index + 1);
        attributeName = attribute.substring(0, index);

        attrList = connection.getAttributes(context.getObjectName(), new String[]{attributeName});
        attrValue = (ObjectAttribute)attrList.get(0);
        if(attrValue.getStatus() == ObjectAttribute.STATUS_OK){
            CompositeData compositeData = (CompositeData)attrValue.getValue();
            attrValue = new ObjectAttribute(attribute, compositeData.get(itemName));
        }
        return attrValue;
    }

    /**
     * @return list of attribute values for given attributes
     */
    private AttributeListData getAttributes(ServiceContext context,
                                            ApplicationConfig appConfig,
                                            ObjectName objectName,
                                            String[] attributes)
            throws ConnectionFailedException {

        // TODO: we should hide the attributes in the jsp
        for(int attrCount = 0; attrCount < attributes.length; attrCount++){
        }
        ServerConnection connection = null;
        try {
            connection = ServerConnector.getServerConnection(appConfig);
            List<ObjectAttribute> attrList = connection.getAttributes(objectName, attributes);
            return new AttributeListData(appConfig.getName(), attrList);
        } finally {
            ServiceUtils.close(connection);
        }
    }

    public List<ObjectAttributeInfo> filterAttributes(ServiceContext context, ObjectName objectName,
                                 ObjectAttributeInfo[] objAttrInfo, String[] dataTypes){
        List<ObjectAttributeInfo> objAttrInfoList = new LinkedList<ObjectAttributeInfo>();
        checkAttributeDataType(context.getServerConnection(), objectName, objAttrInfo, dataTypes,
                context.getApplicationConfig() ,objAttrInfoList);
         return objAttrInfoList;
    }

    public OperationResultData[] invoke(ServiceContext context, String operationName, String[] params)
            throws ServiceException {
        /* try to determine the method, based on params */
        ObjectOperationInfo operationInfo =
                findOperation(context, operationName, params != null ? params.length : 0);
        return invoke(context, operationName, params, operationInfo.getParameters());
    }

    /**
     * Invokes MBean operation
     * @return
     * @throws ServiceException
     */
    public OperationResultData[] invoke(ServiceContext context,
                                        String operationName,
                                        String[] params,
                                        String[] signature)
            throws ServiceException {
        return invoke(context, context.getObjectName(), operationName, params, signature);
    }
    
    /**
     * Invokes MBean operation
     * @return
     * @throws ServiceException
     */
    public OperationResultData[] invoke(ServiceContext context,
                                        ObjectName objectName,
                                        String operationName,
                                        String[] params,
                                        String[] signature)
        throws ServiceException{
        
        canAccessThisMBean(context, objectName);

        ApplicationConfig appConfig = context.getApplicationConfig();
        OperationResultData[] resultData = null;
        if(appConfig.isCluster()){
            /* we need to perform this operation for all servers
                in this cluster */
            resultData = new OperationResultData[appConfig.getApplications().size()];
            int index = 0;
            for(Iterator<?> it=appConfig.getApplications().iterator(); it.hasNext(); index++){
                ApplicationConfig childAppConfig = (ApplicationConfig)it.next();
                resultData[index] =
                        executeMBeanOperation(context, childAppConfig, objectName, operationName, params, signature);
            }
        }else{
            resultData = new OperationResultData[1];
            resultData[0] = executeMBeanOperation(context, appConfig, objectName, operationName, params, signature);
        }
        return resultData;
    }
    

    private static OperationResultData executeMBeanOperation(
            ServiceContext context,
            ApplicationConfig appConfig,
            ObjectName objectName,
            String operationName,
            String[] params,
            String[] signature){

        OperationResultData resultData = new OperationResultData(appConfig.getName());
        ServerConnection serverConnection = null;
        try {
            serverConnection = ServerConnector.getServerConnection(appConfig);
            Object[] typedParams = getTypedArray(appConfig, params, signature);
            final Object result = serverConnection.invoke(objectName, operationName, typedParams, signature);

            resultData.setOutput(result);
        } catch (ConnectionFailedException e) {
            logger.info( "Error executing operation " + operationName + " on " + objectName, e);
            resultData.setResult(OperationResultData.RESULT_ERROR);
            resultData.setErrorString(ErrorCatalog.getMessage(ErrorCodes.CONNECTION_FAILED));
        } catch (RuntimeException e){
            logger.error( "Error executing operation " + operationName + " on " + objectName, e);
            resultData.setResult(OperationResultData.RESULT_ERROR);
            if(e.getCause() != null){
                if(e.getCause().getClass().getName().
                        equals("javax.management.RuntimeMBeanException") &&
                        e.getCause().getCause() != null){
                    resultData.setException(e.getCause().getCause());
                }else{
                    resultData.setException(e.getCause());
                }
            }else{
                resultData.setException(e);
            }
        } catch (Exception e){
            logger.error( "Error executing operation " + operationName + " on " + objectName, e);
            resultData.setResult(OperationResultData.RESULT_ERROR);
            resultData.setException(e);
        } finally {
            ServiceUtils.close(serverConnection);
        }
        return resultData;
    }

    private ObjectOperationInfo findOperation(ServiceContext context, String operationName, int paramCount){
        ObjectName objectName = context.getObjectName();
        ServerConnection connection = null;
        try {
            connection = ServiceUtils.getServerConnectionEvenIfCluster(context.getApplicationConfig());
            ObjectInfo objectInfo = connection.getObjectInfo(objectName);
            ObjectOperationInfo[] operationInfo = objectInfo.getOperations();
            for(int i=0; i< operationInfo.length; i++){
                if(operationInfo[i].getName().equals(operationName) &&
                        operationInfo[i].getSignature().length == paramCount){
                    return operationInfo[i];
                }
            }
            throw new ServiceException(ErrorCodes.INVALID_MBEAN_OPERATION, operationName, objectName);
        } finally {
            ServiceUtils.close(connection);
        }
    }

    //TODO: should we first check that all apps in a cluster are up,
    // before updating?  - rk
    public AttributeListData[] setAttributes(ServiceContext context, String[][] attributes) throws ServiceException{
        canAccessThisMBean(context);
        List<ApplicationConfig> applications = getApplications(context.getApplicationConfig());
        ObjectName objectName = context.getObjectName();
        List<ObjectAttribute> attributeList = buildAttributeList(context, attributes);
        AttributeListData[] attrListData = new AttributeListData[applications.size()];
        int index = 0;
        for(Iterator<?> it=applications.iterator(); it.hasNext(); index++){
            final ApplicationConfig childAppConfig = (ApplicationConfig)it.next();
            attrListData[index] = updateAttributes(context, childAppConfig, objectName, attributeList);
        }
        return attrListData;
    }

    /**
     * Updates MBean attributes at a stand alone application level or at a
     * cluster level.
     * <p>
     * The attributes element contains the keys in the format:
     * attr+<applicationId>+<attrName>+<attrType>
     * <p>
     * todo: improve this interface (currently written for webui)
     *
     *
     * @param context
     * @param attributes Map containing
     * @throws ServiceException
     */
    public AttributeListData[] setAttributes(ServiceContext context, Map<String, String[]> attributes) throws ServiceException{
        canAccessThisMBean(context);
        List<ApplicationConfig> applications = getApplications(context.getApplicationConfig());
        ObjectName objectName = context.getObjectName();
        AttributeListData[] attrListData = new AttributeListData[applications.size()];
        int index = 0;

        ServerConnection connection =
                ServiceUtils.getServerConnectionEvenIfCluster(context.getApplicationConfig());
        try {
            ObjectInfo objInfo = connection.getObjectInfo(objectName);
            for(Iterator<?> it=applications.iterator(); it.hasNext(); index++){
                final ApplicationConfig childAppConfig = (ApplicationConfig)it.next();
                List<ObjectAttribute> attributeList = buildAttributeList(attributes,
                        childAppConfig, objInfo.getAttributes(), connection, objectName);
                attrListData[index] = updateAttributes(context, childAppConfig, objectName, attributeList);
            }
        } finally {
            ServiceUtils.close(connection);
        }
        return attrListData;
    }

    public Map<String, ObjectNotificationInfo[]> queryMBeansWithNotifications(ServiceContext context)
            throws ServiceException {

        ServerConnection serverConnection = context.getServerConnection();
        Set<ObjectName> mbeans = serverConnection.queryNames(DEFAULT_FILTER_OBJECT_NAME);
        Map<String, ObjectNotificationInfo[]> mbeanToNoficationsMap = 
        	new TreeMap<String, ObjectNotificationInfo[]>();
        for(Iterator<?> it=mbeans.iterator(); it.hasNext(); ){
            ObjectName objName = (ObjectName)it.next();

            try {
                ObjectInfo objInfo = serverConnection.getObjectInfo(objName);
                ObjectNotificationInfo[] notifications = objInfo.getNotifications();
                if(notifications != null && notifications.length > 0){
                    mbeanToNoficationsMap.put(objName.getDisplayName(), notifications);
                }
            } catch (Exception e) {
                /* if there is an error while getting MBean Info, continue
                    looking further */
                String errorMessage = "Error getting ObjectInfo for: " +
                        objName + ", error=" + e.getMessage();
                logger.warn( errorMessage);
                logger.info( errorMessage, e);
            }
        }
        return mbeanToNoficationsMap;
    }
    private AttributeListData updateAttributes(ServiceContext context,
                                               ApplicationConfig appConfig,
                                               ObjectName objectName,
                                               List<ObjectAttribute> attributeList){
        for(Iterator<?> attrIterator = attributeList.iterator(); attrIterator.hasNext();){
            ObjectAttribute objAttr = (ObjectAttribute)attrIterator.next();
            System.out.println(objAttr);
        }
        AttributeListData attrListData = null;
        ServerConnection serverConnection = null;
        try{
            serverConnection = ServerConnector.getServerConnection(appConfig);
            attributeList = serverConnection.setAttributes(objectName, attributeList);
            attrListData = new AttributeListData(appConfig.getName(), attributeList);
            String logString = getLogString(attributeList);
            System.out.println(logString);
        }catch(ConnectionFailedException e){
            logger.info( "Error connecting to :" + appConfig.getName(), e);
            attrListData = new AttributeListData(appConfig.getName());
        }finally{
            ServiceUtils.close(serverConnection);
        }
        return attrListData;
    }

    /**
     * Converts a two dimentional String array containing attribute name and
     * value to a list of ObjectAttribute objects.
     *
     * @return list containing ObjectAttribute objects
     */
    private List<ObjectAttribute> buildAttributeList(ServiceContext context, String[][] attributes){
        ObjectName objectName;
        ObjectInfo objInfo;
        ServerConnection connection = null;
        try {
            connection = ServiceUtils.getServerConnectionEvenIfCluster(context.getApplicationConfig());
            objectName = context.getObjectName();
            objInfo = connection.getObjectInfo(objectName);
        } finally {
            ServiceUtils.close(connection);
        }

        ObjectAttributeInfo[] objAttributes = objInfo.getAttributes();
        List<ObjectAttribute> attributeList = new LinkedList<ObjectAttribute>();
        for(int i=0; i<attributes.length; i++){
            String attribute = attributes[i][0];
            String type = getAttributeType(connection, objAttributes, attribute, objectName);
            /* ensure that this attribute is writable */
            ensureAttributeIsWritable(objAttributes, attribute, objectName);

            Object value = getTypedValue(context.getApplicationConfig(), attributes[i][1], type);
            ObjectAttribute objAttribute = new ObjectAttribute(attribute, value);
            attributeList.add(objAttribute);
        }
        return attributeList;
    }

    // handles composite attribute type
    private String getAttributeType(ServerConnection connection,
                                    ObjectAttributeInfo[] objAttributes,
                                    String attribute,
                                    ObjectName objectName){

        /* first look for normal attribute */
        for(int i=0; i<objAttributes.length; i++){
            if(objAttributes[i].getName().equals(attribute)){
                return objAttributes[i].getType();
            }
        }

        /* now look for CompositeData */
        String itemName = null;
        final int index = attribute.indexOf(COMPOSITE_ATTR_SEPARATOR);
        if(index != -1){
            itemName = attribute.substring(index + 1);
            attribute = attribute.substring(0, index);
            for(int i=0; i<objAttributes.length; i++){
                if(objAttributes[i].getName().equals(attribute)){
                    // it is a CompositeData type
                    CompositeType type = getCompositeType(connection, objectName, objAttributes[i]);
                    return type.getType(itemName).getClassName();
                }
            }
        }
        throw new ServiceException(ErrorCodes.INVALID_MBEAN_ATTRIBUTE,
                attribute, objectName);
    }

    private void ensureAttributeIsWritable(ObjectAttributeInfo[] objAttributes, String attribute, ObjectName objectName){
        ObjectAttributeInfo attributeInfo = null;
        for(int i=0; i<objAttributes.length; i++){
            if(objAttributes[i].getName().equals(attribute)){
                attributeInfo = objAttributes[i];
                break;
            }
        }
        assert attributeInfo != null :"attribute not found:" + attribute;
        if(!attributeInfo.isWritable()){
            throw new ServiceException(ErrorCodes.READ_ONLY_MBEAN_ATTRIBUTE, attribute, objectName);
        }
    }


    private List<ApplicationConfig> getApplications(ApplicationConfig appConfig){
        List<ApplicationConfig> applications = null;
        if(appConfig.isCluster()){
            applications = appConfig.getApplications();
        }else{
            applications = new ArrayList<ApplicationConfig>(1);
            applications.add(appConfig);
        }
        return applications;
    }

    /**
     * Map keys are of the format:
     * attr+<applicationId>+<attrName>
     *
     */
    private List<ObjectAttribute> buildAttributeList(Map<String, String[]> attributes,
                                    ApplicationConfig appConfig,
                                    ObjectAttributeInfo[] objAttributes,
                                    ServerConnection connection,
                                    ObjectName objectName){

        String applicationId = appConfig.getApplicationId();
        Iterator<String> it = attributes.keySet().iterator();
        List<ObjectAttribute> attributeList = new LinkedList<ObjectAttribute>();
        while(it.hasNext()){
            String param = it.next();
            // look for keys which only start with "attr+"
            if(param.startsWith("attr+")){
                StringTokenizer tokenizer = new StringTokenizer(param, "+");
                if(tokenizer.countTokens() != 3){
                    throw new RuntimeException("Invalid param name: " + param);
                }
                tokenizer.nextToken(); // equals to "attr"
                if(applicationId.equals(tokenizer.nextToken())){ // applicationId
                    String attrName = tokenizer.nextToken();
                    String attrType = getAttributeType(connection, objAttributes, attrName, objectName);

                    String[] attrValues = attributes.get(param);
                    Object typedValue = null;
                    if(attrType.startsWith("[")){
                        // it is an array
                        // the first elements in the array is dummy to allow
                        //  empty string to be saved
                        String[] actualValue = new String[attrValues.length - 1];
                        for(int i=0;i<actualValue.length;i++){
                            actualValue[i] = attrValues[i+1];
                        }
                        try {
                            typedValue = ConvertUtils.convert(actualValue, Class.forName(attrType));
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        typedValue = getTypedValue(appConfig, attrValues[0], attrType);
                    }
                    attributeList.add(new ObjectAttribute(attrName, typedValue));
                }
            }
        }
        return attributeList;
    }

    /**
     *
     * @param attributes
     * @return
     */
    private String getLogString(List<ObjectAttribute> attributes){
        StringBuffer logString = new StringBuffer("");
        for(Iterator<?> iterator = attributes.iterator(); iterator.hasNext(); ){
            ObjectAttribute attribute = (ObjectAttribute)iterator.next();
            logString.append(" [");
            logString.append(attribute.getName());
            logString.append("=");
            logString.append(attribute.getValue());
            logString.append("]");
        }
        return logString.toString();
    }


    private void canAccessThisMBean(ServiceContext context){
        canAccessThisMBean(context, context.getObjectName());
    }
    
    private void canAccessThisMBean(ServiceContext context, ObjectName objectName){
        final ApplicationConfig config = context.getApplicationConfig();
        //final MBeanConfig configuredMBean = config.findMBeanByObjectName(objectName.getCanonicalName());
        config.findMBeanByObjectName(objectName.getCanonicalName());
    }
        

    public static Object getTypedValue(ApplicationConfig appConfig, String value, String type){

        if(type.equals("int")){
            type = "java.lang.Integer";
        }else if(type.equals("long")){
            type = "java.lang.Long";
        }else if(type.equals("short")){
            type = "java.lang.Short";
        }else if(type.equals("float")){
            type = "java.lang.Float";
        }else if(type.equals("double")){
            type = "java.lang.Double";
        }else if(type.equals("char")){
            type = "java.lang.Character";
        }else if(type.equals("boolean")){
            type = "java.lang.Boolean";
        }else if(type.equals("byte")){
            type = "java.lang.Byte";
        }

        try {
            /* handle ObjectName as a special type */
            if(type.equals("javax.management.ObjectName")){
                Class<?> clazz = Class.forName(type, true, appConfig.getApplicationClassLoader());
                try {
                    Constructor<?> ctor = clazz.getConstructor(new Class[]{String.class});
                    return ctor.newInstance(new Object[]{value});
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            /* other types */
            return ConvertUtils.convert(value, Class.forName(type));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object[] getTypedArray(ApplicationConfig appConfig, String[] values, String[] type){
        Object[] obj = new Object[values.length];
        for(int i=0; i<values.length; i++){
            obj[i] = getTypedValue(appConfig, values[i], type[i]);
        }
        return obj;
    }

    public ObjectOperationInfo getOperationInfo(ServiceContext context, String operationName, String[] signature)
            throws ServiceException {

        ObjectOperationInfo operationInfo = null;
        ObjectInfo objectInfo = getMBeanInfo(context);
        ObjectOperationInfo[] operations = objectInfo.getOperations();

        for(int index = 0; index < operations.length; index++) {
            operationInfo = operations[index];

            if(operationName.equals(operationInfo.getName())) {
                int matchCounter = 0;
                ObjectParameterInfo[] objectSignature = operationInfo.getSignature();

                if(signature.length ==  objectSignature.length) {
                    for(int paramIndex = 0; paramIndex < objectSignature.length; paramIndex++) {
                        if(signature[paramIndex].equals(objectSignature[paramIndex].getType())) {
                            matchCounter++;
                        }
                    }

                    if(matchCounter == signature.length) {
                        break;
                    }
                }
            }
        }
        return operationInfo;
    }

    public String getAttributeDataType(ServiceContext context, String attributeName, String objectName){
        ServerConnection connection = context.getServerConnection();
        ObjectName objName = new ObjectName(objectName);
        ObjectInfo objectInfo = connection.getObjectInfo(objName);
        ObjectAttributeInfo[] objAttrInfo = objectInfo.getAttributes();
        return getAttributeType(connection, objAttrInfo, attributeName, objName);
    }
}
