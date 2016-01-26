package com.appleframework.jmx.database.service;

import java.util.List;

import com.appleframework.exception.AppleException;
import com.appleframework.jmx.database.entity.AlertContactEntity;

public interface AlertContactService {
	
	public List<AlertContactEntity> findAll();
		
	public AlertContactEntity get(Integer id);
	
	public void update(AlertContactEntity entity);
	
	public void insert(AlertContactEntity entity);
	
	public boolean isExistByName(String name);
	
	public int countByName(String name);
	
	public boolean isUniqueByName(String oldName, String newName);
	
	public void delete(Integer id) throws AppleException;
	
}