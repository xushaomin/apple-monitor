package com.appleframework.monitor.jmx;

import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.MBeanServerConnection;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.remote.JMXConnectionNotification;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.rmi.RMIConnector;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.config.ApplicationConfigManager;
import com.appleframework.jmx.core.management.ObjectName;
import com.appleframework.jmx.core.management.ServerConnection;
import com.appleframework.jmx.core.management.ServerConnectionProxy;
import com.appleframework.jmx.core.management.ServerConnector;
import com.appleframework.jmx.core.modules.JMXServerConnection;
import com.appleframework.jmx.core.modules.jsr160.JSR160ServerConnection;
import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.monitor.jmx.event.JMEevntCenter;
import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.OperatingSystemMXBean;

/**
 * 
 * @author @author code_czp@126.com-2015年5月12日
 */
@SuppressWarnings("restriction")
public class JMConnManager implements NotificationListener {

	private static final Logger logger = Loggers.getLogger(JMConnManager.class);
	
    public static final String GCCMS = "java.lang:type=GarbageCollector,name=ConcurrentMarkSweep";
    public static final String GCMSC = "java.lang:type=GarbageCollector,name=MarkSweepCompact";
    public static final String GCSCAV = "java.lang:type=GarbageCollector,name=PS Scavenge";
    public static final String GCMS = "java.lang:type=GarbageCollector,name=PS MarkSweep";
    public static final String GCPARNEW = "java.lang:type=GarbageCollector,name=ParNew";
    public static final String HOTSPOTDUMP = "com.sun.management:type=HotSpotDiagnostic";
    public static final String HEAP_ITEM = "PS Survivor Space,PS Eden Space,PS Old Gen";
    public static final String GCCOPY = "java.lang:type=GarbageCollector,name=Copy";
    public static final String OSBEANNAME = "java.lang:type=OperatingSystem";
    public static final String POOLNAME = "java.lang:type=MemoryPool";
    public static final String RUNTIMNAME = "java.lang:type=Runtime";
    public static final String NONHEAP_ITEM = "Code Cache, Perm Gen";
    private static final String CLSBEANAME = "java.lang:type=ClassLoading";
    public static final String THREAD_BEAN_NAME = "java.lang:type=Threading";
    public static final String MEMORYNAME = "java.lang:type=Memory";
    
    private static ConcurrentHashMap<String, JMXConnector> conns = new ConcurrentHashMap<String, JMXConnector>();
    private static final NotificationListener INSTANCE = new JMConnManager();

