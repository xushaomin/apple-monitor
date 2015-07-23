package com.appleframework.jmx.database.service;

import java.util.List;

import com.appleframework.exception.ServiceException;
import com.appleframework.jmx.database.entity.AppClusterEntity;

public interface AppClusterService {
	
	public AppClusterEntity get(Integer id);
	
	public void update(AppClusterEntity appCluster) throws ServiceException;
	
	public void save(AppClusterEntity appCluster) throws ServiceException;
	
	public AppClusterEntity getByName(String name);
			
	public boolean isUniqueByName(String oldName, String newName);
	
	public boolean isExistByName(String name);
	
	public AppClusterEntity saveWithName(String name);
	
	public List<AppClusterEntity> findAll();
	
	public List<AppClusterEntity> findListByStart();
	
	public void calibratedAppNum(Integer id);
	
	public Integer delete(Integer id) throws ServiceException;
	
}