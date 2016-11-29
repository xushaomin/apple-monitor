package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.appleframework.exception.AppleException;
import com.appleframework.jmx.database.dao.AppGroupDao;
import com.appleframework.jmx.database.entity.AppGroupEntity;
import com.appleframework.jmx.database.service.AppConfigService;
import com.appleframework.jmx.database.service.AppGroupService;

@Service("appGroupService")
public class AppGroupServiceImpl implements AppGroupService {

	@Resource
	private AppGroupDao appGroupDao;
	
	@Resource
	private AppConfigService appConfigService;
	
	public List<AppGroupEntity> findAll() {
		return appGroupDao.findAll();
	}
	
	public AppGroupEntity get(Integer id) {
		return appGroupDao.get(id);
	}
	
	public void update(AppGroupEntity entity) {
		appGroupDao.update(entity);
	}
	
	public void save(AppGroupEntity entity) {
		appGroupDao.insert(entity);
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
		return appGroupDao.countByName(name);
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
	
	public AppGroupEntity getByName(String name) {
		return appGroupDao.getByName(name);
	}
	
	public void delete(Integer id) throws AppleException {
		/*if(appConfigService.isExistByAppGroupId(id)) {
			AppGroupEntity entity = this.get(id);
			throw new ServiceException("ERROR", entity.getName() + "在应用监控中存在，不能删除！");
		}
		else {
			appGroupDao.deleteByPrimaryKey(id);
		}*/
	}
	
	public AppGroupEntity saveWithName(String name) {
		AppGroupEntity appGroup = this.getByName(name);
		if(null == appGroup) {
			appGroup = new AppGroupEntity();
			appGroup.setName(name);
			appGroup.setCreateTime(new Date());
			this.save(appGroup);
			return appGroup;
		}
		else {
			return appGroup;
		}
	}
	
}