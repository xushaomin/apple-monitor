package com.appleframework.jmx.database.service;

import java.util.List;

import com.appleframework.jmx.database.entity.AlertContactEntity;

public interface AlertContactService {
	
	public List<AlertContactEntity> findListByGroupId(Integer alertGroupId);
	
}