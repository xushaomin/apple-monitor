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
package com.appleframework.jmx.util.display.html;

/**
 * <SELECT MULTIPLE="true" NAME="interests" SIZE="4"> <OPTION VALUE="arts"> Arts
 * <OPTION VALUE="pol" > Politics <OPTION VALUE="sci" > Science <OPTION
 * VALUE="comp"> Computers and Internet </SELECT>
 * 
 * @author Rakesh Kalra
 */
public class Select extends CompositeElement {

	public Select(String name) {
		this(name, 1);
	}

	public Select(String name, int size) {
		this(name, 1, false);
	}

	public Select(String name, int size, boolean multiple) {
		super("SELECT");
		addAttribute("name", name);
		addAttribute("size", String.valueOf(size));
		if (multiple)
			addAttribute("multiple", "true");
	}

	public void onChange(String function) {
		addAttribute("onChange", function);
	}

	public void addOption(String id) {
		addOption(id, id);
	}

	public void addOption(String id, String value) {
		addChildElement(new Option(id, value));
	}

	private class Option extends Element {
		@SuppressWarnings("unused")
		String id;
		@SuppressWarnings("unused")
		String value;

		Option(String id, String value) {
			super("OPTION", value);
			addAttribute("VALUE", id);
		}
	}
}