    public static void addConnInfo(ApplicationConfig appConfig) {
    	JMXConnector jmxConnector = null;
    	try {
    		jmxConnector = getConnection(appConfig);
    		conns.putIfAbsent(appConfig.getName(), jmxConnector);
            JSONObject quit = new JSONObject();
            quit.put("type", "add");
            quit.put("app", appConfig.getName());
            JMEevntCenter.getInstance().send(quit);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
    }
    
    public static void init() {
    	List<ApplicationConfig> list = ApplicationConfigManager.getAllApplications();
        for (ApplicationConfig applicationConfig : list) {
            addConnInfo(applicationConfig);
        }
    }

    public static <T> T getServer(ApplicationConfig appConfig, String beanBane, Class<T> cls) throws IOException {
        T ser = ManagementFactory.newPlatformMXBeanProxy(getConn(appConfig), beanBane, cls);
        return ser;
    }

    public static ThreadMXBean getThreadMBean(ApplicationConfig appConfig) throws IOException {
        return getServer(appConfig, THREAD_BEAN_NAME, ThreadMXBean.class);
    }

    public static RuntimeMXBean getRuntimeMBean(ApplicationConfig appConfig) throws IOException {
    	
    	ServerConnection connection = null;
        try {
            connection = ServerConnector.getServerConnection(appConfig);
            RMIConnector obj1 = (RMIConnector)connection.getServerConnection();
            System.out.println(obj1);
            MBeanServerConnection obj = (MBeanServerConnection)connection.getServerConnection();
            RuntimeMXBean ser = ManagementFactory.newPlatformMXBeanProxy(obj, RUNTIMNAME, RuntimeMXBean.class);
            //if(connection instanceof JSR160ServerConnection) {
            	//ServerConnectionProxy conn = (ServerConnectionProxy)connection;
            	//conn.getMbeanServerClass()
            //}
            System.out.println(ser);
            /*javax.management.ObjectName runtimeMXBean = (javax.management.ObjectName)connection.buildObjectName(RUNTIMNAME);
            System.out.println(runtimeMXBean);
            
            ObjectName objectName = new ObjectName(RUNTIMNAME);
            Object sets = connection.getAttribute(objectName, "Name");
            System.out.println(sets);*/
        }catch(Exception e){
            logger.info("Application is down: " + appConfig.getName());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (IOException e) {
                logger.warn(e.getMessage());
            }
        }
        
        return getServer(appConfig, RUNTIMNAME, RuntimeMXBean.class);
    }

    public static MemoryMXBean getMemoryMBean(ApplicationConfig appConfig) throws IOException {
        return getServer(appConfig, MEMORYNAME, MemoryMXBean.class);
    }

    public static OperatingSystemMXBean getOSMbean(ApplicationConfig appConfig) throws IOException {
        return getServer(appConfig, OSBEANNAME, OperatingSystemMXBean.class);
    }

    public static ClassLoadingMXBean getClassMbean(ApplicationConfig appConfig) throws IOException {
        return getServer(appConfig, CLSBEANAME, ClassLoadingMXBean.class);
    }

    public static MBeanServerConnection getConn(ApplicationConfig appConfig) throws IOException {
    	JMXConnector jmxConnector = conns.get(appConfig.getAppId());
         if (jmxConnector == null) {
        	 jmxConnector = getConnection(appConfig);
        	 conns.putIfAbsent(appConfig.getAppId(), jmxConnector);
        	 return jmxConnector.getMBeanServerConnection();
         }
         else {
        	return jmxConnector.getMBeanServerConnection();
         }
    }

    public static boolean isLocalHost(String ip) {
        return "127.0.0.1".equals(ip) || "localhost".equals(ip);
    }

    public static void close() {
        Collection<JMXConnector> values = conns.values();
        for (JMXConnector jmxConnector : values) {
            try {
            	jmxConnector.close();
            } catch (IOException e) {
                logger.error("close error:" + e);
            }
        }
    }

    private static JMXConnector getConnection(ApplicationConfig appConfig) {
        try {
        	//ServerConnection connection = ServerConnector.getServerConnection(appConfig);
            Map<String, String[]> map = new HashMap<String, String[]>();
            if (appConfig.getUsername() != null && appConfig.getPassword() != null) {
                map.put(JMXConnector.CREDENTIALS, new String[] { appConfig.getUsername(), appConfig.getPassword() });
            }
            JMXConnector connector = JMXConnectorFactory.newJMXConnector(new JMXServiceURL(appConfig.getUrl()), map);
            connector.addConnectionNotificationListener(INSTANCE, null, appConfig.getName());
            connector.connect();
            return connector;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static HotSpotDiagnosticMXBean getHotspotBean(ApplicationConfig appConfig) throws IOException {
        return getServer(appConfig, HOTSPOTDUMP, HotSpotDiagnosticMXBean.class);
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {
    	logger.info(String.valueOf(handback));
        JMXConnectionNotification noti = (JMXConnectionNotification) notification;
        if (noti.getType().equals(JMXConnectionNotification.CLOSED)) {
            disconnect(String.valueOf(handback));
        } else if (noti.getType().equals(JMXConnectionNotification.FAILED)) {
            disconnect(String.valueOf(handback));
        } else if (noti.getType().equals(JMXConnectionNotification.NOTIFS_LOST)) {
            disconnect(String.valueOf(handback));
        }
    }

    private static void disconnect(String app) {
        try {
        	JMXConnector jmxConnector = conns.remove(app);
            JSONObject quit = new JSONObject();
            quit.put("type", "quit");
            quit.put("app", app);
            JMEevntCenter.getInstance().send(quit);
            jmxConnector.removeConnectionNotificationListener(INSTANCE);
            jmxConnector.close();
        } catch (Exception e) {
            logger.error("disconnect error:" + e);
        }
    }
}
