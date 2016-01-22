package com.appleframework.monitor.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	private static Map<String, ThirdPlus> plusMap = new HashMap<String, ThirdPlus>();
	
	@Resource
	private ThirdPlusService thirdPlusService;
	
	private static final String KEY_PREFIX_ID   = "ID_";
	private static final String KEY_PREFIX_TYPE = "TYPE_";
	
	public ThirdPlus generateByPlusId(Integer id) {
		ThirdPlus plus = null;
		String key = KEY_PREFIX_ID + id;
		try {
			if (null == plusMap.get(key)) {
				ThirdPlusEntity entity = thirdPlusService.get(id);
				plus = this.generate(entity);
				plusMap.put(key, plus);
			} else {
				plus = plusMap.get(key);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return plus;
	}
	
	public ThirdPlus generateByPlusType(Integer type) {
		ThirdPlus plus = null;
		String key = KEY_PREFIX_TYPE + type;
		try {
			if (null == plusMap.get(key)) {
				ThirdPlusEntity entity = thirdPlusService.getByType(type);
				plus = this.generate(entity);
				plusMap.put(key, plus);
			} else {
				plus = plusMap.get(key);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return plus;
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
			return null;
		}
	}
	
}
