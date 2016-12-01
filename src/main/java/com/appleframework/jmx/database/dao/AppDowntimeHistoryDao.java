package com.appleframework.jmx.database.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appleframework.jmx.database.entity.AppDowntimeHistoryEntity;
import com.appleframework.jmx.database.entity.AppDowntimeHistoryEntityExample;
import com.appleframework.jmx.database.mapper.AppDowntimeHistoryEntityMapper;

@Repository
public class AppDowntimeHistoryDao {

	@Resource
	private AppDowntimeHistoryEntityMapper appDowntimeHistoryEntityMapper;

	public AppDowntimeHistoryEntity get(Integer id) {
		return appDowntimeHistoryEntityMapper.selectByPrimaryKey(id);
	}
	
	public void update(AppDowntimeHistoryEntity historyEntity) {
		historyEntity.setUpdateTime(new Date());
		appDowntimeHistoryEntityMapper.updateByPrimaryKey(historyEntity);
	}
	
	public void save(AppDowntimeHistoryEntity historyEntity) {
		historyEntity.setCreateTime(new Date());
		appDowntimeHistoryEntityMapper.insert(historyEntity);
	}
	
	public List<AppDowntimeHistoryEntity> findAll() {
		AppDowntimeHistoryEntityExample example = new AppDowntimeHistoryEntityExample();
		example.createCriteria();
		return appDowntimeHistoryEntityMapper.selectByExample(example);
	}
	
	public void delete(Integer id) {
		appDowntimeHistoryEntityMapper.deleteByPrimaryKey(id);
	}
	
	public void deleteByAppId(Integer appId) {
		AppDowntimeHistoryEntityExample example = new AppDowntimeHistoryEntityExample();
		example.createCriteria().andAppIdEqualTo(appId);
		appDowntimeHistoryEntityMapper.deleteByExample(example);
	}
	
	public List<AppDowntimeHistoryEntity> findListByAppId(Integer appId) {
		AppDowntimeHistoryEntityExample example = new AppDowntimeHistoryEntityExample();
		example.createCriteria().andAppIdEqualTo(appId);
		return appDowntimeHistoryEntityMapper.selectByExample(example);
	}
	
	public AppDowntimeHistoryEntity get(Integer appId, Date startTime) {
		AppDowntimeHistoryEntityExample example = new AppDowntimeHistoryEntityExample();
		example.createCriteria().andAppIdEqualTo(appId).andStartTimeEqualTo(startTime);
		List<AppDowntimeHistoryEntity> list = appDowntimeHistoryEntityMapper.selectByExample(example);
		if(list.size() > 0) {
			return list.get(0);
		}
		else {
			return null;
		}
	}

}