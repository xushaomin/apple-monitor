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

import java.util.Iterator;

/**
 * 
 * <p>
 * Date: Sep 29, 2005
 * 
 * @author Rakesh Kalra
 */
public class HtmlTable extends AbstractTable {

	public String draw() {
		StringBuffer buff = new StringBuffer();
		buff.append("<table class=\"HtmlTable\">");
		drawHeader(buff);
		for (Iterator<?> it = getRows().iterator(); it.hasNext();) {
			drawRow(buff, (String[]) it.next());
		}
		buff.append("</table>");
		return buff.toString();
	}

	private void drawHeader(StringBuffer buff) {
		buff.append("<tr>");
		String[] header = getHeader();
		for (int i = 0; i < header.length; i++) {
			buff.append("<td valign=\"top\" style=\"border: none;font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 11px\">");
			buff.append("<b>");
			buff.append(header[i]);
			buff.append("</b></td>");
		}
		buff.append("</tr>");
	}

	private void drawRow(StringBuffer buff, String[] row) {
		buff.append("<tr>");
		for (int i = 0; i < row.length; i++) {
			buff.append("<td valign=\"top\" style=\"border: none;font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 11px\">");
			buff.append(row[i]);
			buff.append("</td>");
		}
		buff.append("</tr>");
	}
}
