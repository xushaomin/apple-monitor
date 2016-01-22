package com.appleframework.jmx.database.service;

import com.appleframework.jmx.database.entity.ThirdPlusEntity;

public interface ThirdPlusService {
	
	public ThirdPlusEntity getByType(Integer type);
	
	public ThirdPlusEntity get(Integer id);
	
	public void update(ThirdPlusEntity thirdPlus);
	
	public void insert(ThirdPlusEntity thirdPlus);
	
}