package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.entity.AppInfoEntity;
import com.appleframework.jmx.database.entity.AppInfoEntityExample;
import com.appleframework.jmx.database.mapper.AppInfoEntityMapper;
import com.appleframework.jmx.database.service.AppInfoService;

@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService {

	@Resource
	private AppInfoEntityMapper appInfoEntityMapper;
	
	public AppInfoEntity get(Integer id) {
		return appInfoEntityMapper.selectByPrimaryKey(id);
	}
	
	public void update(AppInfoEntity appInfo) {
		appInfo.setUpdateTime(new Date());
		appInfoEntityMapper.updateByPrimaryKey(appInfo);
	}
	
	public void insert(AppInfoEntity appInfo) {
		Date now = new Date();
		appInfo.setCreateTime(now);
		appInfo.setUpdateTime(now);
		appInfoEntityMapper.insert(appInfo);
	}
	
	public boolean isExistByNodeAndCluster(Integer nodeId, Integer clusterId) {
	    if(this.countByNodeAndCluster(nodeId, clusterId) > 0) {
	    	return true;
	    } else {
			return false;
		}
	}
	
	public int countByNodeAndCluster(Integer nodeId, Integer clusterId) {
		AppInfoEntityExample example = new AppInfoEntityExample();
		example.createCriteria().andNodeIdEqualTo(nodeId).andClusterIdEqualTo(clusterId);
		return appInfoEntityMapper.countByExample(example);
	}
	
	public AppInfoEntity getByNodeAndCluster(Integer nodeId, Integer clusterId) {
		AppInfoEntityExample example = new AppInfoEntityExample();
		example.createCriteria().andNodeIdEqualTo(nodeId).andClusterIdEqualTo(clusterId);
		List<AppInfoEntity> list = appInfoEntityMapper.selectByExample(example);
		if(list.size() > 0) {
			return list.get(0);
		}
		else {
			return null;
		}
	}
	
	public AppInfoEntity saveOrUpdate(AppInfoEntity appInfo) {
		AppInfoEntity existAppInfo = this.getByNodeAndCluster(appInfo.getNodeId(), appInfo.getClusterId());
		if(null == existAppInfo) {
			this.insert(appInfo);
			return appInfo;
		}
		else {
			String[] ignoreProperties = {"id", "createTime", "disorder", "remark", "state"};
			BeanUtils.copyProperties(appInfo, existAppInfo, ignoreProperties);
			this.update(existAppInfo);
			return existAppInfo;
		}
	}
		
	public List<AppInfoEntity> findAll() {
		AppInfoEntityExample example = new AppInfoEntityExample();
		example.createCriteria();
		return appInfoEntityMapper.selectByExample(example);
	}
	
	public List<AppInfoEntity> findListByClusterId(Integer clusterId) {
		AppInfoEntityExample example = new AppInfoEntityExample();
		example.createCriteria().andClusterIdEqualTo(clusterId);
		return appInfoEntityMapper.selectByExample(example);
	}
	
	public int countByClusterId(Integer clusterId) {
		AppInfoEntityExample example = new AppInfoEntityExample();
		example.createCriteria().andClusterIdEqualTo(clusterId);
		return appInfoEntityMapper.countByExample(example);
	}
	

}