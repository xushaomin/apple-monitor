package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.appleframework.exception.ServiceException;
import com.appleframework.jmx.database.constant.IsCluster;
import com.appleframework.jmx.database.constant.StateType;
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
			appCluster.setState(StateType.START.getIndex());
			appCluster.setIsCluster(IsCluster.NO.getIndex());
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
		example.setOrderByClause("cluster_name");
		example.setDistinct(true);
		return appClusterEntityMapper.selectByExample(example);
	}
	
	public List<AppClusterEntity> findListByStart() {
		AppClusterEntityExample example = new AppClusterEntityExample();
		example.createCriteria().andStateEqualTo(StateType.START.getIndex());
		example.setOrderByClause("cluster_name");
		example.setDistinct(true);
		return appClusterEntityMapper.selectByExample(example);
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
		appClusterEntityMapper.updateByPrimaryKey(entity);
		return id;
	}

}