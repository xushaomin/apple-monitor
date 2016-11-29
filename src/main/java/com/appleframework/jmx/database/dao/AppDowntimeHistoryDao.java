package com.appleframework.jmx.database.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appleframework.jmx.database.constant.StateType;
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
		historyEntity.setState(StateType.START.getIndex());
		historyEntity.setCreateTime(new Date());
		appDowntimeHistoryEntityMapper.insert(historyEntity);
	}
	
	public List<AppDowntimeHistoryEntity> findAll() {
		AppDowntimeHistoryEntityExample example = new AppDowntimeHistoryEntityExample();
		example.createCriteria();
		return appDowntimeHistoryEntityMapper.selectByExample(example);
	}

}