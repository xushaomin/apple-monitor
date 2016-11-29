package com.appleframework.monitor.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.monitoring.downtime.ApplicationDowntimeService;
import com.appleframework.jmx.monitoring.downtime.DowntimeRecorder;

@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private DowntimeRecorder downtimeRecorder;
	
	@Autowired
	private ApplicationDowntimeService applicationDowntimeService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			// TODO 这里写下将要初始化的内容
			downtimeRecorder.init();
			
			applicationDowntimeService.start();
		}
	}
}
