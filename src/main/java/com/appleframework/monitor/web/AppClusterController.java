package com.appleframework.monitor.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.exception.ServiceException;
import com.appleframework.jmx.database.entity.AppClusterEntity;
import com.appleframework.jmx.database.service.AppClusterService;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;

import com.appleframework.monitor.service.AppClusterSearchService;
import com.appleframework.web.bean.Message;

@Controller
@RequestMapping("/app_cluster")
public class AppClusterController extends BaseController {
		
	@Resource
	private AppClusterSearchService appClusterSearchService;
	
	@Resource
	private AppClusterService appClusterService;
	
	private String viewModel = "app_cluster/";
	
	@RequestMapping(value = "/list")
	public String list(Model model, Pagination page, Search search, HttpServletRequest request) {
		page = appClusterSearchService.findPage(page, search);
		model.addAttribute("se", search);
		model.addAttribute("page", page);
		return viewModel + "list";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model, Integer id, HttpServletResponse response) throws Exception {
		AppClusterEntity info = appClusterService.get(id);
        model.addAttribute("info", info);
		return viewModel + "view";
	}
	
	@RequestMapping(value = "/update")
	public String update(Model model, AppClusterEntity appClusterVo, HttpServletResponse response) {
		try {
			AppClusterEntity old = appClusterService.get(appClusterVo.getId());
			old.setClusterName(appClusterVo.getClusterName());
			old.setClusterDesc(appClusterVo.getClusterDesc());
			old.setRemark(appClusterVo.getRemark());
			appClusterService.update(old);
			appClusterService.calibratedAppNum(appClusterVo.getId());
		} catch (Exception e) {
			addErrorMessage(model, e.getMessage());
			return "/commons/error_ajax";
		}
		addSuccessMessage(model, "修改集群成功", "list");
		return "/commons/success_ajax";
	}
	
	// AJAX唯一验证
	@RequestMapping(value = "/check_clusterName", method = RequestMethod.GET)
	public @ResponseBody String checkClusterName(String oldClusterName, String clusterName) {
		if (appClusterService.isUniqueByName(oldClusterName, clusterName)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, Integer id, HttpServletResponse response) throws Exception {
		AppClusterEntity info = appClusterService.get(id);
		model.addAttribute("info", info);
		return viewModel + "edit";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Integer id) {
		try {
			appClusterService.delete(id);
			return SUCCESS_MESSAGE;
		} catch (ServiceException e) {
			return Message.error(e.getMessage());
		}
	}
}