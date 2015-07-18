/**
 * Copyright 2004-2006 jManage.org
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
package com.appleframework.jmx.core.management.metadata;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import com.appleframework.jmx.core.management.ObjectAttributeInfo;
import com.appleframework.jmx.core.management.ObjectInfo;
import com.appleframework.jmx.core.management.ObjectName;
import com.appleframework.jmx.core.management.ObjectOperationInfo;
import com.appleframework.jmx.core.management.ObjectParameterInfo;
import com.appleframework.jmx.core.management.ServerConnection;
import com.appleframework.jmx.core.util.CoreUtils;
import com.appleframework.jmx.core.util.Loggers;

/**
 * 
 * @author Rakesh Kalra
 */
public class Repository {

    private static Logger logger = Loggers.getLogger(Repository.class);
    private static Map<ObjectName, ObjectInfo> mbeanToObjectInfoMap = new HashMap<ObjectName, ObjectInfo>();
    
    public static ObjectInfo applyMetaData(ObjectInfo objInfo, ServerConnection connection){
        ObjectInfo metaObjectInfo = mbeanToObjectInfoMap.get(objInfo.getObjectName());
        if(metaObjectInfo != null){
            objInfo.applyMetaData(metaObjectInfo, 
                    new ExpressionProcessor(connection, objInfo.getObjectName()));
        }
        return objInfo;
    }
    
    static{
        try {
            load();
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
        	throw new RuntimeException(e);
		}
    }
    
    @SuppressWarnings("unchecked")
	private static void load() throws JDOMException, IOException{
		File metadataRepository = new File(CoreUtils.getConfigDir() + "/mbeans");
		assert metadataRepository.isDirectory() : "repository not found";
		File[] metadataFiles = metadataRepository.listFiles(new XMLFilter());
		for (File file : metadataFiles) {
			Document config = new SAXBuilder().build(file);
			List<Element> mbeans = config.getRootElement().getChildren();
			for (Iterator<?> it = mbeans.iterator(); it.hasNext();) {
				Element mbean = (Element) it.next();
				ObjectInfo objInfo = getObjectInfo(mbean);
				ObjectInfo oldObjInfo = mbeanToObjectInfoMap.put(objInfo.getObjectName(), objInfo);
				if (oldObjInfo != null) {
					logger.warn("Duplicate mbean found: " + oldObjInfo.getObjectName().toString());
				}
			}
		}
    }
    
    private static ObjectInfo getObjectInfo(Element mbean){
        ObjectName objectName = new ObjectName(mbean.getAttributeValue("name"));
        String description = mbean.getAttributeValue("description");
        ObjectAttributeInfo[] attributes = getObjectAttributeInfo(mbean);
        ObjectOperationInfo[] operations = getObjectOperationInfo(mbean);
        return new ObjectInfo(objectName, null, description, attributes, null, operations, null);
    }
    
    private static ObjectAttributeInfo[] getObjectAttributeInfo(Element mbean){
        
        List attributes = mbean.getChildren("attribute");
        ObjectAttributeInfo[] attributeInfo = new ObjectAttributeInfo[attributes.size()];
        int index = 0;
        for(Iterator it = attributes.iterator(); it.hasNext(); index++){
            Element attribute = (Element)it.next();
            attributeInfo[index] = new ObjectAttributeInfo(attribute.getAttributeValue("name"),
                                attribute.getAttributeValue("description"),
                                null, // not overridden
                                false,// not overridden
                                false,// not overridden
                                false);// not overridden
            attributeInfo[index].setUnits(attribute.getAttributeValue("units"));
        }
        return attributeInfo;
    }
    
    private static ObjectOperationInfo[] getObjectOperationInfo(Element mbean){
        
        List operations = mbean.getChildren("operation");
        ObjectOperationInfo[] operationInfo = new ObjectOperationInfo[operations.size()];
        int index = 0;
        for(Iterator it = operations.iterator(); it.hasNext(); index++){
            Element operation = (Element)it.next();
            operationInfo[index] = new ObjectOperationInfo(operation.getAttributeValue("name"),
                    operation.getAttributeValue("description"),
                    getSignature(operation),
                    null, // not overridden
                    0);   // not overridden
            operationInfo[index].setUnits(operation.getAttributeValue("units"));
        }
        return operationInfo;
    }
    
    private static ObjectParameterInfo[] getSignature(Element operation){
        List parameters = operation.getChildren("parameter");
        ObjectParameterInfo[] parameterInfo = new ObjectParameterInfo[parameters.size()];
        int index = 0;
        for(Iterator it = parameters.iterator(); it.hasNext(); index++){
            Element parameter = (Element)it.next();
            parameterInfo[index] = new ObjectParameterInfo(parameter.getAttributeValue("name"),
                    parameter.getAttributeValue("description"),
                    parameter.getAttributeValue("type"),
                    parameter.getAttributeValue("legalValues"));
            parameterInfo[index].setUnits(parameter.getAttributeValue("units"));
        }
        return parameterInfo;
    }
    
    private static class XMLFilter implements FilenameFilter {
	public boolean accept(File dir, String name) {
	    return (name.endsWith(".xml") || name.endsWith(".XML"));
	}
    }
}
