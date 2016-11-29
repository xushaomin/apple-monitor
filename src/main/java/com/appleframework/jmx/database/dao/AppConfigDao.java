package com.appleframework.jmx.database.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appleframework.jmx.database.constant.StateType;
import com.appleframework.jmx.database.entity.AppConfigEntity;
import com.appleframework.jmx.database.entity.AppConfigEntityExample;
import com.appleframework.jmx.database.mapper.AppConfigEntityMapper;

@Repository
public class AppConfigDao {

	@Resource
	private AppConfigEntityMapper appConfigEntityMapper;

	public AppConfigEntity get(Integer id) {
		return appConfigEntityMapper.selectByPrimaryKey(id);
	}

	public void update(AppConfigEntity appConfig) {
		appConfig.setUpdateTime(new Date());
		appConfigEntityMapper.updateByPrimaryKey(appConfig);
	}

	public void insert(AppConfigEntity appConfig) {
		Date now = new Date();
		appConfig.setCreateTime(now);
		appConfig.setUpdateTime(now);
		appConfig.setIsAlert(false);
		appConfigEntityMapper.insert(appConfig);
	}

	public List<AppConfigEntity> findAll() {
		AppConfigEntityExample example = new AppConfigEntityExample();
		example.createCriteria();
		return appConfigEntityMapper.selectByExample(example);
	}

	public List<AppConfigEntity> findListByClusterId(Integer clusterId) {
		AppConfigEntityExample example = new AppConfigEntityExample();
		example.createCriteria().andClusterIdEqualTo(clusterId).andStateBetween(StateType.STOP.getIndex(),
				StateType.START.getIndex());
		return appConfigEntityMapper.selectByExample(example);
	}

	public List<AppConfigEntity> findListByClusterIdAndStart(Integer clusterId) {
		AppConfigEntityExample example = new AppConfigEntityExample();
		example.createCriteria().andClusterIdEqualTo(clusterId).andStateEqualTo(StateType.START.getIndex());
		example.setOrderByClause("id");
		example.setDistinct(true);
		return appConfigEntityMapper.selectByExample(example);
	}

	public List<AppConfigEntity> findListByIsAlert() {
		AppConfigEntityExample example = new AppConfigEntityExample();
		example.createCriteria().andIsAlertEqualTo(true).andStateEqualTo(StateType.START.getIndex());
		example.setDistinct(true);
		return appConfigEntityMapper.selectByExample(example);
	}

	public int countByAlertGroupId(Integer alertGroupId) {
		AppConfigEntityExample example = new AppConfigEntityExample();
		example.createCriteria().andAlertGroupIdEqualTo(alertGroupId);
		return appConfigEntityMapper.countByExample(example);
	}

}