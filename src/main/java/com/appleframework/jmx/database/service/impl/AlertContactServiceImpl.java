package com.appleframework.jmx.database.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.entity.AlertContactEntity;
import com.appleframework.jmx.database.mapper.extend.AlertGroupContactExtendMapper;
import com.appleframework.jmx.database.service.AlertContactService;

@Service("alertContactService")
public class AlertContactServiceImpl implements AlertContactService {

	@Resource
	private AlertGroupContactExtendMapper alertGroupContactExtendMapper;
	
	public List<AlertContactEntity> findListByGroupId(Integer alertGroupId) {
		return alertGroupContactExtendMapper.selectAlertContactByAlertGroupId(alertGroupId);
	}
	
}