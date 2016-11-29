package com.appleframework.jmx.database.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appleframework.jmx.database.constant.StateType;
import com.appleframework.jmx.database.entity.AppDowntimeEntity;
import com.appleframework.jmx.database.entity.AppDowntimeEntityExample;
import com.appleframework.jmx.database.mapper.AppDowntimeEntityMapper;

@Repository
public class AppDowntimeDao {

	@Resource
	private AppDowntimeEntityMapper appDowntimeEntityMapper;

	public AppDowntimeEntity get(Integer id) {
		return appDowntimeEntityMapper.selectByPrimaryKey(id);
	}

	public void update(AppDowntimeEntity appDowntime) {
		appDowntime.setUpdateTime(new Date());
		appDowntimeEntityMapper.updateByPrimaryKey(appDowntime);
	}

	public void save(AppDowntimeEntity appDowntime) {
		appDowntime.setState(StateType.START.getIndex());
		appDowntime.setCreateTime(new Date());
		appDowntimeEntityMapper.insert(appDowntime);
	}

	public List<AppDowntimeEntity> findAll() {
		AppDowntimeEntityExample example = new AppDowntimeEntityExample();
		example.createCriteria().andStateEqualTo(StateType.START.getIndex());
		return appDowntimeEntityMapper.selectByExample(example);
	}

}