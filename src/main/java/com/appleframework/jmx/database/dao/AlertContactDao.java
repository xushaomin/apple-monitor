package com.appleframework.jmx.database.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appleframework.exception.AppleException;
import com.appleframework.jmx.database.entity.AlertContactEntity;
import com.appleframework.jmx.database.entity.AlertContactEntityExample;
import com.appleframework.jmx.database.mapper.AlertContactEntityMapper;
import com.appleframework.jmx.database.mapper.extend.AlertContactExtendMapper;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;

@Repository
public class AlertContactDao {
	
	@Resource
	private AlertContactEntityMapper alertContactEntityMapper;
	
	@Resource
	private AlertContactExtendMapper alertContactExtendMapper;
		
	public List<AlertContactEntity> findByPage(Pagination page, Search search) {
		return alertContactExtendMapper.selectByPage(page, search);
	}
		
	public AlertContactEntity get(Integer id) {
		return alertContactEntityMapper.selectByPrimaryKey(id);
	}
	
	public void update(AlertContactEntity entity) {
		entity.setUpdateTime(new Date());
		alertContactEntityMapper.updateByPrimaryKey(entity);
	}
	
	public void insert(AlertContactEntity entity) {
		Date now = new Date();
		entity.setCreateTime(now);
		entity.setUpdateTime(now);
		alertContactEntityMapper.insert(entity);
	}
	
	public int countByName(String name) {
		AlertContactEntityExample example = new AlertContactEntityExample();
		example.createCriteria().andNameEqualTo(name);
		return alertContactEntityMapper.countByExample(example);
	}
	
	public void delete(Integer id) throws AppleException {
		alertContactEntityMapper.deleteByPrimaryKey(id);
	}
	
	public List<AlertContactEntity> findAll() {
		AlertContactEntityExample example = new AlertContactEntityExample();
		example.createCriteria();
		return alertContactEntityMapper.selectByExample(example);
	}
}
