package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.entity.AppClusterEntity;
import com.appleframework.jmx.database.entity.AppClusterEntityExample;
import com.appleframework.jmx.database.mapper.AppClusterEntityMapper;
import com.appleframework.jmx.database.service.AppClusterService;
import com.appleframework.jmx.database.service.AppInfoService;

@Service("appClusterService")
public class AppClusterServiceImpl implements AppClusterService {

	@Resource
	private AppClusterEntityMapper appClusterEntityMapper;
	
	@Resource
	private AppInfoService appInfoService;

	public AppClusterEntity get(Integer id) {
		return appClusterEntityMapper.selectByPrimaryKey(id);
	}
	
	public void update(AppClusterEntity appCluster) {
		appClusterEntityMapper.updateByPrimaryKey(appCluster);
	}
	
	public void save(AppClusterEntity appCluster) {
		Date now = new Date();
		appCluster.setCreateTime(now);
		appCluster.setUpdateTime(now);
		appClusterEntityMapper.insert(appCluster);
	}
	
	public boolean isExistByName(String name) {
	    if(this.countByName(name) > 0) {
	    	return true;
	    } else {
			return false;
		}
	}
	
	public int countByName(String name) {
		AppClusterEntityExample example = new AppClusterEntityExample();
		example.createCriteria().andClusterNameEqualTo(name);
		return appClusterEntityMapper.countByExample(example);
	}
	
	public AppClusterEntity getByName(String name) {
		AppClusterEntityExample example = new AppClusterEntityExample();
		example.createCriteria().andClusterNameEqualTo(name);
		List<AppClusterEntity> list = appClusterEntityMapper.selectByExample(example);
		if(list.size() > 0) {
			return list.get(0);
		}
		else {
			return null;
		}
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
		AppClusterEntity appCluster = this.getByName(name);
		if(null == appCluster) {
			appCluster = new AppClusterEntity();
			appCluster.setClusterName(name);
			appCluster.setClusterDesc(name);
			appCluster.setCreateTime(new Date());
			appCluster.setDisorder(1);
			appCluster.setRemark("");
			appCluster.setState((short)1);
			appCluster.setIsCluster((short)0);
			appCluster.setAppNum(0);
			this.save(appCluster);
			return appCluster;
		}
		else {
			return appCluster;
		}
	}
	
	public List<AppClusterEntity> findAll() {
		AppClusterEntityExample example = new AppClusterEntityExample();
		example.createCriteria();
		return appClusterEntityMapper.selectByExample(example);
	}
	
	public void calibratedAppNum(Integer id) {
		int appNum = appInfoService.countByClusterId(id);
		AppClusterEntity entity = this.get(id);
		if(appNum > 1) {
			entity.setIsCluster((short)1);
		}
		else {
			entity.setIsCluster((short)0);
		}
		entity.setAppNum(appNum);
		this.update(entity);
	}

}