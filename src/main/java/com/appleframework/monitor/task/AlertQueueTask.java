package com.appleframework.monitor.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.appleframework.jmx.monitoring.downtime.alert.AlertDelivery;
import com.appleframework.monitor.cache.AlertQueue;
import com.appleframework.monitor.model.AlertDeliveryBo;

@Component("alertQueueTask")
@Lazy(false)
public class AlertQueueTask {
		
	private ExecutorService executor = Executors.newFixedThreadPool(1);

	private AlertQueue alertQueue;
	
	@Resource
	private AlertDelivery alertDelivery;
	
	@PostConstruct
    public void init() {
		
		executor.submit(new Runnable() {
			public void run() {
				while(true) {
					AlertDeliveryBo bo = alertQueue.get();
					if(null != bo) {
						alertDelivery.deliver(bo);
					}
				}
			}
        });
    }
	
}