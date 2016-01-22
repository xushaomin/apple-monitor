package com.appleframework.monitor.interceptor;

import java.text.MessageFormat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
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
import com.appleframework.jmx.database.service.AppInfoService;
import com.appleframework.jmx.database.service.NodeInfoService;
import com.appleframework.jmx.monitoring.downtime.ApplicationDowntimeService;

@Component
@Aspect
@Lazy(false)
public class AppInfoInterceptor {
	
	private final static Logger logger = LoggerFactory.getLogger(AppInfoInterceptor.class);

	private volatile boolean running = true;

	private Thread clusterThread;

	private BlockingQueue<Integer> clusterQueue;
	
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
		
	
	@PostConstruct
	public void init() {
		clusterQueue = new LinkedBlockingQueue<Integer>(100);
		clusterThread = new Thread(new Runnable() {
			public void run() {
				while (running) {
					try {
						processQueue(); // 发送
					} catch (Throwable t) { // 防御性容错
						logger.error(
								"Unexpected error occur at write stat log, cause: " + t.getMessage(), t);
						try {
							Thread.sleep(1000); // 失败延迟
						} catch (Throwable t2) {
						}
					}
				}
			}
		});
		clusterThread.setDaemon(true);
		clusterThread.setName("CLUSTER_THREAD");
		clusterThread.start();
	}
	
	@PreDestroy
	public void close() {
		try {
			running = false;
			clusterQueue.offer(new Integer(0));
		} catch (Throwable t) {
			logger.warn(t.getMessage(), t);
		}
	}
	
	public void addQueue(Integer id) {
		try {
			clusterQueue.offer(id);
		} catch (Exception e) {

		}
	}
	
	private void processQueue() throws Exception {
		Integer id = clusterQueue.take();
		AppInfoEntity appInfo = appInfoService.get(id);
		try {
			if(ObjectUtility.isEmpty(appInfo))
				return;
			if(appInfo.getState() == StateType.START.getIndex()) {
				AppClusterEntity appCluster = appClusterService.get(appInfo.getClusterId());
				NodeInfoEntity nodeInfo = nodeInfoService.get(appInfo.getNodeId());
				
				ApplicationClusterConfig clusterConfig 
				= new ApplicationClusterConfig(String.valueOf(appCluster.getId()), appCluster.getClusterName());
			
				String url = MessageFormat.format(JSR160ApplicationConfig.URL_FORMAT, 
						nodeInfo.getIp(), String.valueOf(appInfo.getJmxPort()));
				
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
			}
			else {
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
			}
			appClusterService.calibratedAppNum(appInfo.getClusterId());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}
	
}
