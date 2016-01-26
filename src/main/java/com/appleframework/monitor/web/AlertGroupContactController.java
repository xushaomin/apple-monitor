package com.appleframework.monitor.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appleframework.jmx.database.entity.AlertContactEntity;
import com.appleframework.jmx.database.entity.AlertGroupContactEntity;
import com.appleframework.jmx.database.service.AlertContactService;
import com.appleframework.jmx.database.service.AlertGroupContactService;

@Controller
@RequestMapping("/alert_group_contact")
public class AlertGroupContactController extends BaseController {
	
	@Resource
	private AlertContactService alertContactService;
	
	@Resource
	private AlertGroupContactService alertGroupContactService;
		
	private String viewModel = "alert_group_contact/";
	
	@RequestMapping(value = "/list")
	public String list(Model model, Integer groupId) {
		List<AlertContactEntity> allContactList = alertContactService.findAll();
		List<AlertContactEntity> existContactList = alertGroupContactService.findAlertContactListByGroupId(groupId);
		model.addAttribute("GROUP_ID", groupId);
		model.addAttribute("ALL_CONTACT_LIST", allContactList);
		model.addAttribute("EXIST_CONTACT_LIST", existContactList);
		return viewModel + "list";
	}
	
	@RequestMapping(value = "/update")
	public String update(Model model, Integer groupId, Integer[] contactIds) {
		try {
			//新的联系人
			Set<Integer> newSet = new HashSet<Integer>();
			for (int i = 0; i < contactIds.length; i++) {
				newSet.add(contactIds[i]);
			}
			
			//旧联系人
			List<AlertGroupContactEntity> existList = alertGroupContactService.findListByGroupId(groupId);
			Set<Integer> existSet = new HashSet<Integer>();
			for (AlertGroupContactEntity entity : existList) {
				existSet.add(entity.getContactId());
			}
			
			//新增
			for (Integer contactId : contactIds) {
				if(!existSet.contains(contactId)) {
					AlertGroupContactEntity entity = new AlertGroupContactEntity();
					entity.setContactId(contactId);
					entity.setGroupId(groupId);
					alertGroupContactService.insert(entity);
				}
			}
			
			//删除
			for (AlertGroupContactEntity entity : existList) {
				if(!newSet.contains(entity.getContactId())) {
					alertGroupContactService.delete(entity.getId());
				}
			}
		} catch (Exception e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_AJAX;
		}
		addSuccessMessage(model, "修改成功");
		return SUCCESS_AJAX;
	}
}