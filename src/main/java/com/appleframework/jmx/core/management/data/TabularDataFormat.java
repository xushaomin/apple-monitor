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

import com.appleframework.jmx.util.display.Table;

import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularType;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.CompositeData;
import java.util.*;

/**
 *
 * <p>
 * Date:  Oct 1, 2005
 * @author	Rakesh Kalra
 */
public abstract class TabularDataFormat implements DataFormat {

	public String format(Object data) {
        TabularData tabularData = (TabularData) data;

        TabularType type = tabularData.getTabularType();
        CompositeType rowType = type.getRowType();

        /* get the header and index names */
        Set<String> itemNamesSet = rowType.keySet();
        String[] itemNames = (String[])itemNamesSet.toArray(new String[itemNamesSet.size()]);

        final List<String> indexNamesList = tabularData.getTabularType().getIndexNames();
        final String[] indexNames =
              (String[])indexNamesList.toArray(new String[indexNamesList.size()]);

        /* move index names at the begining of the item names (if not the case)*/
        for(int i=0; i<indexNames.length; i++){
            if(!itemNames[i].equals(indexNames[i])){
                // find where is the indexNames[i]
                int index = find(itemNames, indexNames[i]);
                if(index != -1){
                    // swap
                    itemNames[index] = itemNames[i];
                    itemNames[i] = indexNames[i];
                }
            }
        }

        /* sort the CompositeData objects by the index values */
        /* Sorting logic contributed by Jess Holle (SF id: jess_hole)*/
        final int dataRowsCount = tabularData.size();
        final TabularDataData  tabularDataData[] = new TabularDataData[dataRowsCount];
        int  ii = 0;
        for (Iterator<?> it = tabularData.values().iterator(); it.hasNext();) {
            CompositeData compositeData = (CompositeData) it.next();
            final Object[] indices = compositeData.getAll(indexNames);
            tabularDataData[ii++] = new TabularDataData(indices, compositeData);
        }
        Arrays.sort(tabularDataData);

        /* get the rows */
        List<String[]> rows = new LinkedList<String[]>();
        for (int i=0; i < dataRowsCount; i++) {
            CompositeData compositeData = tabularDataData[i].compositeData;
            String[] itemValues = new String[itemNames.length];
            for (int j = 0; j < itemNames.length; j++) {
                Object value = compositeData.get(itemNames[j]);
                itemValues[j] = DataFormatUtil.format(value);
            }
            rows.add(itemValues);
        }

        /* draw the table */
        Table table = getTable();
        table.setHeader(itemNames);
        table.addRows(rows);
        return table.draw();
    }

    private int find(String[] array, String item){
        for(int i=0; i<array.length; i++){
            if(array[i].equals(item)){
                return i;
            }
        }
        return -1;
    }


    // provided by Jess Hole
    @SuppressWarnings("rawtypes")
	private static class TabularDataData implements Comparable {

        TabularDataData(final Object indices[], final CompositeData compositeData) {
            this.indices = indices;
            this.compositeData = compositeData;
        }

        @SuppressWarnings("unchecked")
		public int compareTo(Object data2) {
            for (int ii = 0; ii < indices.length; ++ii) {
                final Object indexData = indices[ii];
                final Object indexData2 = ((TabularDataData)data2).indices[ii];
                if (indexData == null)
                    if (indexData2 == null)
                        continue;  // two nulls are always considered equal
                    else
                        return (-1);  // we'll always consider a null less than a non-null
                final int comparison = ((indexData instanceof Comparable) ?
                        ((Comparable) indexData).compareTo(indexData2)
                        : indexData.toString().compareTo(indexData2.toString()));
                if (comparison != 0)
                    return (comparison);
            }
            return (0);
        }

        final Object indices[];
        final CompositeData compositeData;
    }

    protected abstract Table getTable();
}

