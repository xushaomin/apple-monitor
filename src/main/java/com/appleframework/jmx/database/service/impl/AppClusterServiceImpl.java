package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.appleframework.exception.ServiceException;
import com.appleframework.jmx.database.constant.IsCluster;
import com.appleframework.jmx.database.constant.StateType;
import com.appleframework.jmx.database.dao.AppClusterDao;
import com.appleframework.jmx.database.entity.AppClusterEntity;
import com.appleframework.jmx.database.entity.AppGroupEntity;
import com.appleframework.jmx.database.service.AppClusterService;
import com.appleframework.jmx.database.service.AppGroupService;
import com.appleframework.jmx.database.service.AppInfoService;

@Service("appClusterService")
public class AppClusterServiceImpl implements AppClusterService {

	@Resource
	private AppClusterDao appClusterDao;
	
	@Resource
	private AppInfoService appInfoService;
	
	@Resource
	private AppGroupService appGroupService;

	public AppClusterEntity get(Integer id) {
		return appClusterDao.get(id);
	}
	
	public void update(AppClusterEntity appCluster) {
		appClusterDao.update(appCluster);
	}
	
	public void save(AppClusterEntity appCluster) {
		appClusterDao.save(appCluster);
	}
	
	public boolean isExistByName(String name) {
	    if(this.countByName(name) > 0) {
	    	return true;
	    } else {
			return false;
		}
	}
	
	public int countByName(String name) {
		return appClusterDao.countByName(name);
	}
	
	public AppClusterEntity getByName(String name) {
		return appClusterDao.getByName(name);
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
	
	public AppClusterEntity saveWithName(String name) {
		AppGroupEntity appGroup = null;
		String[] names = name.split("-");
		if(names.length > 0) {
			String groupName = names[0].trim();
			appGroup = appGroupService.saveWithName(groupName);
		}
		AppClusterEntity appCluster = this.getByName(name);
		if(null == appCluster) {
			appCluster = new AppClusterEntity();
			appCluster.setClusterName(name);
			appCluster.setClusterDesc(name);
			appCluster.setCreateTime(new Date());
			appCluster.setDisorder(1);
			appCluster.setRemark("");
			appCluster.setState(StateType.START.getIndex());
			appCluster.setIsCluster(IsCluster.NO.getIndex());
			appCluster.setAppNum(0);
			appCluster.setGroupId(appGroup.getId());
			this.save(appCluster);
			return appCluster;
		}
		else {
			if(null == appCluster.getGroupId()) {
				appCluster.setGroupId(appGroup.getId());
				this.update(appCluster);
			}
			return appCluster;
		}
	}
	
	public List<AppClusterEntity> findAll() {
		return appClusterDao.findAll();
	}
	
	public List<AppClusterEntity> findListByStart() {
		return appClusterDao.findListByStart();
	}
	
	//跳转AppNum字段个数
	public void calibratedAppNum(Integer id) {
		int appNum = appInfoService.countByClusterId(id);
		AppClusterEntity entity = this.get(id);
		if(appNum > 1) {
			entity.setIsCluster(IsCluster.YES.getIndex());
		}
		else {
			entity.setIsCluster(IsCluster.NO.getIndex());
		}
		entity.setAppNum(appNum);
		this.update(entity);
	}
	
	public Integer delete(Integer id) throws ServiceException {
		int appNum = appInfoService.countByClusterId(id);
		if(appNum > 0) {
			throw new ServiceException("ERROR", "还有应用存在，不能删除");
		}
		
		AppClusterEntity entity = this.get(id);
		entity.setState(StateType.DELETE.getIndex());
		appClusterDao.update(entity);
		return id;
	}

}