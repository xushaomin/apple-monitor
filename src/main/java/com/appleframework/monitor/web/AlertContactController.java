package com.appleframework.monitor.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.jmx.database.entity.AlertContactEntity;
import com.appleframework.jmx.database.service.AlertContactService;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.monitor.service.AlertContactSearchService;
import com.appleframework.web.bean.Message;

@Controller
@RequestMapping("/alert_contact")
public class AlertContactController extends BaseController {
	
	@Resource
	private AlertContactService alertContactService;
	
	@Resource
	private AlertContactSearchService alertContactSearchService;
		
	private String viewModel = "alert_contact/";
	
	@RequestMapping(value = "/list")
	public String list(Model model, Pagination page, Search search) {
		page = alertContactSearchService.findPage(page, search);
		model.addAttribute("se", search);
		model.addAttribute("page", page);
		return viewModel + "list";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, Integer id) throws Exception {
		AlertContactEntity info = alertContactService.get(id);
		model.addAttribute("info", info);
		return viewModel + "edit";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model, Integer id) throws Exception {
		AlertContactEntity info = alertContactService.get(id);
        model.addAttribute("info", info);
		return viewModel +  "view";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) throws Exception {
		return viewModel +  "add";
	}
	
	@RequestMapping(value = "/update")
	public String update(Model model, AlertContactEntity alertContact) {
		try {
			AlertContactEntity old = alertContactService.get(alertContact.getId());
			old.setName(alertContact.getName());
			old.setMobile(alertContact.getMobile());
			old.setEmail(alertContact.getEmail());
			alertContactService.update(old);
		} catch (Exception e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_AJAX;
		}
		addSuccessMessage(model, "修改成功");
		return SUCCESS_AJAX;
	}
	
	@RequestMapping(value = "/save")
	public String save(Model model, AlertContactEntity alertContact) {
		try {
			alertContactService.insert(alertContact);
		} catch (Exception e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_AJAX;
		}
		addSuccessMessage(model, "添加成功");
		return SUCCESS_AJAX;
	}
	
	// AJAX唯一验证
	@RequestMapping(value = "/check_name", method = RequestMethod.GET)
	public @ResponseBody String checkRoleName(String oldName, String name) {
		if (alertContactService.isUniqueByName(oldName, name)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Integer id) {
		try {
			alertContactService.delete(id);
			return SUCCESS_MESSAGE;
		} catch (Exception e) {
			return Message.error(e.getMessage());
		}
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
	
	
	
	
	
	*/
		
}