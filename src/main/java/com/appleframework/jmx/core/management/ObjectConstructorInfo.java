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
package com.appleframework.jmx.core.management;

/**
 *
 * date:  Aug 13, 2004
 * @author	Rakesh Kalra
 */
public class ObjectConstructorInfo extends ObjectFeatureInfo {

	private static final long serialVersionUID = 2570461882432124832L;

	private ObjectParameterInfo[] signature;

    public ObjectConstructorInfo(String name,
                                 String description,
                                 ObjectParameterInfo[] signature) {
        super(name, description);
        this.signature = signature;
    }

    public ObjectParameterInfo[] getSignature() {
        return signature;
    }
}
