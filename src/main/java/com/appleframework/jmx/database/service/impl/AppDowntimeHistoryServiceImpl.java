package com.appleframework.jmx.database.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.entity.AppDowntimeHistoryEntity;
import com.appleframework.jmx.database.entity.AppDowntimeHistoryEntityExample;
import com.appleframework.jmx.database.mapper.AppDowntimeHistoryEntityMapper;
import com.appleframework.jmx.database.service.AppDowntimeHistoryService;

@Service("appDowntimeHistoryService")
public class AppDowntimeHistoryServiceImpl implements AppDowntimeHistoryService {

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
	
	public void saveOrUpdate(Integer id, long downtimeBegin, long downtimeEnd) {
		AppDowntimeHistoryEntity history = this.get(id);
    	if(null != history) {
        	history.setStartTime(new Timestamp(downtimeBegin));
        	history.setEndTime(new Timestamp(downtimeEnd));
        	this.update(history);
    	}
    	else {
    		history = new AppDowntimeHistoryEntity();
        	history.setId(id);
        	history.setStartTime(new Timestamp(downtimeBegin));
        	history.setEndTime(new Timestamp(downtimeEnd));
        	this.save(history);
    	}
	}
	
	public List<AppDowntimeHistoryEntity> findAll() {
		AppDowntimeHistoryEntityExample example = new AppDowntimeHistoryEntityExample();
		example.createCriteria();
		return appDowntimeHistoryEntityMapper.selectByExample(example);
	}

}