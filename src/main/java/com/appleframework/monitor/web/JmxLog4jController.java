package com.appleframework.monitor.web;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.config.ApplicationConfigManager;
import com.appleframework.jmx.core.data.OperationResultData;
import com.appleframework.jmx.core.services.MBeanService;
import com.appleframework.jmx.core.services.ServiceContext;
import com.appleframework.jmx.core.services.impl.ServiceContextImpl;
import com.appleframework.jmx.core.util.StringUtils;
import com.appleframework.jmx.database.service.AppInfoService;
import com.appleframework.jmx.webui.view.ApplicationViewHelper;
import com.appleframework.monitor.model.Log4jLevelOperation;
import com.appleframework.monitor.model.Log4jLevelType;

@Controller
@RequestMapping("/jmx_log4j")
public class JmxLog4jController extends BaseController {
		
	@Resource
	private MBeanService mbeanService;
	
	@Resource
	private ApplicationViewHelper applicationViewHelper;
	
	@Resource
	private AppInfoService appInfoService;
	
	private String viewModel = "jmx_log4j/";
	
	private static final String MBEAN_NAME = "com.appleframework:type=container,id=LogContainer";

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model, Integer id, HttpServletResponse response) throws Exception {
		ApplicationConfig appConfig = ApplicationConfigManager.getApplicationConfig(id.toString());
		ServiceContext serviceContext = new ServiceContextImpl(appConfig.getAppId(), MBEAN_NAME);
		String [] dates = {};
		OperationResultData date[] = mbeanService.invoke(serviceContext, "printLog4jConfig", dates);
		for (int i = 0; i < date.length; i++) {
			OperationResultData operationResultData = date[i];
			if(!operationResultData.isError()) {
				model.addAttribute("logConfig", operationResultData.getDisplayOutput()); 
			}
			else {
				model.addAttribute("logConfig", operationResultData.getErrorString()); 
			}	
		}            
        model.addAttribute("appConfig", appConfig); 
		return viewModel + "view";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, Integer id, HttpServletResponse response) throws Exception {
		ApplicationConfig appConfig = ApplicationConfigManager.getApplicationConfig(id.toString());
		model.addAttribute("appConfig", appConfig);
		model.addAttribute("appInfo", appInfoService.get(id));
		model.addAttribute("log4jLevelTypes", getLog4jLevelTypes());
		return viewModel + "edit";
	}
	
	@RequestMapping(value = "/update")
	public String update(Model model, Integer id, short level, HttpServletResponse response) {
		try {
			String operateName = Log4jLevelOperation.toLevelOperation(level);
			ApplicationConfig appConfig = ApplicationConfigManager.getApplicationConfig(id.toString());
			ServiceContext serviceContext = new ServiceContextImpl(appConfig.getAppId(), MBEAN_NAME);
			String [] dates = {};
			//ServiceFactory.getMBeanService();
			OperationResultData date[] = mbeanService.invoke(serviceContext, operateName, dates);
			for (int i = 0; i < date.length; i++) {
				OperationResultData operationResultData = date[i];
				if(operationResultData.isError()) {
					addErrorMessage(model, operationResultData.getErrorString());
				}
			}            
			
			appInfoService.updateLogLevel(id, Log4jLevelType.getName(level));
		} catch (Exception e) {
			addErrorMessage(model, e.getMessage());
			return "/commons/error_ajax";
		}
		addSuccessMessage(model, "修改成功");
		return "/commons/success_ajax";
	}
	
	public List<Log4jLevelType> getLog4jLevelTypes() {
		return Arrays.asList(Log4jLevelType.values());
	}
	
	
	@RequestMapping(value = "/level_select")
	public String levelSelect(Model model, String ids, HttpServletResponse response) throws Exception {
		model.addAttribute("IDS", ids);
		model.addAttribute("log4jLevelTypes", getLog4jLevelTypes());
		return viewModel + "level_select";
	}
	
	@RequestMapping(value = "/batch_update")
	public String batchUpdate(Model model, String ids, short level, HttpServletResponse response) {
		try {
			String operateName = Log4jLevelOperation.toLevelOperation(level);
			String levelName = Log4jLevelType.getName(level);
			String[] idds = ids.split(",");
			for (int j = 0; j < idds.length; j++) {
				String id = idds[j];
				if(!StringUtils.isEmpty(id)) {
					ApplicationConfig appConfig = ApplicationConfigManager.getApplicationConfig(id.toString());
					
					if(applicationViewHelper.isApplicationUp(appConfig)) {
						ServiceContext serviceContext = new ServiceContextImpl(appConfig.getAppId(), MBEAN_NAME);
						String [] dates = {};
						OperationResultData date[] = mbeanService.invoke(serviceContext, operateName, dates);
						for (int i = 0; i < date.length; i++) {
							OperationResultData operationResultData = date[i];
							if(operationResultData.isError()) {
								addErrorMessage(model, operationResultData.getErrorString());
							}
						}
						appInfoService.updateLogLevel(Integer.parseInt(id), levelName);
					}

				}
			}
			
		} catch (Exception e) {
			addErrorMessage(model, e.getMessage());
			return "/commons/error_ajax";
		}
		addSuccessMessage(model, "修改成功");
		return "/commons/success_ajax";
	}
	
}