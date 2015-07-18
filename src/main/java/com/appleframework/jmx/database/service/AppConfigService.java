package com.appleframework.jmx.database.service;

import java.util.List;

import com.appleframework.jmx.database.entity.AppConfigEntity;


public interface AppConfigService {
	
	public AppConfigEntity get(Integer id);
	
	public void update(AppConfigEntity appConfig);
	
	public void insert(AppConfigEntity appConfig);
		
	public List<AppConfigEntity> findAll();
	
	public List<AppConfigEntity> findListByClusterId(Integer clusterId);
	
}