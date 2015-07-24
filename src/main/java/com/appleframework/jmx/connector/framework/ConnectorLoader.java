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
package com.appleframework.jmx.connector.framework;

import com.appleframework.jmx.core.util.CoreUtils;
import com.appleframework.jmx.core.util.Loggers;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.log4j.Logger;

/**
 * The loader scans the connector folder and loads the connector metadata
 * from the connector.xml file.
 * 
 * @author	Tak-Sang Chan
 */
public class ConnectorLoader {

    private static final Logger logger = Loggers.getLogger(ConnectorLoader.class);
    
    private static final String CONNECTOR_XML = "connector.xml";

    private int count;
    private List<URL> urls;
    private List<String> archiveNames;
    private List<InputStream> configInputStreams;

    public ConnectorLoader() {
        initConnector();
    }

    public URL[] getUrls() {
        URL[]arrUrl = new URL[urls.size()];
        urls.toArray(arrUrl);
        return arrUrl;
    }

    public String[] getArchiveNames() {
        String[] names = new String[archiveNames.size()];
        archiveNames.toArray(names);
        return names;
    }

    public InputStream[] getConfigInputStreams() {
        InputStream[] streams = new InputStream[configInputStreams.size()];
        configInputStreams.toArray(streams);
        return streams;
    }

    public int getCount() {
        return count;
    }

    @SuppressWarnings("deprecation")
	private void initConnector() {
        try {
            File connDir = new File(CoreUtils.getConnectorDir());
            if (connDir.exists()) {
                File[] files = connDir.listFiles(new ArchiveFilter());

                if (files.length > 0) {
                    urls = new ArrayList<URL>();
                    archiveNames = new ArrayList<String>();
                    configInputStreams = new ArrayList<InputStream>();

                    for (File file : files) {
                        InputStream xmlStream = getConnectorXmlInputStream(file);
                        if (xmlStream != null) {
                            urls.add(file.toURL());
                            archiveNames.add(file.getName());
                            configInputStreams.add(getConnectorXmlInputStream(file));
                            count++;
                        }
                        else {
                            logger.warn("Cannot not find " + "connector.xml in " + file.getAbsolutePath());
                        }
                    }
                }
            }
        }
        catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Loops through the jar or directory and returns an InputStream for
     * the connector.xml file.
     * 
     * @param file
     * @return InputStream for connector.xml or null if the xml file
     *         does not exist.
     */
    private InputStream getConnectorXmlInputStream(File file) {
        try {
            if (isJarFile(file)) {
                JarFile jar = new JarFile(file);
                Enumeration<?> e = jar.entries();
                JarEntry entry;
                while (e.hasMoreElements()) {
                    entry = (JarEntry) e.nextElement();
                    if ((!entry.isDirectory()) && (entry.getName().endsWith(CONNECTOR_XML))) {
                        InputStream is = jar.getInputStream(entry);
                        return is;
                    }
                }
            }
            else {
                File xmlFile = new File(file.getAbsolutePath() + File.separator + CONNECTOR_XML);
                return new FileInputStream(xmlFile);
            }
            return null;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    private boolean isJarFile(File file) {
        return file.getName().endsWith(".jar");
    }

    private static class ArchiveFilter implements FileFilter {
        
        /**
         * @param pathname
         * @return Returns true when the pathname points to a jar file or directory
         */
        public boolean accept(File pathname) {
            if (pathname.isDirectory() || pathname.getName().endsWith(".jar")) {
                return true;
            }
            return false;
        }
    }
}
