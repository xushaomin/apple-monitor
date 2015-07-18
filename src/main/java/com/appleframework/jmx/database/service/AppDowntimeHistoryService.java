package com.appleframework.jmx.database.service;

import java.util.List;

import com.appleframework.jmx.database.entity.AppDowntimeHistoryEntity;

public interface AppDowntimeHistoryService {
	
	public AppDowntimeHistoryEntity get(Integer id);
	
	public void update(AppDowntimeHistoryEntity history);
	
	public void insert(AppDowntimeHistoryEntity history);
		
	public List<AppDowntimeHistoryEntity> findAll();
	
	//public AppDowntimeHistory saveOrUpdate(AppDowntimeHistory appConfig);
	
}