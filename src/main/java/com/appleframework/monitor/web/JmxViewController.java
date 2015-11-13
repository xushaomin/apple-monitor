package com.appleframework.monitor.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.config.ApplicationConfigManager;

@Controller
@RequestMapping("/jmx_view")
public class JmxViewController extends BaseController {
		
	private String viewModel = "jmx_view/";
	
	@RequestMapping(value = "/view_monitor", method = RequestMethod.GET)
	public String viewMonitor(Model model, Integer id, HttpServletResponse response) throws Exception {
		ApplicationConfig appConfig = ApplicationConfigManager.getApplicationConfig(id.toString());
		model.addAttribute("app", appConfig);
		return viewModel + "view_monitor";
	}
	
	@RequestMapping(value = "/view_runtime", method = RequestMethod.GET)
	public String viewRuntime(Model model, Integer id, HttpServletResponse response) throws Exception {
		ApplicationConfig appConfig = ApplicationConfigManager.getApplicationConfig(id.toString());
		model.addAttribute("app", appConfig);
		return viewModel + "view_runtime";
	}

	@RequestMapping(value = "/view_thread", method = RequestMethod.GET)
	public String viewThread(Model model, Integer id, HttpServletResponse response) throws Exception {
		ApplicationConfig appConfig = ApplicationConfigManager.getApplicationConfig(id.toString());
		model.addAttribute("app", appConfig);
		return viewModel + "view_thread";
	}
	
	@RequestMapping(value = "/index_monitor", method = RequestMethod.GET)
	public String indexMonitor(Model model, HttpServletResponse response) throws Exception {
		return viewModel + "index_monitor";
	}
	
	@RequestMapping(value = "/index_runtime", method = RequestMethod.GET)
	public String indexRuntime(Model model, HttpServletResponse response) throws Exception {
		return viewModel + "index_runtime";
	}
	
	@RequestMapping(value = "/index_thread", method = RequestMethod.GET)
	public String indexThread(Model model, HttpServletResponse response) throws Exception {
		return viewModel + "index_thread";
	}
			
}