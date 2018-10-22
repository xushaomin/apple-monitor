package com.appleframework.jmx.database.service.impl;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.dao.AlertTemplDao;
import com.appleframework.jmx.database.entity.AlertTemplEntity;
import com.appleframework.jmx.database.service.AlertTemplService;

@Service("alertTemplService")
public class AlertTemplServiceImpl implements AlertTemplService {

	@Resource
	private AlertTemplDao alertTemplDao;
			
	public AlertTemplEntity get(Integer id) {
		return alertTemplDao.get(id);
	}
	
	public void update(AlertTemplEntity entity) {
		alertTemplDao.update(entity);
	}
	
	public void insert(AlertTemplEntity entity) {
		alertTemplDao.insert(entity);
	}
	
	@Cacheable(value = "alertTemplCache", key = "'getByTypeAndCode.' + #type + '.' + #code ")
	public AlertTemplEntity getByTypeAndCode(Integer type, String code) {
		return alertTemplDao.selectByTypeAndCode(type, code);
	}
		
}