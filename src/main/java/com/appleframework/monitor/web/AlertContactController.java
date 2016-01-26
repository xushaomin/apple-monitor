package com.appleframework.monitor.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appleframework.jmx.database.service.AppClusterService;
import com.appleframework.jmx.database.service.AlertContactService;
import com.appleframework.jmx.database.service.NodeInfoService;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.monitor.service.AlertContactSearchService;

@Controller
@RequestMapping("/alert_contact")
public class AlertContactController extends BaseController {
	
	@Resource
	private AlertContactService alertContactService;
	
	@Resource
	private AlertContactSearchService alertContactSearchService;
	
	@Resource
	private AppClusterService appClusterService;
	
	@Resource
	private NodeInfoService nodeInfoService;
	
	private String viewModel = "alert_contact/";
	
	@RequestMapping(value = "/list")
	public String list(Model model, Pagination page, 
			Search search, HttpServletRequest request) {
		page = alertContactSearchService.findPage(page, search);
		model.addAttribute("se", search);
		model.addAttribute("page", page);
		return viewModel + "list";
	}
	
	/*@RequestMapping(value = "/list_for_cluster")
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
		
		List<AlertContactEntity> list = alertContactService.findListByClusterId(clusterId);

		model.addAttribute("NODE_INFO_LIST", nodeInfoList);
		model.addAttribute("APP_CLUSTER_LIST", appGroupList);
		model.addAttribute("NODE_INFO_MAP", nodeInfoMap);
		model.addAttribute("APP_CLUSTER_MAP", appGroupMap);
		model.addAttribute("list", list);
		return viewModel + "list_for_cluster";
	}
	
	@RequestMapping(value = "/get")
	public @ResponseBody AlertContactEntity get(Model model, Integer id, HttpServletRequest request) {
		AlertContactEntity info = alertContactService.get(id);
		return info;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, Integer id, HttpServletResponse response) throws Exception {
		AlertContactEntity info = alertContactService.get(id);
		model.addAttribute("info", info);
		return "app_info/edit";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model, Integer id, HttpServletResponse response) throws Exception {
		AlertContactEntity info = alertContactService.get(id);
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
			alertContactService.delete(id);
			return SUCCESS_MESSAGE;
		} catch (Exception e) {
			return Message.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/deletes", method = RequestMethod.POST)
	public @ResponseBody
	Message deletes(Integer[] ids) {
		try {
			for (Integer id : ids) {
				alertContactService.delete(id);
			}
			return SUCCESS_MESSAGE;
		} catch (Exception e) {
			return Message.error(e.getMessage());
		}
	}*/
	
	/*
	@RequestMapping(value = "/save")
	public String save(Model model, AlertContact alertContact, HttpServletRequest request) {
		try {
			alertContactService.insert(alertContact);
		} catch (ServiceException e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_VIEW;
		}
		addSuccessMessage(model, "添加应用成功", "list");
		return SUCCESS_VIEW;
	}
	
	
	
	@RequestMapping(value = "/update")
	public String update(Model model, AlertContact alertContact, HttpServletResponse response) {
		try {
			AlertContact old = alertContactService.get(alertContact.getId());
			old.setCode(alertContact.getCode());
			old.setName(alertContact.getName());
			old.setRemark(alertContact.getRemark());
			old.setUpdateTime(new Date());
			alertContactService.update(old);
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
		if (alertContactService.isUniqueByCode(oldCode, code)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}*/
		
}