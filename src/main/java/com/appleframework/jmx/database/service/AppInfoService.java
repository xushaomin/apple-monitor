package com.appleframework.jmx.database.service;

import java.util.List;

import com.appleframework.jmx.database.entity.AppInfoEntity;

public interface AppInfoService {
	
	public AppInfoEntity get(Integer id);
	
	public void update(AppInfoEntity appInfo);
	
	public void insert(AppInfoEntity appInfo);
	
	public boolean isExistByNodeAndCluster(Integer nodeId, Integer clusterId);
	
	public int countByNodeAndCluster(Integer nodeId, Integer clusterId);
	
	public AppInfoEntity getByNodeAndCluster(Integer nodeId, Integer clusterId);
	
	public AppInfoEntity saveOrUpdate(AppInfoEntity appInfo);
		
	public List<AppInfoEntity> findAll();
	
	public List<AppInfoEntity> findListByClusterId(Integer clusterId);
	
	public int countByClusterId(Integer clusterId);
	
}