package com.appleframework.jmx.database.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.entity.AlertGroupEntity;
import com.appleframework.jmx.database.entity.AlertGroupEntityExample;
import com.appleframework.jmx.database.mapper.AlertGroupEntityMapper;
import com.appleframework.jmx.database.service.AlertGroupService;

@Service("alertGroupService")
public class AlertGroupServiceImpl implements AlertGroupService {

	@Resource
	private AlertGroupEntityMapper alertGroupEntityMapper;
	
	public List<AlertGroupEntity> findAll() {
		AlertGroupEntityExample example = new AlertGroupEntityExample();
		example.createCriteria();
		return alertGroupEntityMapper.selectByExample(example);
	}
	
}