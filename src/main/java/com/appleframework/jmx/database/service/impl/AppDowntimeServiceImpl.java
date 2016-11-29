package com.appleframework.jmx.database.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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
			appDowntime.setCreateTime(new Date());
			this.update(appDowntime);
		} else {
			appDowntime = new AppDowntimeEntity();
			appDowntime.setId(id);
			appDowntime.setRecordingStart(new Timestamp(recordingSince));
			appDowntime.setRecordingEnd(new Timestamp(recordingSince + 630720000000L));
			appDowntime.setCreateTime(new Date());
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

}