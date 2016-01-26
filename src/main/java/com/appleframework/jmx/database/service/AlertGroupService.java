package com.appleframework.jmx.database.service;

import java.util.List;

import com.appleframework.exception.AppleException;
import com.appleframework.jmx.database.entity.AlertGroupEntity;

public interface AlertGroupService {
	
	public List<AlertGroupEntity> findAll();
	
	public AlertGroupEntity get(Integer id);
	
	public void update(AlertGroupEntity entity);
	
	public void insert(AlertGroupEntity entity);
	
	public boolean isExistByName(String name);
	
	public int countByName(String name);
	
	public boolean isUniqueByName(String oldName, String newName);
	
	public void delete(Integer id) throws AppleException;
	
}