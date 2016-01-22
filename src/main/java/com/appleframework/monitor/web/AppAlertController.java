package com.appleframework.monitor.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.jmx.database.entity.AlertGroupEntity;
import com.appleframework.jmx.database.service.AlertGroupService;
import com.appleframework.jmx.database.service.AppConfigService;
import com.appleframework.web.bean.Message;

@Controller
@RequestMapping("/app_alert")
public class AppAlertController extends BaseController {

	private static final Logger logger = Loggers.getLogger(AppAlertController.class);

	@Resource
	private AppConfigService appConfigService;
	
	@Resource
	private AlertGroupService alertGroupService;
	
	private String viewModel = "app_alert/";

	@RequestMapping(value = "/alert_start", method = RequestMethod.POST)
	public @ResponseBody Message start(Integer id) {
		try {
			appConfigService.updateIsAlert(id, true);
			return SUCCESS_MESSAGE;
		} catch (Exception e) {
			return Message.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/alert_stop", method = RequestMethod.POST)
	public @ResponseBody Message stop(Integer id) {
		try {
			appConfigService.updateIsAlert(id, false);
			return SUCCESS_MESSAGE;
		} catch (Exception e) {
			logger.error(e);
			return Message.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/alert_starts", method = RequestMethod.POST)
	public @ResponseBody Message starts(Integer[] ids) {
		try {
			for (Integer id : ids) {
				appConfigService.updateIsAlert(id, true);
			}
			return SUCCESS_MESSAGE;
		} catch (Exception e) {
			return Message.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/alert_stops", method = RequestMethod.POST)
	public @ResponseBody Message stops(Integer[] ids) {
		try {
			for (Integer id : ids) {
				appConfigService.updateIsAlert(id, false);
			}
			return SUCCESS_MESSAGE;
		} catch (Exception e) {
			logger.error(e);
			return Message.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/alert_group_select")
	public String list(Model model, Integer[] ids, HttpServletRequest request) {
		List<AlertGroupEntity> groupList = alertGroupService.findAll();
		model.addAttribute("ALERT_GROUP_LIST", groupList);
		model.addAttribute("IDS", ids);
		return viewModel + "/alert_group_select";
	}

}