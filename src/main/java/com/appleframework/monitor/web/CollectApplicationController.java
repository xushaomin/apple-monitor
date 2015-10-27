package com.appleframework.monitor.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.jmx.database.constant.StateType;
import com.appleframework.jmx.database.entity.AppClusterEntity;
import com.appleframework.jmx.database.entity.AppInfoEntity;
import com.appleframework.jmx.database.entity.NodeInfoEntity;
import com.appleframework.jmx.database.service.AppClusterService;
import com.appleframework.jmx.database.service.AppInfoService;
import com.appleframework.jmx.database.service.NodeInfoService;
import com.appleframework.web.bean.Message;

@Controller
@RequestMapping("/collect")
public class CollectApplicationController extends BaseController {

	@Resource
	private NodeInfoService nodeInfoService;

	@Resource
	private AppClusterService appClusterService;

	@Resource
	private AppInfoService appInfoService;

	@RequestMapping(value = "/application")
	public @ResponseBody Message application(HttpServletRequest request) {
		try {

			String appName = request.getParameter("application.name");
			String nodeIp = request.getParameter("node.ip");
			String nodeHost = request.getParameter("node.host");

			String installPath = request.getParameter("install.path");
			String webContext = request.getParameter("web.context");

			String webPortStr = request.getParameter("web.port");
			String jmxPortStr = request.getParameter("jmx.port");
			String servicePortStr = request.getParameter("service.port");

			String confGroup = request.getParameter("deploy.group");
			String confDataId = request.getParameter("deploy.dataId");
			String confEnv = request.getParameter("deploy.env");

			int webPort = 0;
			int jmxPort = 0;
			int servicePort = 0;

			if (null != webPortStr) {
				webPort = Integer.parseInt(webPortStr);
			}
			if (null != jmxPortStr) {
				jmxPort = Integer.parseInt(jmxPortStr);
			}
			if (null != servicePortStr) {
				servicePort = Integer.parseInt(servicePortStr);
			}

			NodeInfoEntity nodeInfo = nodeInfoService.saveWithHost(nodeHost, nodeIp);
			AppClusterEntity appCluster = appClusterService.saveWithName(appName);

			AppInfoEntity appInfo = new AppInfoEntity();
			appInfo.setAppName(appName);
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

			appInfo = appInfoService.saveOrUpdate(appInfo);

			return SUCCESS_MESSAGE;
		} catch (Exception e) {
			return Message.error(e.getMessage());
		}
	}
}