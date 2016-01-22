package com.appleframework.monitor.service;

import com.appleframework.monitor.plus.ThirdPlus;

public interface PlusService {

	public ThirdPlus generateByPlusId(Integer id);
	
	public ThirdPlus generateByPlusType(Integer type);
	
}
