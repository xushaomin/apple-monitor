package com.appleframework.jmx.database.service;

import com.appleframework.jmx.database.entity.AlertGroupContactEntity;

public interface AlertGroupContactService {
	
	public AlertGroupContactEntity get(Integer id);
	
	public void update(AlertGroupContactEntity entity);
	
	public void insert(AlertGroupContactEntity entity);
	
}