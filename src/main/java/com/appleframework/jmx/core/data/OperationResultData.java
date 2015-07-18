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
package com.appleframework.jmx.core.data;

import com.appleframework.jmx.core.management.data.DataFormatUtil;

import java.io.StringWriter;
import java.io.PrintWriter;

/**
 *
 * date:  Jan 23, 2005
 * @author	Rakesh Kalra
 */
public class OperationResultData implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final int RESULT_OK = 0;
    public static final int RESULT_ERROR =1;

    private String appName;
    private Object output;
    private int result = RESULT_OK;
    private String errorString;
    private String stackTrace;

    public OperationResultData(String appName){
        this.appName = appName;
    }

    public String getApplicationName(){
        return appName;
    }

    public Object getOutput() {
        return output;
    }

    public String getDisplayOutput(){
        return DataFormatUtil.format(getOutput());
    }

    public void setOutput(Object output) {
        this.output = output;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public void setException(Throwable e){
        setErrorString(e.getMessage());
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        this.stackTrace = writer.toString();
    }

    public String getStackTrace(){
        return stackTrace;
    }

    public boolean isError(){
        return result == RESULT_ERROR;
    }
}
