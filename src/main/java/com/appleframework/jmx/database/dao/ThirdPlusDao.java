package com.appleframework.jmx.database.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appleframework.jmx.database.entity.ThirdPlusEntity;
import com.appleframework.jmx.database.entity.ThirdPlusEntityExample;
import com.appleframework.jmx.database.mapper.ThirdPlusEntityMapper;

@Repository
public class ThirdPlusDao {

	@Resource
	private ThirdPlusEntityMapper thirdPlusEntityMapper;

	public ThirdPlusEntity get(Integer id) {
		return thirdPlusEntityMapper.selectByPrimaryKey(id);
	}

	public void update(ThirdPlusEntity thirdPlus) {
		thirdPlus.setUpdateTime(new Date());
		thirdPlusEntityMapper.updateByPrimaryKey(thirdPlus);
	}

	public void insert(ThirdPlusEntity thirdPlus) {
		Date now = new Date();
		thirdPlus.setCreateTime(now);
		thirdPlus.setUpdateTime(now);
		thirdPlusEntityMapper.insert(thirdPlus);
	}

	public ThirdPlusEntity getByType(Integer type) {
		ThirdPlusEntityExample example = new ThirdPlusEntityExample();
		example.createCriteria().andTypeEqualTo(type);
		List<ThirdPlusEntity> list = thirdPlusEntityMapper.selectByExample(example);
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

}