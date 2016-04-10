package com.appleframework.jmx.database.service;

import java.util.List;

import com.appleframework.exception.AppleException;
import com.appleframework.jmx.database.entity.AppGroupEntity;

public interface AppGroupService {
	
	public List<AppGroupEntity> findAll();
	
	public AppGroupEntity get(Integer id);
	
	public void update(AppGroupEntity entity);
	
	public void save(AppGroupEntity entity);
	
	public boolean isExistByName(String name);
	
	public int countByName(String name);
	
	public AppGroupEntity getByName(String name);
	
	public boolean isUniqueByName(String oldName, String newName);
	
	public void delete(Integer id) throws AppleException;
	
	public AppGroupEntity saveWithName(String name);
	
}