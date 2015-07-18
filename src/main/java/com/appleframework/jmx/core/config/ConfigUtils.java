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
package com.appleframework.jmx.core.config;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.File;

/**
 *
 * <p>
 * Date:  Sep 25, 2005
 * @author	Rakesh Kalra
 */
public class ConfigUtils {

    @SuppressWarnings("deprecation")
	public static URL[] getClassPath(File dir){
        assert dir.isDirectory();
        try {
            File[] files = dir.listFiles();
            URL[] urls = new URL[files.length];
            for(int i=0; i<files.length; i++){
                urls[i] = files[i].toURL();
            }
            return urls;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
