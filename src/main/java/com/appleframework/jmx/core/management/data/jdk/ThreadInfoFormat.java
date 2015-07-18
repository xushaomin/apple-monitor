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

import javax.management.openmbean.CompositeData;

/**
 *
 * Name: main (id=1)
 * State: TIMED_WAITING
 * Total blocked: 0  Total waited: 0
 *
 * Stack trace:
 * java.lang.Thread.sleep(Native Method)
 * JConsoleTest.main(JConsoleTest.java:13)
 *
 * <p>
 * Date:  Dec 9, 2005
 * @author	Rakesh Kalra
 */
public class ThreadInfoFormat implements DataFormat {

    private static final DataFormat stackTraceFormatter =
            new StackTraceElementFormat();

    public String format(Object data) {

        CompositeData compositeData = (CompositeData)data;
        if(!compositeData.getCompositeType().getTypeName().
                equals("java.lang.management.ThreadInfo")){
            throw new RuntimeException("Invalid typeName:" +
                    compositeData.getCompositeType().getTypeName());
        }

        StringBuffer threadInfo = new StringBuffer();
        threadInfo.append("Name: ");
        threadInfo.append(compositeData.get("threadName"));
        threadInfo.append(" (id=");
        threadInfo.append(compositeData.get("threadId"));
        threadInfo.append(")\n");
        threadInfo.append("State: ");
        String threadState = (String)compositeData.get("threadState");
        threadInfo.append(threadState);
        if(threadState.equals("WAITING")){
            threadInfo.append(" on ");
            threadInfo.append(compositeData.get("lockName"));
        }
        threadInfo.append("\n");
        threadInfo.append("Total blocked: ");
        threadInfo.append(compositeData.get("blockedCount"));
        threadInfo.append("    Total waited: ");
        threadInfo.append(compositeData.get("waitedCount"));
        threadInfo.append("\n");
        CompositeData[] stackTrace =
                (CompositeData[])compositeData.get("stackTrace");

        threadInfo.append("Strack trace:\n");
        threadInfo.append(stackTraceFormatter.format(stackTrace));
        threadInfo.append("\n\n");
        return threadInfo.toString();
    }
}
