package com.appleframework.jmx.database.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appleframework.jmx.database.entity.AppGroupEntity;
import com.appleframework.jmx.database.entity.AppGroupEntityExample;
import com.appleframework.jmx.database.mapper.AppGroupEntityMapper;

@Repository
public class AppGroupDao {

	@Resource
	private AppGroupEntityMapper appGroupEntityMapper;

	public List<AppGroupEntity> findAll() {
		AppGroupEntityExample example = new AppGroupEntityExample();
		example.createCriteria();
		return appGroupEntityMapper.selectByExample(example);
	}

	public AppGroupEntity get(Integer id) {
		return appGroupEntityMapper.selectByPrimaryKey(id);
	}

	public void update(AppGroupEntity entity) {
		entity.setUpdateTime(new Date());
		appGroupEntityMapper.updateByPrimaryKey(entity);
	}

	public void insert(AppGroupEntity entity) {
		Date now = new Date();
		entity.setCreateTime(now);
		appGroupEntityMapper.insert(entity);
	}

	public int countByName(String name) {
		AppGroupEntityExample example = new AppGroupEntityExample();
		example.createCriteria().andNameEqualTo(name);
		return appGroupEntityMapper.countByExample(example);
	}

	public AppGroupEntity getByName(String name) {
		AppGroupEntityExample example = new AppGroupEntityExample();
		example.createCriteria().andNameEqualTo(name);
		List<AppGroupEntity> list = appGroupEntityMapper.selectByExample(example);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

}