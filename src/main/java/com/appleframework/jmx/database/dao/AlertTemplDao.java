package com.appleframework.jmx.database.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appleframework.exception.AppleException;
import com.appleframework.jmx.database.entity.AlertTemplEntity;
import com.appleframework.jmx.database.mapper.AlertTemplEntityMapper;
import com.appleframework.jmx.database.mapper.AlertTemplExtendMapper;

@Repository
public class AlertTemplDao {

	@Resource
	private AlertTemplEntityMapper alertTemplEntityMapper;
	
	@Resource
	private AlertTemplExtendMapper alertTemplExtendMapper;
		
	public AlertTemplEntity get(Integer id) {
		return alertTemplEntityMapper.selectByPrimaryKey(id);
	}
	
	public void update(AlertTemplEntity entity) {
		entity.setUpdateTime(new Date());
		alertTemplEntityMapper.updateByPrimaryKey(entity);
	}
	
	public void insert(AlertTemplEntity entity) {
		Date now = new Date();
		entity.setCreateTime(now);
		alertTemplEntityMapper.insert(entity);
	}
		
	public void delete(Integer id) throws AppleException {
		alertTemplEntityMapper.deleteByPrimaryKey(id);
	}
	
	public AlertTemplEntity selectByTypeAndCode(Integer type, String code) {
		return alertTemplExtendMapper.selectByTypeAndCode(type, code);
	}
	
}