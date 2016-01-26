package com.appleframework.monitor.jmx;

import java.io.IOException;
import java.lang.Thread.State;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.config.ApplicationConfigManager;
import com.sun.management.OperatingSystemMXBean;

/***
 * 
 * @author coder_czp@126.com
 *
 */
public class JMServerInfo {

    public static JSONObject doDeadlockCheck(ApplicationConfig appConfig) {
        try {
            ThreadMXBean tBean = JMConnManager.getThreadMBean(appConfig);
            JSONObject json = new JSONObject();
            long[] dTh = tBean.findDeadlockedThreads();
            if (dTh != null) {
                ThreadInfo[] threadInfo = tBean.getThreadInfo(dTh, Integer.MAX_VALUE);
                StringBuffer sb = new StringBuffer();
                for (ThreadInfo info : threadInfo) {
                    sb.append("\n").append(info);
                }
                json.put("hasdeadlock", true);
                json.put("info", sb);
                return json;
            }
            json.put("hasdeadlock", false);
            return json;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject doLoadThreadInfo(ApplicationConfig appConfig) {
        try {
            ThreadMXBean tBean = JMConnManager.getThreadMBean(appConfig);
            ThreadInfo[] allThreads = tBean.dumpAllThreads(false, false);

            JSONObject root = new JSONObject();
            JSONArray detail = new JSONArray();
            HashMap<State, Integer> state = new HashMap<Thread.State, Integer>();
            for (ThreadInfo info : allThreads) {
                JSONObject th = new JSONObject();
                long threadId = info.getThreadId();
                long cpu = tBean.getThreadCpuTime(threadId);
                State tState = info.getThreadState();

                th.put("id", threadId);
                th.put("state", tState);
                th.put("name", info.getThreadName());
                th.put("cpu", TimeUnit.NANOSECONDS.toMillis(cpu));
                detail.add(th);

                Integer vl = state.get(tState);
                if (vl == null) {
                    state.put(tState, 0);
                } else {
                    state.put(tState, vl + 1);
                }
            }

            root.put("state", state);
            root.put("detail", detail);
            root.put("total", tBean.getThreadCount());
            root.put("time", System.currentTimeMillis());
            root.put("deamon", tBean.getDaemonThreadCount());

            return root;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject doLoadRuntimeInfo(ApplicationConfig appConfig) {
        try {
            RuntimeMXBean mBean = JMConnManager.getRuntimeMBean(appConfig);
            ClassLoadingMXBean cBean = JMConnManager.getClassMbean(appConfig);
            Map<String, String> props = mBean.getSystemProperties();
            DateFormat format = DateFormat.getInstance();
            List<String> input = mBean.getInputArguments();
            Date date = new Date(mBean.getStartTime());

            TreeMap<String, Object> data = new TreeMap<String, Object>();

            data.put("apppid", mBean.getName());
            data.put("startparam", input.toString());
            data.put("starttime", format.format(date));
            data.put("classLoadedNow", cBean.getLoadedClassCount());
            data.put("classUnloadedAll", cBean.getUnloadedClassCount());
            data.put("classLoadedAll", cBean.getTotalLoadedClassCount());
            data.putAll(props);

            JSONObject json = new JSONObject(true);
            json.putAll(data);
            return json;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String doVMGC(ApplicationConfig appConfig) {
        try {
            JMConnManager.getMemoryMBean(appConfig).gc();
            return "success";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject doLoadMonitorData(ApplicationConfig appConfig) {
        try {
            long now = System.currentTimeMillis();
            JSONObject data = new JSONObject();

            JSONObject gc = geGCInfo(appConfig);
            JSONObject cpu = findCpuInfo(appConfig);
            JSONObject memory = loadMemoryInfo(appConfig);

            data.put("gc", gc);
            data.put("cpu", cpu);
            data.put("time", now);
            data.put("memory", memory);

            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONArray doRequestLoadCluster(ApplicationConfig appConfig) {
    	List<ApplicationConfig> apps = ApplicationConfigManager.getAllApplications();
        JSONArray tree = new JSONArray();
        for (ApplicationConfig app : apps) {
            JSONObject node = new JSONObject();
            node.put("host", app.getHost());
            node.put("port", app.getPort());
            node.put("text", app.getName());
            node.put("cluster", app.getName());
            tree.add(node);
        }
        return tree;
    }


    public static JSONObject loadMemoryInfo(ApplicationConfig appConfig) {
        try {
            MemoryMXBean mBean = JMConnManager.getMemoryMBean(appConfig);
            MemoryUsage nonHeap = mBean.getNonHeapMemoryUsage();
            MemoryUsage heap = mBean.getHeapMemoryUsage();

            JSONObject map = new JSONObject(true);
            buildMemoryJSon(heap, "heap", map);
            buildMemoryJSon(nonHeap, "nonheap", map);

            JSONObject heapChild = new JSONObject();
            JSONObject nonheapChild = new JSONObject();

            JSONObject heapUsed = new JSONObject();
            JSONObject heapMax = new JSONObject();
            heapUsed.put("used", heap.getUsed());
            heapMax.put("used", heap.getCommitted());
            heapChild.put("HeapUsed", heapUsed);
            heapChild.put("HeapCommit", heapMax);

            JSONObject nonheapUsed = new JSONObject();
            JSONObject noheapMax = new JSONObject();
            nonheapUsed.put("used", nonHeap.getUsed());
            noheapMax.put("used", nonHeap.getCommitted());

            nonheapChild.put("NonheapUsed", nonheapUsed);
            nonheapChild.put("NonheapCommit", noheapMax);

            ObjectName obj = new ObjectName("java.lang:type=MemoryPool,*");
            MBeanServerConnection conn = JMConnManager.getConn(appConfig);
            Set<ObjectInstance> MBeanset = conn.queryMBeans(obj, null);
            for (ObjectInstance objx : MBeanset) {
                String name = objx.getObjectName().getCanonicalName();
                String keyName = objx.getObjectName().getKeyProperty("name");
                MemoryPoolMXBean bean = JMConnManager.getServer(appConfig, name, MemoryPoolMXBean.class);
                JSONObject item = toJson(bean.getUsage());
                if (JMConnManager.HEAP_ITEM.contains(keyName)) {
                    heapChild.put(keyName, item);
                } else {
                    nonheapChild.put(keyName, item);
                }
            }
            map.getJSONObject("heap").put("childs", heapChild);
            map.getJSONObject("nonheap").put("childs", nonheapChild);

            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	public static JSONObject findCpuInfo(ApplicationConfig appConfig) {
        try {
            JSONObject map = new JSONObject(true);
            OperatingSystemMXBean os = JMConnManager.getOSMbean(appConfig);
            map.put("os", (long) (os.getSystemCpuLoad() * 100));
            map.put("vm", (long) (os.getProcessCpuLoad() * 100));
            map.put("cores", (long) (os.getAvailableProcessors()));
            map.put("freememory", os.getFreePhysicalMemorySize());
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject geGCInfo(ApplicationConfig appConfig) throws Exception {
        ObjectName obj = new ObjectName("java.lang:type=GarbageCollector,*");
        MBeanServer conn = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectInstance> MBeanset = conn.queryMBeans(obj, null);
        Class<GarbageCollectorMXBean> cls = GarbageCollectorMXBean.class;
        JSONObject data = new JSONObject();
        for (ObjectInstance objx : MBeanset) {
            String name = objx.getObjectName().getCanonicalName();
            String keyName = objx.getObjectName().getKeyProperty("name");
            GarbageCollectorMXBean gc = ManagementFactory.newPlatformMXBeanProxy(conn, name, cls);
            data.put(keyName + "-time", gc.getCollectionTime() / 1000.0);
            data.put(keyName + "-count", gc.getCollectionCount());
        }
        return data;
    }

    private static void buildMemoryJSon(MemoryUsage useage, String name, JSONObject map) {
        JSONObject item = toJson(useage);
        map.put(name, item);
    }

    private static JSONObject toJson(MemoryUsage useage) {
        JSONObject item = new JSONObject();
        item.put("commit", useage.getCommitted());
        item.put("used", useage.getUsed());
        item.put("init", useage.getInit());
        item.put("max", useage.getMax());
        return item;
    }

}
