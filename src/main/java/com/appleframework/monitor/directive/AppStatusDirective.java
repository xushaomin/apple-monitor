package com.appleframework.monitor.directive;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.appleframework.core.utils.ObjectUtility;
import com.appleframework.core.utils.SpringUtility;
import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.core.config.ApplicationConfigManager;
import com.appleframework.jmx.webui.view.ApplicationViewHelper;
import com.appleframework.web.freemarker.directive.BaseDirective;
import com.appleframework.web.freemarker.util.DirectiveUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("appStatusDirective")
public class AppStatusDirective extends BaseDirective {
	
	public static final String TAG_NAME = "appStatus";

	private static final String APP_ID_PARAMETER_NAME = "appId";
	
	private static final String IS_UPDATE_VARIABLE_NAME = "isUp";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) 
			throws TemplateException, IOException {
		Object appId = DirectiveUtil.getObjectParameter(APP_ID_PARAMETER_NAME, params);
		boolean isUp = false;
		try {
			ApplicationViewHelper applicationViewHelper 
				= (ApplicationViewHelper)SpringUtility.getBean("applicationViewHelper");
			ApplicationConfig applicationConfig 
				= ApplicationConfigManager.getApplicationConfig(appId.toString());
			if(ObjectUtility.isNotEmpty(applicationConfig)) {
				isUp = applicationViewHelper.isApplicationUp(applicationConfig);
			}
			else {
				isUp = false;
			}
		} catch (Exception e) {
			isUp = false;
		}

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(IS_UPDATE_VARIABLE_NAME, isUp);
		setLocalVariables(variables, env, body);
	}

}