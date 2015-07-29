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
package com.appleframework.jmx.core.management.data.jdk;

import com.appleframework.jmx.core.management.data.DataFormat;
import com.appleframework.jmx.util.display.TextTable;
import com.appleframework.jmx.util.display.Table;

import javax.management.openmbean.CompositeData;

/**
 * Formatter for "java.lang.management.MemoryUsage" CompositeType.
 *
 * Used: 1,515 KB
 * Committed: 1,984 KB
 * Max: 65,088 KB
 * Initial: 0 KB
 * <p>
 * Date:  Dec 22, 2005
 * @author	Rakesh Kalra
 */
public class MemoryUsageFormat implements DataFormat {

    public String format(Object data) {

        CompositeData compositeData = (CompositeData)data;
        if(!compositeData.getCompositeType().getTypeName().equals("java.lang.management.MemoryUsage")){
            throw new RuntimeException("Invalid typeName:" + compositeData.getCompositeType().getTypeName());
        }

        Table table = getTable();
        table.setHeader(new String[]{"Used", "Committed", "Max", "Initial"});
        String[] values = new String[4];
        values[0] = getKBytes(compositeData.get("used"));
        values[1] = getKBytes(compositeData.get("committed"));
        values[2] = getKBytes(compositeData.get("max"));
        values[3] = getKBytes(compositeData.get("init"));
        table.addRow(values);
        return table.draw();
    }

    protected Table getTable(){
        return new TextTable();
    }

    private String getKBytes(Object bytes){
        return Long.toString(((Long)bytes).longValue()/1024L) + " KB";
    }
}