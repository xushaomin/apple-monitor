package com.appleframework.jmx.database.service;

import java.util.List;

import com.appleframework.jmx.database.entity.AppDowntimeHistoryEntity;

public interface AppDowntimeHistoryService {
	
	public AppDowntimeHistoryEntity get(Integer id);
	
	public void update(AppDowntimeHistoryEntity history);
	
	public void save(AppDowntimeHistoryEntity history);
		
	public List<AppDowntimeHistoryEntity> findAll();
	
	public void saveOrUpdate(Integer id, long downtimeBegin, long downtimeEnd);
	
}