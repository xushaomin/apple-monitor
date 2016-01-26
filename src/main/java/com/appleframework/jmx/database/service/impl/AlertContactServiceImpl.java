package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.appleframework.exception.AppleException;
import com.appleframework.exception.ServiceException;
import com.appleframework.jmx.database.entity.AlertContactEntity;
import com.appleframework.jmx.database.entity.AlertContactEntityExample;
import com.appleframework.jmx.database.mapper.AlertContactEntityMapper;
import com.appleframework.jmx.database.mapper.extend.AlertContactExtendMapper;
import com.appleframework.jmx.database.service.AlertContactService;
import com.appleframework.jmx.database.service.AlertGroupContactService;

@Service("alertContactService")
public class AlertContactServiceImpl implements AlertContactService {
	
	@Resource
	private AlertContactEntityMapper alertContactEntityMapper;
	
	@Resource
	private AlertContactExtendMapper alertContactExtendMapper;
	
	@Resource
	private AlertGroupContactService alertGroupContactService;
	
	public AlertContactEntity get(Integer id) {
		return alertContactEntityMapper.selectByPrimaryKey(id);
	}
	
	public void update(AlertContactEntity entity) {
		entity.setUpdateTime(new Date());
		alertContactEntityMapper.updateByPrimaryKey(entity);
	}
	
	public void insert(AlertContactEntity entity) {
		Date now = new Date();
		entity.setCreateTime(now);
		entity.setUpdateTime(now);
		alertContactEntityMapper.insert(entity);
	}
	
	public boolean isExistByName(String name) {
	    if(this.countByName(name) > 0) {
	    	return true;
	    } else {
			return false;
		}
	}
	
	public int countByName(String name) {
		AlertContactEntityExample example = new AlertContactEntityExample();
		example.createCriteria().andNameEqualTo(name);
		return alertContactEntityMapper.countByExample(example);
	}
	
	public boolean isUniqueByName(String oldName, String newName) {
		if (StringUtils.equalsIgnoreCase(oldName, newName)) {
			return true;
		} else {
			if (this.isExistByName(newName)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	public void delete(Integer id) throws AppleException {
		if(alertGroupContactService.isExistByContactId(id)) {
			AlertContactEntity entity = this.get(id);
			throw new ServiceException("ERROR", entity.getName() + "在分组中存在，不能删除！");
		}
		else {
			alertContactEntityMapper.deleteByPrimaryKey(id);
		}
	}
	
	public List<AlertContactEntity> findAll() {
		AlertContactEntityExample example = new AlertContactEntityExample();
		example.createCriteria();
		return alertContactEntityMapper.selectByExample(example);
	}
}