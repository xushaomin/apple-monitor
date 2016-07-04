package com.appleframework.monitor.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.config.core.PropertyConfigurer;
import com.appleframework.jmx.core.util.StringUtils;
import com.appleframework.jmx.database.entity.AppClusterEntity;
import com.appleframework.jmx.database.entity.AppInfoEntity;
import com.appleframework.jmx.database.entity.NodeInfoEntity;
import com.appleframework.jmx.database.service.AppClusterService;
import com.appleframework.jmx.database.service.AppInfoService;
import com.appleframework.jmx.database.service.NodeInfoService;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.monitor.model.AppCommandParam;
import com.appleframework.monitor.model.AppInfoSo;
import com.appleframework.monitor.model.CommandExeType;
import com.appleframework.monitor.model.Log4jLevelType;
import com.appleframework.monitor.service.AppInfoSearchService;
import com.appleframework.monitor.service.CommandService;
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
	
	@Resource
	private CommandService commandService;
	
	private String websocketUrl = PropertyConfigurer.getString("websocket.url");	

	private String viewModel = "app_info/";
	
	@RequestMapping(value = "/list")
	public String list(Model model, Pagination page, Search search, AppInfoSo so) {
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
		model.addAttribute("LOG_LEVEL_TYPES", getLog4jLevelTypes());
		model.addAttribute("se", search);
		model.addAttribute("so", so);
		model.addAttribute("page", page);
		return viewModel + "list";
	}
	
	@RequestMapping(value = "/list_for_cluster")
	public String listForCluster(Model model, Integer clusterId) {
		
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
	public @ResponseBody AppInfoEntity get(Model model, Integer id) {
		AppInfoEntity info = appInfoService.get(id);
		return info;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, Integer id) throws Exception {
		AppInfoEntity info = appInfoService.get(id);
		model.addAttribute("info", info);
		return "app_info/edit";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model, Integer id) throws Exception {
		AppInfoEntity info = appInfoService.get(id);
        model.addAttribute("info", info);
		return "app_info/view";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) throws Exception {
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
	
	@RequestMapping(value = "/deletes", method = RequestMethod.POST)
	public @ResponseBody
	Message deletes(Integer[] ids) {
		try {
			for (Integer id : ids) {
				appInfoService.delete(id);
			}
			return SUCCESS_MESSAGE;
		} catch (Exception e) {
			return Message.error(e.getMessage());
		}
	}
	
	public List<Log4jLevelType> getLog4jLevelTypes() {
		return Arrays.asList(Log4jLevelType.values());
	}
	
	@RequestMapping(value = "/command")
	public String commandStart(Model model, Integer id, String command) {
		AppInfoEntity appInfo = appInfoService.get(id);
		NodeInfoEntity nodeInfo = nodeInfoService.get(appInfo.getNodeId());
		AppCommandParam param = AppCommandParam.create(id, nodeInfo.getHost(), 
				appInfo.getInstallPath(), appInfo.getConfEnv());
		commandService.doExe(param, CommandExeType.valueOf(command.toLowerCase()));
		model.addAttribute("taskId", id);
		model.addAttribute("websocketUrl", websocketUrl);
		return viewModel + "command";
	}
	
	@RequestMapping(value = "/batch_command")
	public String batchCommand(Model model, String ids, String command) {

		String[] idds = ids.split(",");
		for (int j = 0; j < idds.length; j++) {
			String id = idds[j];
			if (!StringUtils.isEmpty(id)) {

				AppInfoEntity appInfo = appInfoService.get(Integer.parseInt(id));
				NodeInfoEntity nodeInfo = nodeInfoService.get(appInfo.getNodeId());
				AppCommandParam param = AppCommandParam.create(Integer.parseInt(id), nodeInfo.getHost(),
						appInfo.getInstallPath(), appInfo.getConfEnv());
				commandService.doExe(param, CommandExeType.valueOf(command.toUpperCase()));

			}
		}
		model.addAttribute("taskId", 0);
		model.addAttribute("websocketUrl", websocketUrl);
		return viewModel + "command";
	}
			
}