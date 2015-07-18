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

package com.appleframework.jmx.core.auth;

/**
 * Date: Mar 14, 2005 12:32:38 AM
 * @author Shashank Bellary 
 */
public class UnAuthorizedAccessException extends RuntimeException{
    
	private static final long serialVersionUID = 6452798002989567729L;
	
	private String message;

    public UnAuthorizedAccessException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
