package com.appleframework.monitor.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.jmx.core.util.Loggers;
import com.appleframework.jmx.database.entity.AppClusterEntity;
import com.appleframework.jmx.database.entity.NodeInfoEntity;
import com.appleframework.jmx.database.service.AppConfigService;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.monitor.model.AppInfoSo;
import com.appleframework.web.bean.Message;

@Controller
@RequestMapping("/app_alert")
public class AppAlertController extends BaseController {

	private static final Logger logger = Loggers.getLogger(AppAlertController.class);

	@Resource
	private AppConfigService appConfigService;

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
	public String list(Model model, Pagination page, 
			Search search, AppInfoSo so, HttpServletRequest request) {
		
		/*page = appInfoSearchService.findPage(page, search, so);
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
		return viewModel + "list";*/
		return null;
	}

}