/*package com.appleframework.jmx.database.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
		historyEntity.setCreateTime(new Date());
		appDowntimeHistoryDao.save(historyEntity);
	}
	
	@Async
	public void saveOrUpdate(Integer appId, long downtimeBegin, long downtimeEnd) {
		Date startTime = new Date(downtimeBegin);
		AppDowntimeHistoryEntity history = appDowntimeHistoryDao.get(appId, startTime);
		if(null != history) {
			history.setEndTime(new Timestamp(downtimeEnd));
			this.update(history);
		}
		else {
			history = new AppDowntimeHistoryEntity();
			history.setAppId(appId);
			history.setStartTime(new Timestamp(downtimeBegin));
			history.setEndTime(new Timestamp(downtimeEnd));
			this.save(history);
		}
	}
	
	public List<AppDowntimeHistoryEntity> findAll() {
		return appDowntimeHistoryDao.findAll();
	}
	
	public List<AppDowntimeHistoryEntity> findListByAppId(Integer appId) {
		return appDowntimeHistoryDao.findListByAppId(appId);
	}
	
	public void delete(Integer id) {
		appDowntimeHistoryDao.delete(id);
	}
	
	public void deleteByAppId(Integer appId) {
		appDowntimeHistoryDao.deleteByAppId(appId);
	}

}*/