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
 * Draws the table as following:
 * <p>
 * name1|value11|value12<br/>
 * name2|value21|value22<br/>
 * name3|value31|value32<br/>
 * 
 * <p>
 * Date: Nov 5, 2005
 * 
 * @author Rakesh Kalra
 */
public class VerticalHeaderHtmlTable extends AbstractTable {

	public String draw() {
		StringBuffer buff = new StringBuffer();
		buff.append("<table class=\"HtmlTable\">");
		String[] header = getHeader();
		for (int i = 0; i < header.length; i++) {
			buff.append("<tr>");
			buff.append("<td valign=\"top\" style=\"border: none;font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 11px\">");
			buff.append("<b>");
			buff.append(header[i]);
			buff.append("</b></td>");
			for (Iterator<?> it = getRows().iterator(); it.hasNext();) {
				String[] row = (String[]) it.next();
				buff.append("<td valign=\"top\" style=\"border: none;font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 11px\">");
				buff.append(row[i]);
				buff.append("</td>");
			}
			buff.append("</tr>");
		}

		buff.append("</table>");
		return buff.toString();
	}
}
