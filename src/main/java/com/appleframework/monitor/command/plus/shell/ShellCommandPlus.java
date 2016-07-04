package com.appleframework.monitor.command.plus.shell;

import com.appleframework.monitor.command.plus.CommandPlus;
import com.appleframework.monitor.model.AppCommandParam;
import com.appleframework.monitor.model.CommandExeType;
import com.appleframework.monitor.model.CommandParam;
import com.appleframework.monitor.utils.Constants;
import com.appleframework.monitor.utils.ShellUtil;
import com.appleframework.monitor.websocket.WebSocketServer;
import com.taobao.diamond.utils.JSONUtils;

public class ShellCommandPlus implements CommandPlus {
	
	private static String CMD = "/work/shell/deploy.py";
	
	@Override
	public void doExe(AppCommandParam param, CommandExeType exeType) {
		this.doCommand(param, exeType.name());
	}

	@Override
	public void doLog(AppCommandParam param) {
		// TODO Auto-generated method stub
		
	}
	
	private void doCommand(AppCommandParam param, String option) {
		Constants.BOOT_STATUS_MAP.put(param.getId(), false);
		try {
			
			String hosts[] = new String[1];
			hosts[0] = param.getHost();
			
			CommandParam cmdParam = new CommandParam();
			cmdParam.setHosts(hosts);
			cmdParam.setEnv(param.getEnv());
			cmdParam.setPath(param.getInstallPath());
			cmdParam.setOption(option.toLowerCase());

			String paramJson = JSONUtils.serializeObject(param);
			WebSocketServer.sendMessage(param.getId(), ShellUtil.exec(CMD, paramJson));
			
		} catch (Exception e) {
			WebSocketServer.sendMessage(param.getId(), "命令执行出错，" + e.getMessage());
		}
	}
}
