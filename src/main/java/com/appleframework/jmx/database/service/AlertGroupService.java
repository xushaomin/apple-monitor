package com.appleframework.jmx.database.service;

import java.util.List;

import com.appleframework.jmx.database.entity.AlertGroupEntity;

public interface AlertGroupService {
	
	public List<AlertGroupEntity> findAll();
	
}