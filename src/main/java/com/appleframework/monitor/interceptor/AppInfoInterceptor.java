package com.appleframework.monitor.interceptor;

import java.text.MessageFormat;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.appleframework.core.utils.ObjectUtility;
import com.appleframework.jmx.core.config.ApplicationClusterConfig;
import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.config.ApplicationConfigManager;
import com.appleframework.jmx.core.modules.jsr160.JSR160ApplicationConfig;
import com.appleframework.jmx.database.constant.StateType;
import com.appleframework.jmx.database.entity.AppClusterEntity;
import com.appleframework.jmx.database.entity.AppInfoEntity;
import com.appleframework.jmx.database.entity.NodeInfoEntity;
import com.appleframework.jmx.database.service.AppClusterService;
import com.appleframework.jmx.database.service.AppDowntimeHistoryService;
import com.appleframework.jmx.database.service.AppDowntimeService;
import com.appleframework.jmx.database.service.AppInfoService;
import com.appleframework.jmx.database.service.NodeInfoService;
import com.appleframework.jmx.monitoring.downtime.ApplicationDowntimeService;
import com.appleframework.monitor.task.AlertTask;

@Component
@Aspect
public class AppInfoInterceptor {
	
	private final static Logger logger = LoggerFactory.getLogger(AppInfoInterceptor.class);
	
	@Resource
	private AppInfoService appInfoService;
	
	@Resource
	private NodeInfoService nodeInfoService;
	
	@Resource
	private AppClusterService appClusterService;
	
	@Resource
	private ApplicationConfigManager applicationConfigManager;
	
	@Resource
	private ApplicationDowntimeService applicationDowntimeService;
	
	@Resource
	private AppDowntimeService appDowntimeService;
	
	@Resource
	private AppDowntimeHistoryService appDowntimeHistoryService;

	@Resource
	private ThreadPoolTaskExecutor taskExecutor;

	//应用新增或修改
	@AfterReturning(value="execution(* com.appleframework.jmx.database.service.impl.AppInfoServiceImpl.saveOrUpdate(..))", 
			argNames="rtv", returning="rtv")
	public void afterAddMethod(JoinPoint jp, final Object rtv) {
		AppInfoEntity entity = (AppInfoEntity)rtv;
		logger.info("entity=" + entity.toString());
		addQueue(entity.getId());
    }
	
	//应用新增或修改
	@AfterReturning(value="execution(* com.appleframework.jmx.database.service.impl.AppInfoServiceImpl.delete(..))", 
			argNames="rtv", returning="rtv")
	public void afterDeleteMethod(JoinPoint jp, final Object rtv) {
		Integer id = (Integer)rtv;
		logger.info("id=" + id.toString());
		addQueue(id);
	}
	
	public void addQueue(final Integer id) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					processQueue(id);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
	}
	
	private void processQueue(Integer id) throws Exception {
		AppInfoEntity appInfo = appInfoService.get(id);
		try {
			if (ObjectUtility.isEmpty(appInfo))
				return;
			if (appInfo.getState() == StateType.START.getIndex()) {
				AppClusterEntity appCluster = appClusterService.get(appInfo.getClusterId());
				NodeInfoEntity nodeInfo = nodeInfoService.get(appInfo.getNodeId());

				ApplicationClusterConfig clusterConfig = new ApplicationClusterConfig(String.valueOf(appCluster.getId()), appCluster.getClusterName());

				String url = MessageFormat.format(JSR160ApplicationConfig.URL_FORMAT, nodeInfo.getIp(),String.valueOf(appInfo.getJmxPort()));

				ApplicationConfig appConfig = new JSR160ApplicationConfig();
				appConfig.setApplicationId(appInfo.getId() + "");
				appConfig.setHost(nodeInfo.getHost());
				appConfig.setName(appCluster.getClusterName());
				appConfig.setType("jsr160");
				appConfig.setPort(appInfo.getJmxPort());
				appConfig.setUrl(url);
				appConfig.setUsername(null);
				appConfig.setPassword(null);
				appConfig.setCluster(false);
				appConfig.setClusterConfig(clusterConfig);

				try {
					applicationConfigManager.addOrUpdateApplication(appConfig);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				try {
					applicationDowntimeService.addOrUpdateApplication(appConfig);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			} else {
				try {
					applicationConfigManager.deleteApplication(id.toString());
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				try {
					applicationDowntimeService.deleteApplication(id.toString());
				} catch (Exception e) {
					logger.error(e.getMessage());
				}

				try {
					appDowntimeHistoryService.deleteByAppId(id);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}

				try {
					appDowntimeService.delete(id);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}

			}
			appClusterService.calibratedAppNum(appInfo.getClusterId());
			AlertTask.sendCountMap.remove(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}
	
}
