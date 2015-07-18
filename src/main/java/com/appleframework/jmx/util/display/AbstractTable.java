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
package com.appleframework.jmx.util.display;

import java.util.List;
import java.util.LinkedList;

/**
 *
 * <p>
 * Date:  Sep 29, 2005
 * @author	Rakesh Kalra
 */
public abstract class AbstractTable implements Table {

    private String[] header;
    private List<String[]> rows = new LinkedList<String[]>();

    public void setHeader(String[] header) {
        this.header = header;
    }

    public void addRow(String[] row) {
        rows.add(row);
    }

    public void addRows(List<String[]> rows) {
        this.rows.addAll(rows);
    }

    protected String[] getHeader(){
        return header;
    }

    protected List<String[]> getRows(){
        return rows;
    }
}
