package com.appleframework.monitor.service;

import com.appleframework.monitor.model.AppCommandParam;
import com.appleframework.monitor.model.CommandExeType;

public interface CommandService {

	public void doExe(AppCommandParam param, CommandExeType exeType);
		
	public void doLog(AppCommandParam param);
	
}
