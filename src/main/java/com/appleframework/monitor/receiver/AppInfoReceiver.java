package com.appleframework.monitor.receiver;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import com.appleframework.config.core.PropertyConfigurer;
import com.appleframework.jmx.database.constant.StateType;
import com.appleframework.jmx.database.entity.AppClusterEntity;
import com.appleframework.jmx.database.entity.AppInfoEntity;
import com.appleframework.jmx.database.entity.NodeInfoEntity;
import com.appleframework.jmx.database.service.AppClusterService;
import com.appleframework.jmx.database.service.AppInfoService;
import com.appleframework.jmx.database.service.NodeInfoService;

public class AppInfoReceiver extends ReceiverAdapter {
	
	protected final static Logger logger = Logger.getLogger(AppInfoReceiver.class);
	
	private NodeInfoService nodeInfoService;
	
	private AppClusterService appClusterService;
	
	private AppInfoService appInfoService;
	
	private JChannel channel;

	public void start() throws Exception {
		boolean broadcastReceiveOpen = PropertyConfigurer.getBoolean("broadcast.receive.open", false);
		if(broadcastReceiveOpen) {
			// 创建一个通道
			channel = new JChannel();
			// 创建一个接收器
			channel.setReceiver(this);
			// 加入一个群
			channel.connect("MonitorContainer");
		}
	}

	// 覆盖父类的方法
	@Override
	public void receive(Message msg) {
		Object object = msg.getObject();
		if (object instanceof Properties) {
			
			Properties prop = (Properties) object;
			
			String appName = prop.getProperty("application.name");
			String appVersion = prop.getProperty("application.version");
			String nodeIp = prop.getProperty("node.ip");
			String nodeHost = prop.getProperty("node.host");

			String installPath = prop.getProperty("install.path");
			String webContext = prop.getProperty("web.context");
			
			String webPortStr = prop.getProperty("web.port");
			String jmxPortStr = prop.getProperty("jmx.port");
			String servicePortStr = prop.getProperty("service.port");
			
			String confGroup = prop.getProperty("deploy.group");
			String confDataId = prop.getProperty("deploy.dataId");
			String confEnv = prop.getProperty("deploy.env");
			String logLevel = prop.getProperty("log.level");
			String startParam = prop.getProperty("start.param");
			String memMax = prop.getProperty("mem.max");
			
			int webPort = 0;
			int jmxPort = 0;
			int servicePort = 0;
			
			if(null != webPortStr) {
				webPort = Integer.parseInt(webPortStr);
			}
			if(null != jmxPortStr) {
				jmxPort = Integer.parseInt(jmxPortStr);
			}
			if(null != servicePortStr) {
				servicePort = Integer.parseInt(servicePortStr);
			}
			
			NodeInfoEntity nodeInfo = nodeInfoService.saveWithHost(nodeHost, nodeIp);
			AppClusterEntity appCluster = appClusterService.saveWithName(appName);
			
			AppInfoEntity appInfo = new AppInfoEntity();
			appInfo.setAppName(appName);
			appInfo.setAppVersion(appVersion);
			appInfo.setClusterId(appCluster.getId());
			appInfo.setNodeId(nodeInfo.getId());
			appInfo.setInstallPath(installPath);
			appInfo.setRemark("");
			appInfo.setState(StateType.START.getIndex());
			appInfo.setWebContext(webContext);
			appInfo.setWebPort(webPort);
			appInfo.setServicePort(servicePort);
			appInfo.setJmxPort(jmxPort);
			appInfo.setDisorder(100);
			appInfo.setConfGroup(confGroup);
			appInfo.setConfEnv(confEnv);
			appInfo.setConfDataid(confDataId);
			appInfo.setLogLevel(logLevel);
			appInfo.setStartParam(startParam);
			appInfo.setMemMax(memMax);
			
			appInfo = appInfoService.saveOrUpdate(appInfo);
						
		} else if (object instanceof String) {
			logger.warn(object.toString());
		}
	}

	@Override
	public void viewAccepted(View new_view) {
		logger.warn("** view: " + new_view);
	}

	public void setNodeInfoService(NodeInfoService nodeInfoService) {
		this.nodeInfoService = nodeInfoService;
	}

	public void setAppClusterService(AppClusterService appClusterService) {
		this.appClusterService = appClusterService;
	}

	public void setAppInfoService(AppInfoService appInfoService) {
		this.appInfoService = appInfoService;
	}
	
}
