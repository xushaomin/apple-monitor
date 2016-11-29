package com.appleframework.jmx.database.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.appleframework.exception.AppleException;
import com.appleframework.exception.ServiceException;
import com.appleframework.jmx.database.dao.AlertGroupDao;
import com.appleframework.jmx.database.entity.AlertGroupEntity;
import com.appleframework.jmx.database.service.AlertGroupService;
import com.appleframework.jmx.database.service.AppConfigService;

@Service("alertGroupService")
public class AlertGroupServiceImpl implements AlertGroupService {

	@Resource
	private AlertGroupDao alertGroupDao;
	
	@Resource
	private AppConfigService appConfigService;
	
	public List<AlertGroupEntity> findAll() {
		return alertGroupDao.findAll();
	}
	
	public AlertGroupEntity get(Integer id) {
		return alertGroupDao.get(id);
	}
	
	public void update(AlertGroupEntity entity) {
		alertGroupDao.update(entity);
	}
	
	public void insert(AlertGroupEntity entity) {
		alertGroupDao.insert(entity);
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
		return alertGroupDao.countByName(name);
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
			alertGroupDao.delete(id);
		}
	}
	
}