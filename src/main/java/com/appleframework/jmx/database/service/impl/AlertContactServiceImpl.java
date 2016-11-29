package com.appleframework.jmx.database.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.appleframework.exception.AppleException;
import com.appleframework.exception.ServiceException;
import com.appleframework.jmx.database.dao.AlertContactDao;
import com.appleframework.jmx.database.entity.AlertContactEntity;
import com.appleframework.jmx.database.service.AlertContactService;
import com.appleframework.jmx.database.service.AlertGroupContactService;

@Service("alertContactService")
public class AlertContactServiceImpl implements AlertContactService {
	
	@Resource
	private AlertContactDao alertContactDao;
		
	@Resource
	private AlertGroupContactService alertGroupContactService;
	
	public AlertContactEntity get(Integer id) {
		return alertContactDao.get(id);
	}
	
	public void update(AlertContactEntity entity) {
		alertContactDao.update(entity);
	}
	
	public void insert(AlertContactEntity entity) {
		alertContactDao.insert(entity);
	}
	
	public boolean isExistByName(String name) {
	    if(this.countByName(name) > 0) {
	    	return true;
	    } else {
			return false;
		}
	}
	
	public int countByName(String name) {
		return alertContactDao.countByName(name);
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
			alertContactDao.delete(id);
		}
	}
	
	public List<AlertContactEntity> findAll() {
		return alertContactDao.findAll();
	}
}