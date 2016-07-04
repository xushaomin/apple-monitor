package com.appleframework.monitor.command.plus;

import com.appleframework.monitor.model.AppCommandParam;
import com.appleframework.monitor.model.CommandExeType;

public interface CommandPlus {

	public void doExe(AppCommandParam param, CommandExeType exeType);
		
	public void doLog(AppCommandParam param);
}
