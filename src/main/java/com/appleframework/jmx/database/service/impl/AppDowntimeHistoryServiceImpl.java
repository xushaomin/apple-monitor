package com.appleframework.jmx.database.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.constant.StateType;
import com.appleframework.jmx.database.dao.AppDowntimeHistoryDao;
import com.appleframework.jmx.database.entity.AppDowntimeHistoryEntity;
import com.appleframework.jmx.database.service.AppDowntimeHistoryService;

@Service("appDowntimeHistoryService")
public class AppDowntimeHistoryServiceImpl implements AppDowntimeHistoryService {

	@Resource
	private AppDowntimeHistoryDao appDowntimeHistoryDao;

	public AppDowntimeHistoryEntity get(Integer id) {
		return appDowntimeHistoryDao.get(id);
	}
	
	public void update(AppDowntimeHistoryEntity historyEntity) {
		appDowntimeHistoryDao.update(historyEntity);
	}
	
	public void save(AppDowntimeHistoryEntity historyEntity) {
		historyEntity.setState(StateType.START.getIndex());
		historyEntity.setCreateTime(new Date());
		appDowntimeHistoryDao.save(historyEntity);
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
		return appDowntimeHistoryDao.findAll();
	}
	
	public void delete(Integer id) {
		AppDowntimeHistoryEntity history = this.get(id);
		history.setState(StateType.DELETE.getIndex());
		this.update(history);
	}

}