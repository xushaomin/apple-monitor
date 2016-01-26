package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.appleframework.exception.AppleException;
import com.appleframework.exception.ServiceException;
import com.appleframework.jmx.database.entity.AlertGroupEntity;
import com.appleframework.jmx.database.entity.AlertGroupEntityExample;
import com.appleframework.jmx.database.mapper.AlertGroupEntityMapper;
import com.appleframework.jmx.database.service.AlertGroupService;
import com.appleframework.jmx.database.service.AppConfigService;

@Service("alertGroupService")
public class AlertGroupServiceImpl implements AlertGroupService {

	@Resource
	private AlertGroupEntityMapper alertGroupEntityMapper;
	
	@Resource
	private AppConfigService appConfigService;
	
	public List<AlertGroupEntity> findAll() {
		AlertGroupEntityExample example = new AlertGroupEntityExample();
		example.createCriteria();
		return alertGroupEntityMapper.selectByExample(example);
	}
	
	public AlertGroupEntity get(Integer id) {
		return alertGroupEntityMapper.selectByPrimaryKey(id);
	}
	
	public void update(AlertGroupEntity entity) {
		entity.setUpdateTime(new Date());
		alertGroupEntityMapper.updateByPrimaryKey(entity);
	}
	
	public void insert(AlertGroupEntity entity) {
		Date now = new Date();
		entity.setCreateTime(now);
		alertGroupEntityMapper.insert(entity);
	}
	
	public boolean isExistByName(String name) {
		if(countByName(name) > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int countByName(String name) {
		AlertGroupEntityExample example = new AlertGroupEntityExample();
		example.createCriteria().andNameEqualTo(name);
		return alertGroupEntityMapper.countByExample(example);
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
		if(appConfigService.isExistByAlertGroupId(id)) {
			AlertGroupEntity entity = this.get(id);
			throw new ServiceException("ERROR", entity.getName() + "在应用监控中存在，不能删除！");
		}
		else {
			alertGroupEntityMapper.deleteByPrimaryKey(id);
		}
	}
	
}