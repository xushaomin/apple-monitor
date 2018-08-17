package com.appleframework.monitor.service.impl;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.appleframework.config.core.PropertyConfigurer;
import com.appleframework.monitor.command.plus.CommandPlus;
import com.appleframework.monitor.command.plus.CommandPlusFactory;
import com.appleframework.monitor.model.AppCommandParam;
import com.appleframework.monitor.model.CommandExeType;
import com.appleframework.monitor.service.CommandService;
import com.appleframework.monitor.websocket.WebSocketServer;


/**
 * @author xusm
 * 
 */
@Service("commandService")
public class CommandServiceImpl implements CommandService {
	
	private static String COMMAND_PLUS = PropertyConfigurer.getString("command.plus", "ssh");
	
	private static CommandPlus plus = CommandPlusFactory.create(COMMAND_PLUS);
	
	@Resource
	private TaskExecutor taskExecutor;

	@Override
	public void doLog(AppCommandParam param) {
		
	}
	
	public void doExe(final AppCommandParam param, final CommandExeType exeType) {
		taskExecutor.execute(new Runnable() {
            @Override  
            public void run() {
            	try {
            		Thread.sleep(1000);

            		plus.doExe(param, exeType);
				} catch (Exception e) {
					WebSocketServer.sendMessage(param.getId(), "执行出错：" + e.getMessage());
				}
            	
            }  
        }); 
	}
	
	
	
	
}
