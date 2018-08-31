package com.appleframework.jmx.database.service;

import com.appleframework.jmx.database.entity.AlertTemplEntity;

public interface AlertTemplService {
	
	public AlertTemplEntity get(Integer id);
	
	public void update(AlertTemplEntity entity);
	
	public void insert(AlertTemplEntity entity);
	
	public AlertTemplEntity getByTypeAndCode(Integer type, String code);
	
}