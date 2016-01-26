package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.entity.AlertContactEntity;
import com.appleframework.jmx.database.entity.AlertGroupContactEntity;
import com.appleframework.jmx.database.entity.AlertGroupContactEntityExample;
import com.appleframework.jmx.database.mapper.AlertGroupContactEntityMapper;
import com.appleframework.jmx.database.mapper.extend.AlertGroupContactExtendMapper;
import com.appleframework.jmx.database.service.AlertGroupContactService;

@Service("alertGroupContactService")
public class AlertGroupContactServiceImpl implements AlertGroupContactService {

	@Resource
	private AlertGroupContactEntityMapper alertGroupContactEntityMapper;
	
	@Resource
	private AlertGroupContactExtendMapper alertGroupContactExtendMapper;
	
	public List<AlertContactEntity> findAlertContactListByGroupId(Integer alertGroupId) {
		return alertGroupContactExtendMapper.selectAlertContactByAlertGroupId(alertGroupId);
	}
	
	public List<AlertGroupContactEntity> findListByGroupId(Integer groupId) {
		AlertGroupContactEntityExample example = new AlertGroupContactEntityExample();
		example.createCriteria().andGroupIdEqualTo(groupId);
		return alertGroupContactEntityMapper.selectByExample(example);
	}
	
	public AlertGroupContactEntity get(Integer id) {
		return alertGroupContactEntityMapper.selectByPrimaryKey(id);
	}
	
	public void update(AlertGroupContactEntity entity) {
		entity.setUpdateTime(new Date());
		alertGroupContactEntityMapper.updateByPrimaryKey(entity);
	}
	
	public void delete(Integer id) {
		alertGroupContactEntityMapper.deleteByPrimaryKey(id);
	}
	
	public void insert(AlertGroupContactEntity entity) {
		Date now = new Date();
		entity.setCreateTime(now);
		alertGroupContactEntityMapper.insert(entity);
	}
	
	public boolean isExistByContactId(Integer contactId) {
		if(countByContactId(contactId) > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int countByContactId(Integer contactId) {
		AlertGroupContactEntityExample example = new AlertGroupContactEntityExample();
		example.createCriteria().andContactIdEqualTo(contactId);
		return alertGroupContactEntityMapper.countByExample(example);
	}
	
}