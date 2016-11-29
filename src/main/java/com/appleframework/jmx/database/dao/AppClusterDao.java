package com.appleframework.jmx.database.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appleframework.jmx.database.constant.StateType;
import com.appleframework.jmx.database.entity.AppClusterEntity;
import com.appleframework.jmx.database.entity.AppClusterEntityExample;
import com.appleframework.jmx.database.mapper.AppClusterEntityMapper;
import com.appleframework.jmx.database.mapper.extend.AppClusterExtendMapper;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;

@Repository
public class AppClusterDao {

	@Resource
	private AppClusterEntityMapper appClusterEntityMapper;
	
	@Resource
	private AppClusterExtendMapper appClusterExtendMapper;
	
	public List<AppClusterEntity> findByPage(Pagination page, Search search) {
		return appClusterExtendMapper.selectByPage(page, search);
	}
	
	public AppClusterEntity get(Integer id) {
		return appClusterEntityMapper.selectByPrimaryKey(id);
	}
	
	public void update(AppClusterEntity appCluster) {
		appClusterEntityMapper.updateByPrimaryKey(appCluster);
	}
	
	public void save(AppClusterEntity appCluster) {
		Date now = new Date();
		appCluster.setCreateTime(now);
		appCluster.setUpdateTime(now);
		appClusterEntityMapper.insert(appCluster);
	}
	
	public int countByName(String name) {
		AppClusterEntityExample example = new AppClusterEntityExample();
		example.createCriteria().andClusterNameEqualTo(name);
		return appClusterEntityMapper.countByExample(example);
	}
	
	public AppClusterEntity getByName(String name) {
		AppClusterEntityExample example = new AppClusterEntityExample();
		example.createCriteria().andClusterNameEqualTo(name);
		List<AppClusterEntity> list = appClusterEntityMapper.selectByExample(example);
		if(list.size() > 0) {
			return list.get(0);
		}
		else {
			return null;
		}
	}
	
	public List<AppClusterEntity> findAll() {
		AppClusterEntityExample example = new AppClusterEntityExample();
		example.createCriteria();
		example.setOrderByClause("cluster_name");
		example.setDistinct(true);
		return appClusterEntityMapper.selectByExample(example);
	}
	
	public List<AppClusterEntity> findListByStart() {
		AppClusterEntityExample example = new AppClusterEntityExample();
		example.createCriteria().andStateEqualTo(StateType.START.getIndex());
		example.setOrderByClause("cluster_name");
		example.setDistinct(true);
		return appClusterEntityMapper.selectByExample(example);
	}

}