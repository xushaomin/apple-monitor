package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.appleframework.exception.AppleException;
import com.appleframework.jmx.database.entity.AppGroupEntity;
import com.appleframework.jmx.database.entity.AppGroupEntityExample;
import com.appleframework.jmx.database.mapper.AppGroupEntityMapper;
import com.appleframework.jmx.database.service.AppGroupService;
import com.appleframework.jmx.database.service.AppConfigService;

@Service("appGroupService")
public class AppGroupServiceImpl implements AppGroupService {

	@Resource
	private AppGroupEntityMapper appGroupEntityMapper;
	
	@Resource
	private AppConfigService appConfigService;
	
	public List<AppGroupEntity> findAll() {
		AppGroupEntityExample example = new AppGroupEntityExample();
		example.createCriteria();
		return appGroupEntityMapper.selectByExample(example);
	}
	
	public AppGroupEntity get(Integer id) {
		return appGroupEntityMapper.selectByPrimaryKey(id);
	}
	
	public void update(AppGroupEntity entity) {
		entity.setUpdateTime(new Date());
		appGroupEntityMapper.updateByPrimaryKey(entity);
	}
	
	public void save(AppGroupEntity entity) {
		Date now = new Date();
		entity.setCreateTime(now);
		appGroupEntityMapper.insert(entity);
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
		AppGroupEntityExample example = new AppGroupEntityExample();
		example.createCriteria().andNameEqualTo(name);
		return appGroupEntityMapper.countByExample(example);
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
		AppGroupEntityExample example = new AppGroupEntityExample();
		example.createCriteria().andNameEqualTo(name);
		List<AppGroupEntity> list = appGroupEntityMapper.selectByExample(example);
		if(list.size() > 0) {
			return list.get(0);
		}
		else {
			return null;
		}
	}
	
	public void delete(Integer id) throws AppleException {
		/*if(appConfigService.isExistByAppGroupId(id)) {
			AppGroupEntity entity = this.get(id);
			throw new ServiceException("ERROR", entity.getName() + "在应用监控中存在，不能删除！");
		}
		else {
			appGroupEntityMapper.deleteByPrimaryKey(id);
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