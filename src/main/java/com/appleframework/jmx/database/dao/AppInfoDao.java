package com.appleframework.jmx.database.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appleframework.jmx.database.constant.StateType;
import com.appleframework.jmx.database.entity.AppInfoEntity;
import com.appleframework.jmx.database.entity.AppInfoEntityExample;
import com.appleframework.jmx.database.mapper.AppInfoEntityMapper;
import com.appleframework.jmx.database.mapper.extend.AppInfoExtendMapper;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.monitor.model.AppInfoBo;
import com.appleframework.monitor.model.AppInfoSo;

@Repository
public class AppInfoDao  {

	@Resource
	private AppInfoEntityMapper appInfoEntityMapper;
	
	@Resource
	private AppInfoExtendMapper appInfoExtendMapper;
	
	public List<AppInfoBo> findByPage(Pagination page, Search search, AppInfoSo so) {
		return appInfoExtendMapper.selectByPage(page, search, so);
	}

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

	public int countByNodeAndCluster(Integer nodeId, Integer clusterId) {
		AppInfoEntityExample example = new AppInfoEntityExample();
		example.createCriteria().andNodeIdEqualTo(nodeId).andClusterIdEqualTo(clusterId);
		return appInfoEntityMapper.countByExample(example);
	}

	public AppInfoEntity getByNodeAndCluster(Integer nodeId, Integer clusterId) {
		AppInfoEntityExample example = new AppInfoEntityExample();
		example.createCriteria().andNodeIdEqualTo(nodeId).andClusterIdEqualTo(clusterId).andStateEqualTo((short) 1);
		List<AppInfoEntity> list = appInfoEntityMapper.selectByExample(example);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
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
		example.createCriteria().andClusterIdEqualTo(clusterId).andStateBetween(StateType.STOP.getIndex(),
				StateType.START.getIndex());
		return appInfoEntityMapper.countByExample(example);
	}

}