package com.appleframework.monitor.web;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.jmx.core.config.ApplicationClusterConfig;
import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.config.ApplicationConfigManager;
import com.appleframework.jmx.core.modules.jsr160.JSR160ApplicationConfig;
import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.jmx.database.entity.AppClusterEntity;
import com.appleframework.jmx.database.entity.AppInfoEntity;
import com.appleframework.jmx.database.entity.NodeInfoEntity;
import com.appleframework.jmx.database.service.AppClusterService;
import com.appleframework.jmx.database.service.AppInfoService;
import com.appleframework.jmx.database.service.NodeInfoService;
import com.appleframework.jmx.monitoring.downtime.ApplicationDowntimeHistory;
import com.appleframework.jmx.monitoring.downtime.ApplicationDowntimeService;
import com.appleframework.jmx.monitoring.downtime.ApplicationHeartBeatThread;
import com.appleframework.jmx.monitoring.downtime.DowntimeRecorder;

@Controller
@RequestMapping("/app_config")
public class AppConfigController extends BaseController {
	
	/*ApplicationDowntimeService service = new ApplicationDowntimeService();
    List applications = ApplicationConfigManager.getApplications();*/

    private static final Logger logger = Loggers.getLogger(AppConfigController.class);

	@Resource
	private AppInfoService appInfoService;
	
	@Resource
	private AppClusterService appClusterService;
	
	@Resource
	private NodeInfoService nodeInfoService;
	
	/*@Resource
	private AppConfigService appConfigService;

	@Resource
	private ConfigReader configReader;*/
	
	@Resource
	private ApplicationConfigManager applicationConfigManager;
	
	@Resource
	private ApplicationDowntimeService applicationDowntimeService;
	
	private String viewModel = "app_config/";
	
	@RequestMapping(value = "/reset")
	public String reset(Model model, HttpServletRequest request) {
		
		List<NodeInfoEntity> nodeInfoList = nodeInfoService.findAll();
		List<AppClusterEntity> appClusterList = appClusterService.findAll();
		//List<AppInfoEntity> appInfoList = appInfoService.findAll();
		
		Map<String, NodeInfoEntity> nodeInfoMap = new HashMap<String, NodeInfoEntity>();
		for (NodeInfoEntity nodeInfo : nodeInfoList) {
			nodeInfoMap.put(nodeInfo.getId() + "", nodeInfo);
		}
		Map<String, AppClusterEntity> appClusterMap = new HashMap<String, AppClusterEntity>();
		for (AppClusterEntity appCluster : appClusterList) {
			appClusterMap.put(appCluster.getId() + "", appCluster);
		}
				
    	for (AppClusterEntity appCluster : appClusterList) {
    		Integer clusterId = appCluster.getId();
    		String clusterName = appCluster.getClusterName();
    		
    		ApplicationClusterConfig clusterConfig 
    			= new ApplicationClusterConfig(String.valueOf(clusterId), clusterName);
    		//applicationClusterConfig.setCluster(Boolean.valueOf(appCluster.getIsCluster() + ""));
    		
    		List<AppInfoEntity> appInfoEntityList = appInfoService.findListByClusterId(clusterId);
    		

			for (AppInfoEntity appInfoEntity : appInfoEntityList) {
				NodeInfoEntity nodeInfoEntity = nodeInfoMap.get(appInfoEntity.getNodeId().toString());
				
    			String url = MessageFormat
    					.format(JSR160ApplicationConfig.URL_FORMAT, 
    							nodeInfoEntity.getIp(), appInfoEntity.getJmxPort().toString());			
				
				ApplicationConfig appConfig = new JSR160ApplicationConfig();
				appConfig.setApplicationId(appInfoEntity.getId() + "");
				appConfig.setHost(nodeInfoEntity.getHost());
				appConfig.setName(appInfoEntity.getAppName());
				appConfig.setType("jsr160");
				appConfig.setPort(appInfoEntity.getJmxPort());
				appConfig.setUrl(url);
				appConfig.setUsername(null);
				appConfig.setPassword(null);
				appConfig.setCluster(false);
				appConfig.setClusterConfig(clusterConfig);
							
				try {
					applicationConfigManager.addOrUpdateApplication(appConfig);
					
					applicationDowntimeService.addOrUpdateApplication(appConfig);
				} catch (Exception e) {
					logger.error(e);
				}
			}		
    	}
		return viewModel + "list";
	}
	

	
	
	@RequestMapping(value = "/all")
	public @ResponseBody List<ApplicationConfig> all(Model model, HttpServletRequest request) {
		
		List<ApplicationConfig> list1 = ApplicationConfigManager.getAllApplications();
			
		for (ApplicationConfig config : list1) {
			System.out.println("ApplicationConfigManager--->" + config.getAppId() + "----->>>" +config.getURL());
		}

		System.out.println();
		List<ApplicationHeartBeatThread> list2 = applicationDowntimeService.getThreads();
		for (ApplicationHeartBeatThread applicationHeartBeatThread : list2) {
			ApplicationConfig config = applicationHeartBeatThread.getApplicationConfig();
			System.out.println("ApplicationHeartBeatThread--->" + config.getAppId() + "----->>>" +config.getURL());
		}
		System.out.println();
		
		DowntimeRecorder downtimeRecorder = applicationDowntimeService.getDowntimeRecorder();
		Map<ApplicationConfig, ApplicationDowntimeHistory> map = downtimeRecorder.getDowntimesMap();
		
		for (ApplicationConfig config : map.keySet()) {
			System.out.println("applicationDowntimeService--->" + config.getAppId() + "----->>>" +config.getURL());
		}

		return null;
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model, Integer id, HttpServletResponse response) throws Exception {
		ApplicationConfig config = ApplicationConfigManager.getApplicationConfig(id.toString());
        model.addAttribute("config", config);
		return "app_config/view";
	}
	
	/*@RequestMapping(value = "/add_connector")
	public String list(Model model, String type, HttpServletRequest request) {
		
        ApplicationType appType = ApplicationTypes.getApplicationType(type);

        assert appType.getId().equals("connector"): "Invalid app type: " + type;

        ModuleConfig moduleConfig = appType.getModule();
        MetaApplicationConfig metaAppConfig = moduleConfig.getMetaApplicationConfig();
        model.addAttribute("metaAppConfig", metaAppConfig);

        
        ConnectorConfigData cfgData = ConnectorConfigRegistry.getConnectorConfigData(connForm.getConnectorId());
        connForm.setConfigNames(cfgData.getFieldNames());
        connForm.setConfigValues(cfgData.getFieldDefaultValues());
        

        connForm.setConnectorNames(ConnectorConfigRegistry.getConnectorNames());
        connForm.setConnectorIds(ConnectorConfigRegistry.getConnectorIdList());

        
		ApplicationDowntimeService service = new ApplicationDowntimeService();
        List applications = ApplicationConfigManager.getApplications();

		service.start();
		return viewModel + "list";
	}*/
	
		
}