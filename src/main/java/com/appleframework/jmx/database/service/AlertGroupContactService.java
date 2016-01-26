package com.appleframework.jmx.database.service;

import java.util.List;

import com.appleframework.jmx.database.entity.AlertContactEntity;
import com.appleframework.jmx.database.entity.AlertGroupContactEntity;

public interface AlertGroupContactService {
	
	public List<AlertContactEntity> findAlertContactListByGroupId(Integer alertGroupId);
	
	public AlertGroupContactEntity get(Integer id);
	
	public void delete(Integer id);
	
	public void update(AlertGroupContactEntity entity);
	
	public void insert(AlertGroupContactEntity entity);
	
	public boolean isExistByContactId(Integer contactId);
	
	public int countByContactId(Integer contactId);
	
	public List<AlertGroupContactEntity> findListByGroupId(Integer groupId);
	
}