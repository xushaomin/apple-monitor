package com.appleframework.monitor.cache;

import javax.annotation.Resource;

import org.redisson.Redisson;
import org.redisson.api.RQueue;
import org.redisson.client.codec.Codec;
import org.redisson.codec.SerializationCodec;
import org.springframework.stereotype.Component;

import com.appleframework.monitor.model.AlertDeliveryBo;

@Component
public class AlertQueue {
		
	private String CACHE_KEY = "APPLE:MONITOR:QUEUE";
	
	private Codec codec = new SerializationCodec();
		
	@Resource
	private Redisson redisson;
	
	public void add(AlertDeliveryBo bo) {
		getQueue().add(bo);
	}
	
	public AlertDeliveryBo get() {
		return getQueue().poll();
	}
		
	private RQueue<AlertDeliveryBo> getQueue() {
		return redisson.getQueue(CACHE_KEY, codec);
	}
	
}