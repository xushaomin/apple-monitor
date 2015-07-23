package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.constant.StateType;
import com.appleframework.jmx.database.entity.AppConfigEntity;
import com.appleframework.jmx.database.entity.AppConfigEntityExample;
import com.appleframework.jmx.database.mapper.AppConfigEntityMapper;
import com.appleframework.jmx.database.service.AppConfigService;


@Service("appConfigService")
public class AppConfigServiceImpl implements AppConfigService {

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
		appConfigEntityMapper.insert(appConfig);
	}
	
	
	public AppConfigEntity saveOrUpdate(AppConfigEntity appConfig) {
		AppConfigEntity existAppConfig = this.get(appConfig.getId());
		if(null == existAppConfig) {
			this.insert(appConfig);
			return appConfig;
		}
		else {
			String[] ignoreProperties = {"id"};
			BeanUtils.copyProperties(appConfig, existAppConfig, ignoreProperties);
			this.update(existAppConfig);
			return appConfig;
		}
	}
	
	public List<AppConfigEntity> findAll() {
		AppConfigEntityExample example = new AppConfigEntityExample();
		example.createCriteria();
		return appConfigEntityMapper.selectByExample(example);
	}
	
	public List<AppConfigEntity> findListByClusterId(Integer clusterId) {
		AppConfigEntityExample example = new AppConfigEntityExample();
		example.createCriteria()
			.andClusterIdEqualTo(clusterId)
			.andStateBetween(StateType.STOP.getIndex(), StateType.START.getIndex());
		return appConfigEntityMapper.selectByExample(example);
	}
	
	public List<AppConfigEntity> findListByClusterIdAndStart(Integer clusterId) {
		AppConfigEntityExample example = new AppConfigEntityExample();
		example.createCriteria().andClusterIdEqualTo(clusterId).andStateEqualTo(StateType.START.getIndex());
		return appConfigEntityMapper.selectByExample(example);
	}

}