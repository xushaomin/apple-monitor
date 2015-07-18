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
package com.appleframework.jmx.core.management.data;

import com.appleframework.jmx.core.util.Loggers;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;
import java.util.*;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * Utility class to provide data formatting.
 *
 * <p>
 * Date:  Sep 27, 2005
 * @author	Rakesh Kalra
 */
public class DataFormatUtil {

    private static final Logger logger = Loggers.getLogger(DataFormatUtil.class);

    private static final String FORMAT_PREFIX = "format.";
    private static final String COMPOSITE_TYPE_FORMAT_PREFIX = "CompositeTypeFormat.";

    private static final String LIST_DELIMITER = "list.delimiter";
    private static final String ESCAPE_HTML = "escape.html";
    private static final String NULL_VALUE = "null.value";

    private static Object[][] classToFormatMapping = new Object[0][2];
    private static Map<String, DataFormat> compositeTypeToFormatMapping = 
    	new HashMap<String, DataFormat>();

    // properties
    private static String listDelimiter = System.getProperty("line.separator");
    private static boolean escapeHtml = true;
    private static String nullValue = "";
    private static int formatCount = 0;

    static{
        String formatFile =
                System.getProperty("com.appleframework.jmx.core.management.data.formatConfig");
        if(formatFile != null){
            try {
                InputStream is = new FileInputStream(formatFile);
                Properties props = new Properties();
                props.load(is);
                is.close();
                // note that array is bigger than number of formatters
                classToFormatMapping = new Object[props.keySet().size()][2];
                for(Iterator it=props.keySet().iterator(); it.hasNext();){
                    String property = (String)it.next();
                    if(property.startsWith(FORMAT_PREFIX)){
                        String className = property.substring(FORMAT_PREFIX.length());
                        // todo: it will be better to load the data class using the application classloader
                        // the data format class can be loaded in the base classloader - rk
                        Class clazz = Class.forName(className);
                        classToFormatMapping[formatCount][0] = clazz;
                        classToFormatMapping[formatCount][1] =
                                Class.forName(props.getProperty(property)).newInstance();
                        formatCount ++;
                    }else if(property.startsWith(COMPOSITE_TYPE_FORMAT_PREFIX)){
                        String compositeType = property.substring(COMPOSITE_TYPE_FORMAT_PREFIX.length());
                        DataFormat formatter =
                                (DataFormat)Class.forName(props.getProperty(property)).newInstance();
                        compositeTypeToFormatMapping.put(compositeType,
                                formatter);
                    }else if(property.equals(LIST_DELIMITER)){
                        listDelimiter = props.getProperty(property);
                    }else if(property.equals(ESCAPE_HTML)){
                        escapeHtml =
                                Boolean.valueOf(props.getProperty(property)).booleanValue();
                    }else if(property.equals(NULL_VALUE)){
                        nullValue = props.getProperty(property);
                    }else{
                        logger.warn("Unrecognized property=" + property);
                    }
                }
            } catch (IOException e) {
                logger.error( "Error reading data format config file:" +
                        formatFile + ". DataFormatUtil is not initialized.", e);
            } catch (Exception e) {
                logger.error( "Config file:" + formatFile +
                        ". DataFormatUtil is not initialized.", e);
            }
        }
    }

    public static String getListDelimiter(){
        return listDelimiter;
    }

    public static boolean isEscapeHtml(){
        return escapeHtml;
    }

    public static String getNullDisplayValue(){
        return nullValue;
    }

    public static String format(Object data){
        /* if data is null, return the configured null value */
        if(data == null) return nullValue;

        /* check for a DataFormat for given type (normal object or array)*/
        DataFormat formatter = findDataFormat(data);
        if(formatter != defaultFormatter){
            return formatter.format(data);
        }else if(data.getClass().isArray()){
            /* see if there is a formatter configured for the elements of the
                array. This assumes that the array has the same elements. */
            return formatArray(data);
        }
        /* format with the default formatter */
        return defaultFormatter.format(data);
    }

    private static String formatArray(Object data){
        assert data.getClass().isArray();
        final int arrayLength = Array.getLength(data);
        if(arrayLength == 0){
            return "There are no elements in the array.";
        }
        DataFormat formatter = findDataFormat(Array.get(data, 0));
        StringBuffer buff = new StringBuffer();
        for(int i=0; i<arrayLength; i++){
            if(i > 0){
                buff.append(listDelimiter);
            }
            Object element = Array.get(data, i);
            if(element != null)
                buff.append(formatter.format(element));
            else
                buff.append(nullValue);
        }
        return buff.toString();
    }

    private static final DefaultDataFormat defaultFormatter =
            new DefaultDataFormat();

    private static DataFormat findDataFormat(Object data){
        /* first check if this data is CompositeData type */
        if(CompositeData.class.isInstance(data)){
            CompositeType type = ((CompositeData)data).getCompositeType();
            DataFormat dataFormat =
                    (DataFormat)compositeTypeToFormatMapping.get(type.getTypeName());
            if(dataFormat != null)
                return dataFormat;
        }
        /* now look for other formatters */
        for(int i=0; i<formatCount; i++){
            Class clazz = (Class)classToFormatMapping[i][0];
            if(clazz.isInstance(data)){
                return (DataFormat)classToFormatMapping[i][1];
            }
        }
        return defaultFormatter;
    }
}
