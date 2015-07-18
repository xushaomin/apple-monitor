package com.appleframework.monitor.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.config.ApplicationConfigManager;
import com.appleframework.jmx.database.entity.AppClusterEntity;
import com.appleframework.jmx.database.entity.AppInfoEntity;
import com.appleframework.jmx.database.entity.NodeInfoEntity;
import com.appleframework.jmx.database.service.AppClusterService;
import com.appleframework.jmx.database.service.AppInfoService;
import com.appleframework.jmx.database.service.NodeInfoService;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.monitor.model.AppInfoSo;
import com.appleframework.monitor.service.AppInfoSearchService;

@Controller
@RequestMapping("/jmx_log")
public class JmxLogController extends BaseController {
	
	@Resource
	private AppInfoService appInfoService;
	
	@Resource
	private AppInfoSearchService appInfoSearchService;
	
	@Resource
	private AppClusterService appClusterService;
	
	@Resource
	private NodeInfoService nodeInfoService;
	
	private String viewModel = "jmx_log/";
	
	
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model, Integer id, HttpServletResponse response) throws Exception {
		ApplicationConfig config = ApplicationConfigManager.getApplicationConfig(id.toString());
        model.addAttribute("config", config);        
		return viewModel + "view";
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
		addSuccessMessage(model, "添加应用成功", "list");
		return SUCCESS_VIEW;
	}
	
	
	
	@RequestMapping(value = "/update")
	public String update(Model model, AppInfo appInfo, HttpServletResponse response) {
		try {
			AppInfo old = appInfoService.get(appInfo.getId());
			old.setCode(appInfo.getCode());
			old.setName(appInfo.getName());
			old.setRemark(appInfo.getRemark());
			old.setUpdateTime(new Date());
			appInfoService.update(old);
		} catch (ServiceException e) {
			addErrorMessage(model, e.getMessage());
			return "/commons/error_ajax";
		}
		addSuccessMessage(model, "修改应用成功", "list");
		return "/commons/success_ajax";
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