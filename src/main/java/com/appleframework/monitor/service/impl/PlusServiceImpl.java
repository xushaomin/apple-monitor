package com.appleframework.monitor.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.entity.ThirdPlusEntity;
import com.appleframework.jmx.database.service.ThirdPlusService;
import com.appleframework.monitor.plus.ThirdPlus;
import com.appleframework.monitor.service.PlusService;

/**
 * @author xusm
 * 
 */
@Service("plusService")
public class PlusServiceImpl implements PlusService {
	
	private static final Log logger = LogFactory.getLog(PlusServiceImpl.class);
	
	@Resource
	private ThirdPlusService thirdPlusService;
	
	@Cacheable(value = "plusCache", key = "'PlusService.generateByPlusId.' + #id")
	public ThirdPlus generateByPlusId(Integer id) {
		ThirdPlusEntity entity = thirdPlusService.get(id);
		if(null == entity)
			return null;
		return this.generate(entity);
	}
	
	@Cacheable(value = "plusCache", key = "'PlusService.generateByPlusType.' + #type")
	public ThirdPlus generateByPlusType(Integer type) {
		ThirdPlusEntity entity = thirdPlusService.getByType(type);
		if(null == entity)
			return null;
		return this.generate(entity);
	}
	
	private ThirdPlus generate(ThirdPlusEntity entity) {
		try {
			Class<?> clazz = Class.forName(entity.getThirdClass());
			ThirdPlus plus = (ThirdPlus) clazz.newInstance();
			plus.setThirdKey(entity.getThirdKey());
			plus.setThirdSecret(entity.getThirdSecret());
			plus.setThirdExtend(entity.getThirdExtend());
			return plus;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
}
