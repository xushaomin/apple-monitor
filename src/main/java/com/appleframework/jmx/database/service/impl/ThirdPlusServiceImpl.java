package com.appleframework.jmx.database.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.dao.ThirdPlusDao;
import com.appleframework.jmx.database.entity.ThirdPlusEntity;
import com.appleframework.jmx.database.service.ThirdPlusService;

@Service("thirdPlusService")
public class ThirdPlusServiceImpl implements ThirdPlusService {

	@Resource
	private ThirdPlusDao thirdPlusDao;
	
	public ThirdPlusEntity get(Integer id) {
		return thirdPlusDao.get(id);
	}
	
	public void update(ThirdPlusEntity thirdPlus) {
		thirdPlusDao.update(thirdPlus);
	}
	
	public void insert(ThirdPlusEntity thirdPlus) {
		thirdPlusDao.insert(thirdPlus);
	}
	
	public ThirdPlusEntity getByType(Integer type) {
		return thirdPlusDao.getByType(type);
	}

}