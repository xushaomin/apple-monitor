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
package com.appleframework.jmx.net.protocol.resource;

import java.net.URLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;

/**
 *
 * Date:  Jun 6, 2005
 * @author	Rakesh Kalra
 */
public class ResourceURLConnection extends URLConnection {

    public ResourceURLConnection(final URL url) throws MalformedURLException, IOException {
      super(url);
   }

    public void connect() throws IOException {
        throw new RuntimeException("This is a dummy ResourceURLConnection.connect is not supported.");
    }
}
