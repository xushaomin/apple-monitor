package com.appleframework.monitor.cache;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.client.codec.Codec;
import org.redisson.codec.SerializationCodec;
import org.springframework.stereotype.Component;

import com.appleframework.jmx.core.config.ApplicationConfig;
import com.appleframework.jmx.monitoring.downtime.ApplicationDowntimeHistory;

@Component
public class DowntimeCache {
	
    /*private final Map<ApplicationConfig, ApplicationDowntimeHistory> downtimesMap = 
            new HashMap<ApplicationConfig, ApplicationDowntimeHistory>();*/
	
	private String CACHE_KEY = "APPLE:MONITOR:DOWNTIME";
	
	private Codec codec = new SerializationCodec();
	
	@Resource
	private Redisson redisson;
	
	public ApplicationDowntimeHistory get(ApplicationConfig appConfig) {
		return gen(appConfig).get();
	}
	
	public void update(ApplicationConfig appConfig, ApplicationDowntimeHistory history) {
		gen(appConfig).set(history, 1, TimeUnit.DAYS);
	}
	
	public boolean isExists(ApplicationConfig appConfig) {
		return gen(appConfig).isExists();
	}
	
	public void remove(ApplicationConfig appConfig) {
		gen(appConfig).delete();
	}
	
	private RBucket<ApplicationDowntimeHistory> gen(ApplicationConfig appConfig) {
		return redisson.getBucket(CACHE_KEY + ":" + appConfig.getAppId(), codec);
	}

}
