package com.appleframework.monitor.cache;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
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
		try {
			return getQueue().poll(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			return null;
		}
	}
		
	private RBlockingQueue<AlertDeliveryBo> getQueue() {
		return redisson.getBlockingQueue(CACHE_KEY, codec);
	}
	
}