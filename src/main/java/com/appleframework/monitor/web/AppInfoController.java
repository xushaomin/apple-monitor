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
import com.appleframework.web.bean.Message;

@Controller
@RequestMapping("/app_info")
public class AppInfoController extends BaseController {
	
	@Resource
	private AppInfoService appInfoService;
	
	@Resource
	private AppInfoSearchService appInfoSearchService;
	
	@Resource
	private AppClusterService appClusterService;
	
	@Resource
	private NodeInfoService nodeInfoService;
	
	private String viewModel = "app_info/";
	
	@RequestMapping(value = "/list")
	public String list(Model model, Pagination page, 
			Search search, AppInfoSo so, HttpServletRequest request) {
		
		page = appInfoSearchService.findPage(page, search, so);
		List<NodeInfoEntity> nodeInfoList = nodeInfoService.findAll();
		List<AppClusterEntity> appGroupList = appClusterService.findAll();
		
		Map<String, NodeInfoEntity> nodeInfoMap = new HashMap<String, NodeInfoEntity>();
		for (NodeInfoEntity nodeInfo : nodeInfoList) {
			nodeInfoMap.put(nodeInfo.getId() + "", nodeInfo);
		}
		Map<String, AppClusterEntity> appGroupMap = new HashMap<String, AppClusterEntity>();
		for (AppClusterEntity appGroup : appGroupList) {
			appGroupMap.put(appGroup.getId() + "", appGroup);
		}
		model.addAttribute("NODE_INFO_LIST", nodeInfoList);
		model.addAttribute("APP_CLUSTER_LIST", appGroupList);
		model.addAttribute("NODE_INFO_MAP", nodeInfoMap);
		model.addAttribute("APP_CLUSTER_MAP", appGroupMap);
		model.addAttribute("se", search);
		model.addAttribute("so", so);
		model.addAttribute("page", page);
		return viewModel + "list";
	}
	
	@RequestMapping(value = "/list_for_cluster")
	public String listForCluster(Model model, Integer clusterId, HttpServletRequest request) {
		
		List<NodeInfoEntity> nodeInfoList = nodeInfoService.findAll();
		List<AppClusterEntity> appGroupList = appClusterService.findAll();
		
		Map<String, NodeInfoEntity> nodeInfoMap = new HashMap<String, NodeInfoEntity>();
		for (NodeInfoEntity nodeInfo : nodeInfoList) {
			nodeInfoMap.put(nodeInfo.getId() + "", nodeInfo);
		}
		Map<String, AppClusterEntity> appGroupMap = new HashMap<String, AppClusterEntity>();
		for (AppClusterEntity appGroup : appGroupList) {
			appGroupMap.put(appGroup.getId() + "", appGroup);
		}
		
		List<AppInfoEntity> list = appInfoService.findListByClusterId(clusterId);

		model.addAttribute("NODE_INFO_LIST", nodeInfoList);
		model.addAttribute("APP_CLUSTER_LIST", appGroupList);
		model.addAttribute("NODE_INFO_MAP", nodeInfoMap);
		model.addAttribute("APP_CLUSTER_MAP", appGroupMap);
		model.addAttribute("list", list);
		return viewModel + "list_for_cluster";
	}
	
	@RequestMapping(value = "/get")
	public @ResponseBody AppInfoEntity get(Model model, Integer id, HttpServletRequest request) {
		AppInfoEntity info = appInfoService.get(id);
		return info;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, Integer id, HttpServletResponse response) throws Exception {
		AppInfoEntity info = appInfoService.get(id);
		model.addAttribute("info", info);
		return "app_info/edit";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model, Integer id, HttpServletResponse response) throws Exception {
		AppInfoEntity info = appInfoService.get(id);
        model.addAttribute("info", info);
		return "app_info/view";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletResponse response) throws Exception {
		return "app_info/add";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Integer id) {
		try {
			appInfoService.delete(id);
			return SUCCESS_MESSAGE;
		} catch (Exception e) {
			return Message.error(e.getMessage());
		}
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