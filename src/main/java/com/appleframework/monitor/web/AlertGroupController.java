package com.appleframework.monitor.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.jmx.database.entity.AlertGroupEntity;
import com.appleframework.jmx.database.service.AlertGroupService;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.monitor.service.AlertGroupSearchService;
import com.appleframework.web.bean.Message;

@Controller
@RequestMapping("/alert_group")
public class AlertGroupController extends BaseController {
	
	@Resource
	private AlertGroupService alertGroupService;
	
	@Resource
	private AlertGroupSearchService alertGroupSearchService;
		
	private String viewModel = "alert_group/";
	
	@RequestMapping(value = "/list")
	public String list(Model model, Pagination page, Search search) {
		page = alertGroupSearchService.findPage(page, search);
		model.addAttribute("se", search);
		model.addAttribute("page", page);
		return viewModel + "list";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, Integer id) throws Exception {
		AlertGroupEntity info = alertGroupService.get(id);
		model.addAttribute("info", info);
		return viewModel + "edit";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model, Integer id) throws Exception {
		AlertGroupEntity info = alertGroupService.get(id);
        model.addAttribute("info", info);
		return viewModel +  "view";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) throws Exception {
		return viewModel +  "add";
	}
	
	@RequestMapping(value = "/update")
	public String update(Model model, AlertGroupEntity alertContact) {
		try {
			AlertGroupEntity old = alertGroupService.get(alertContact.getId());
			old.setName(alertContact.getName());
			alertGroupService.update(old);
		} catch (Exception e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_AJAX;
		}
		addSuccessMessage(model, "修改成功");
		return SUCCESS_AJAX;
	}
	
	@RequestMapping(value = "/save")
	public String save(Model model, AlertGroupEntity alertContact) {
		try {
			alertGroupService.insert(alertContact);
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
		if (alertGroupService.isUniqueByName(oldName, name)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Integer id) {
		try {
			alertGroupService.delete(id);
			return SUCCESS_MESSAGE;
		} catch (Exception e) {
			return Message.error(e.getMessage());
		}
	}
		
}