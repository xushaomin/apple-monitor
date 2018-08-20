package com.appleframework.monitor.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appleframework.jmx.core.alert.source.NotificationAlertSource;
import com.appleframework.jmx.core.config.AlertSourceConfig;
import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.config.ApplicationConfigManager;
import com.appleframework.monitor.model.LoggingLevelType;

@Controller
@RequestMapping("/jmx_alert")
public class JmxAlertController extends BaseController {
		
	/*@Resource
	private MBeanService mbeanService;*/
	
	private String viewModel = "jmx_alert/";
	
	private static final String MBEAN_NAME = "java.lang:type=MemoryManager,name=CodeCacheManager";

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model, Integer id, HttpServletResponse response) throws Exception {
		ApplicationConfig appConfig = ApplicationConfigManager.getApplicationConfig(id.toString());
				
		AlertSourceConfig alertSourceConfig = new AlertSourceConfig(MBEAN_NAME, AlertSourceConfig.SOURCE_TYPE_NOTIFICATION); 
		alertSourceConfig.setApplicationConfig(appConfig);
		
		
		NotificationAlertSource notificationAlertSource =  new NotificationAlertSource(alertSourceConfig);
		notificationAlertSource.registerInternal();
		
        model.addAttribute("appConfig", appConfig); 
		return viewModel + "view";
	}	
	
	public List<LoggingLevelType> getLog4jLevelTypes() {
		return Arrays.asList(LoggingLevelType.values());
	}
			
}