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

import com.appleframework.jmx.core.util.StringUtils;

import java.util.Iterator;
import java.util.List;

/**
 * 
 * <p>
 * Date: Sep 29, 2005
 * 
 * @author Rakesh Kalra
 */
public class TextTable extends AbstractTable {

	private static final int COLUMN_SPACING = 5;

	/* contains the max column sizes */
	private int[] columnSize;

	public String draw() {

		String[] header = getHeader();
		List<String[]> rows = getRows();

		/* compute column sizes */
		columnSize = new int[header.length];
		setColumnSize(header);
		for (Iterator<?> it = rows.iterator(); it.hasNext();) {
			setColumnSize((String[]) it.next());
		}

		/* draw */
		StringBuffer buff = new StringBuffer();

		/* print header */
		printRow(header, buff);
		printUnderline(header, buff);

		for (Iterator<?> it = rows.iterator(); it.hasNext();) {
			printRow((String[]) it.next(), buff);
		}
		return buff.toString();
	}

	private void setColumnSize(String[] row) {
		for (int i = 0; i < row.length; i++) {
			if (row[i].length() > columnSize[i])
				columnSize[i] = row[i].length();
		}
	}

	private void printRow(String[] rows, StringBuffer buff) {
		for (int i = 0; i < rows.length; i++) {
			String columnValue = StringUtils.padRight(rows[i], columnSize[i] + COLUMN_SPACING);
			buff.append(columnValue);
		}
		buff.append(System.getProperty("line.separator"));
	}

	private void printUnderline(String[] rows, StringBuffer buff) {
		for (int i = 0; i < rows.length; i++) {
			String underline = StringUtils.getCharSeries('-', rows[i].toString().length());
			underline = StringUtils.padRight(underline, columnSize[i] + COLUMN_SPACING);
			buff.append(underline);
		}
		buff.append(System.getProperty("line.separator"));
	}
}
