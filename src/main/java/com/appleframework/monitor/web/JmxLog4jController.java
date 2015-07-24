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
import com.appleframework.jmx.core.services.ServiceContextImpl;
import com.appleframework.monitor.model.Log4jLevelOperation;
import com.appleframework.monitor.model.Log4jLevelType;

@Controller
@RequestMapping("/jmx_log4j")
public class JmxLog4jController extends BaseController {
	
	/*@Resource
	private AppInfoService appInfoService;
	
	@Resource
	private AppInfoSearchService appInfoSearchService;
	
	@Resource
	private AppClusterService appClusterService;*/
	
	@Resource
	private MBeanService mbeanService;
	
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
	
	/*
	@RequestMapping(value = "/save")
	public String save(Model model, AppInfo appInfo, HttpServletRequest request) {
		try {
			appInfoService.insert(appInfo);
		} catch (ServiceException e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_VIEW;
		}
		
		ApplicationConfig appConfig = ApplicationConfigManager.getApplicationConfig(id.toString());
		ServerConnection connection = ServerConnector.getServerConnection(appConfig);

		Hashtable<String, String> properties = new Hashtable<String, String>();

		properties.put(TYPE_KEY, DEFAULT_TYPE);
		properties.put(ID_KEY, "LogContainer");
		
		ObjectName oname = ObjectName.getInstance("com.appleframework", properties);

		String mbeanName = "com.appleframework" + ".container:type=LogContainer";
		
		ServiceContext serviceContext = new ServiceContextImpl(appConfig.getAppId(), mbeanName);
		
		connection.getObjectInfo(objectName)
		
		AttributeListData[] ad = mbeanService.getAttributes(serviceContext);
		for (int i = 0; i < ad.length; i++) {
			System.out.println(ad[i]);
		}
		addSuccessMessage(model, "添加应用成功", "list");
		return SUCCESS_VIEW;
	}
	
	
	// AJAX唯一验证
	@RequestMapping(value = "/check_code", method = RequestMethod.GET)
	public @ResponseBody String checkRoleName(String oldCode, String code) {
		if (appInfoService.isUniqueByCode(oldCode, code)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}*/
		
}