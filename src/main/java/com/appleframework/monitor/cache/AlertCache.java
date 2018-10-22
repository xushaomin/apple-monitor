package com.appleframework.monitor.cache;

import javax.annotation.Resource;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.springframework.stereotype.Component;

import com.appleframework.jmx.core.config.ApplicationConfig;

@Component
public class AlertCache {
	
    /*private final Map<ApplicationConfig, ApplicationDowntimeHistory> downtimesMap = 
            new HashMap<ApplicationConfig, ApplicationDowntimeHistory>();*/
	
	private String CACHE_KEY = "APPLE:MONITOR:ALERT";
		
	@Resource
	private Redisson redisson;
	
	public long incrementAndGet(ApplicationConfig appConfig) {
		return genAtomic(appConfig).incrementAndGet();
	}
	
	public void increment(ApplicationConfig appConfig) {
		genAtomic(appConfig).incrementAndGet();;
	}
	
	public void init(ApplicationConfig appConfig) {
		genAtomic(appConfig).deleteAsync();
	}
		
	private RAtomicLong genAtomic(ApplicationConfig appConfig) {
		return redisson.getAtomicLong(CACHE_KEY + ":" + appConfig.getAppId());
	}
	
}