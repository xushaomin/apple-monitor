package com.appleframework.jmx.database.service;

import java.util.List;

import com.appleframework.jmx.database.entity.AppDowntimeEntity;

public interface AppDowntimeService {
	
	public AppDowntimeEntity get(Integer id);
	
	public void update(AppDowntimeEntity appDowntime);
	
	public void insert(AppDowntimeEntity appDowntime);
		
	public List<AppDowntimeEntity> findAll();
	
	//public AppDowntime saveOrUpdate(AppDowntime appConfig);
	
}