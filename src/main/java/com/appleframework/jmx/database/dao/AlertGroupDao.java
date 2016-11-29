package com.appleframework.jmx.database.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appleframework.exception.AppleException;
import com.appleframework.jmx.database.entity.AlertGroupEntity;
import com.appleframework.jmx.database.entity.AlertGroupEntityExample;
import com.appleframework.jmx.database.mapper.AlertGroupEntityMapper;
import com.appleframework.jmx.database.mapper.extend.AlertGroupExtendMapper;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;

@Repository
public class AlertGroupDao {

	@Resource
	private AlertGroupEntityMapper alertGroupEntityMapper;
	
	@Resource
	private AlertGroupExtendMapper alertGroupExtendMapper;

		
	public List<AlertGroupEntity> findAll() {
		AlertGroupEntityExample example = new AlertGroupEntityExample();
		example.createCriteria();
		return alertGroupEntityMapper.selectByExample(example);
	}
	
	public AlertGroupEntity get(Integer id) {
		return alertGroupEntityMapper.selectByPrimaryKey(id);
	}
	
	public void update(AlertGroupEntity entity) {
		entity.setUpdateTime(new Date());
		alertGroupEntityMapper.updateByPrimaryKey(entity);
	}
	
	public void insert(AlertGroupEntity entity) {
		Date now = new Date();
		entity.setCreateTime(now);
		alertGroupEntityMapper.insert(entity);
	}
	
	public int countByName(String name) {
		AlertGroupEntityExample example = new AlertGroupEntityExample();
		example.createCriteria().andNameEqualTo(name);
		return alertGroupEntityMapper.countByExample(example);
	}
	
	
	public void delete(Integer id) throws AppleException {
		alertGroupEntityMapper.deleteByPrimaryKey(id);
	}
	
	public List<AlertGroupEntity> findByPage(Pagination page, Search search) {
		return alertGroupExtendMapper.selectByPage(page, search);
	}

	
}