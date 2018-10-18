/*package com.appleframework.jmx.database.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.constant.StateType;
import com.appleframework.jmx.database.dao.AppDowntimeDao;
import com.appleframework.jmx.database.entity.AppDowntimeEntity;
import com.appleframework.jmx.database.service.AppDowntimeService;

@Service("appDowntimeService")
public class AppDowntimeServiceImpl implements AppDowntimeService {

	@Resource
	private AppDowntimeDao appDowntimeDao;

	public AppDowntimeEntity get(Integer id) {
		return appDowntimeDao.get(id);
	}

	public void update(AppDowntimeEntity appDowntime) {
		appDowntimeDao.update(appDowntime);
	}

	public void save(AppDowntimeEntity appDowntime) {
		appDowntimeDao.save(appDowntime);
	}

	public void saveOrUpdate(Integer id, long recordingSince) {
		AppDowntimeEntity appDowntime = this.get(id);
		if (null != appDowntime) {
			appDowntime.setRecordingStart(new Timestamp(recordingSince));
			appDowntime.setRecordingEnd(new Timestamp(recordingSince + 630720000000L));
			appDowntime.setIsDown(false);
			this.update(appDowntime);
		} else {
			appDowntime = new AppDowntimeEntity();
			appDowntime.setId(id);
			appDowntime.setRecordingStart(new Timestamp(recordingSince));
			appDowntime.setRecordingEnd(new Timestamp(recordingSince + 630720000000L));
			appDowntime.setIsDown(false);
			this.save(appDowntime);
		}
	}
	
	@Async
	public void saveOrUpdate(Integer id, long recordingSince, boolean isDown) {
		AppDowntimeEntity appDowntime = this.get(id);
		if (null != appDowntime) {
			if(appDowntime.getIsDown().booleanValue() != isDown) {
				appDowntime.setIsDown(isDown);
				this.update(appDowntime);
			}
		} else {
			appDowntime = new AppDowntimeEntity();
			appDowntime.setId(id);
			appDowntime.setRecordingStart(new Timestamp(recordingSince));
			appDowntime.setRecordingEnd(new Timestamp(recordingSince + 630720000000L));
			appDowntime.setIsDown(isDown);
			this.save(appDowntime);
		}
	}

	public List<AppDowntimeEntity> findAll() {
		return appDowntimeDao.findAll();
	}

	@Override
	public void delete(Integer id) {
		AppDowntimeEntity appDowntime = this.get(id);
		appDowntime.setState(StateType.DELETE.getIndex());
		this.update(appDowntime);

	}

}*/