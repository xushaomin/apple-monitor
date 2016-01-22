package com.appleframework.jmx.database.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.entity.AlertGroupContactEntity;
import com.appleframework.jmx.database.mapper.AlertGroupContactEntityMapper;
import com.appleframework.jmx.database.service.AlertGroupContactService;

@Service("alertGroupContactService")
public class AlertGroupContactServiceImpl implements AlertGroupContactService {

	@Resource
	private AlertGroupContactEntityMapper alertGroupContactEntityMapper;
	
	public AlertGroupContactEntity get(Integer id) {
		return alertGroupContactEntityMapper.selectByPrimaryKey(id);
	}
	
	public void update(AlertGroupContactEntity entity) {
		entity.setUpdateTime(new Date());
		alertGroupContactEntityMapper.updateByPrimaryKey(entity);
	}
	
	public void insert(AlertGroupContactEntity entity) {
		Date now = new Date();
		entity.setCreateTime(now);
		alertGroupContactEntityMapper.insert(entity);
	}
	
}