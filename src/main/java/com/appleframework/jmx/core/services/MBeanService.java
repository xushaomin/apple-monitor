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
package com.appleframework.jmx.core.services;

import com.appleframework.jmx.core.management.*;
import com.appleframework.jmx.core.data.MBeanData;
import com.appleframework.jmx.core.data.OperationResultData;
import com.appleframework.jmx.core.data.AttributeListData;

import java.util.List;
import java.util.Map;

/**
 *
 * date:  Feb 21, 2005
 * @author	Rakesh Kalra
 * @author  Shashank Bellary
 */
public interface MBeanService {

    public List<MBeanData> queryMBeans(ServiceContext context, String filter)
            throws ServiceException;

    /**
     * if datatypes is null then Map will contain all the MBeans
     * if datatype is not null then only Mbeans thats have attributes of matching
     * data types will be returned
     * @param context
     * @param filter
     * @param dataTypes
     * @return
     */
    public Map queryMBeansOutputMap(ServiceContext context, String filter,
                                    String[] dataTypes, String applyAttribFilter);

    /**
     * Gets the MBean information.
     *
     * @param context   instance of ServiceContext
     * @return  instance of ObjectInfo
     * @throws ServiceException
     */
    public ObjectInfo getMBeanInfo(ServiceContext context)
            throws ServiceException;

    /**
     * @return list of all attribute values
     */
    public AttributeListData[] getAttributes(ServiceContext context)
            throws ServiceException;

    public AttributeListData[] getAttributes(ServiceContext context,
                                             String[] attributes,
                                             boolean handleCluster)
            throws ServiceException;

    public List filterAttributes(ServiceContext context,
                                 ObjectName objectName,
                                 ObjectAttributeInfo[] objAttrInfo,
                                 String[] dataTypes)
            throws ServiceException;

    public ObjectAttribute getObjectAttribute(ServiceContext context,
                                              String attribute)
            throws ServiceException;

    /**
     * Invokes MBean operation
     * @return
     * @throws ServiceException
     */
    public OperationResultData[] invoke(ServiceContext context,
                                        String operationName,
                                        String[] params)
            throws ServiceException;

    /**
     * Invokes MBean operation
     * @return
     * @throws ServiceException
     */
    public OperationResultData[] invoke(ServiceContext context,
                                        String operationName,
                                        String[] params,
                                        String[] signature)
        throws ServiceException;


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
        throws ServiceException;

    
    
    public AttributeListData[] setAttributes(ServiceContext context,
                                             String[][] attributes)
            throws ServiceException;

    /**
     * Updates MBean attributes at a stand alone application level or at a
     * cluster level.
     *
     * @param context
     * @param attributes
     * @throws ServiceException
     */
    public AttributeListData[] setAttributes(ServiceContext context,
                                             Map attributes)
            throws ServiceException;

    public Map<String, ObjectNotificationInfo[]> queryMBeansWithNotifications(ServiceContext context)
            throws ServiceException;
    /**
     * returns data type of an attribute
     * @param context
     * @param attributeName
     * @param objectName
     * @return
     * @throws ServiceException
     */
    public String getAttributeDataType(ServiceContext context,
                                       String attributeName,
                                       String objectName)
            throws ServiceException;

    public ObjectOperationInfo getOperationInfo(ServiceContext context,
                                       String operationName,
                                       String[] signature)
            throws ServiceException;
}
