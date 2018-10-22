package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.dao.AlertGroupContactDao;
import com.appleframework.jmx.database.entity.AlertContactEntity;
import com.appleframework.jmx.database.entity.AlertGroupContactEntity;
import com.appleframework.jmx.database.service.AlertGroupContactService;

@Service("alertGroupContactService")
public class AlertGroupContactServiceImpl implements AlertGroupContactService {

	@Resource
	private AlertGroupContactDao alertGroupContactDao;

	@Cacheable(value = "alertGroupContactCache", key = "'findAlertContactListByGroupId.' + #alertGroupId")
	public List<AlertContactEntity> findAlertContactListByGroupId(Integer alertGroupId) {
		return alertGroupContactDao.findAlertContactListByGroupId(alertGroupId);
	}

	public List<AlertGroupContactEntity> findListByGroupId(Integer groupId) {
		return alertGroupContactDao.findListByGroupId(groupId);
	}

	public AlertGroupContactEntity get(Integer id) {
		return alertGroupContactDao.get(id);
	}

	public void update(AlertGroupContactEntity entity) {
		alertGroupContactDao.update(entity);
	}

	public void delete(Integer id) {
		alertGroupContactDao.delete(id);
	}

	public void insert(AlertGroupContactEntity entity) {
		Date now = new Date();
		entity.setCreateTime(now);
		alertGroupContactDao.insert(entity);
	}

	public boolean isExistByContactId(Integer contactId) {
		if (countByContactId(contactId) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int countByContactId(Integer contactId) {
		return alertGroupContactDao.countByContactId(contactId);
	}

}