package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.dao.AppConfigDao;
import com.appleframework.jmx.database.entity.AppConfigEntity;
import com.appleframework.jmx.database.service.AppConfigService;

@Service("appConfigService")
public class AppConfigServiceImpl implements AppConfigService {

	@Resource
	private AppConfigDao appConfigDao;

	@Override
	public void updateIsAlert(Integer id, boolean isAlert) {
		AppConfigEntity entity = this.get(id);
		entity.setIsAlert(isAlert);
		appConfigDao.update(entity);
	}

	public AppConfigEntity get(Integer id) {
		return appConfigDao.get(id);
	}

	public void update(AppConfigEntity appConfig) {
		appConfigDao.update(appConfig);
	}

	public void insert(AppConfigEntity appConfig) {
		Date now = new Date();
		appConfig.setCreateTime(now);
		appConfig.setUpdateTime(now);
		appConfig.setIsAlert(false);
		appConfigDao.insert(appConfig);
	}

	public AppConfigEntity saveOrUpdate(AppConfigEntity appConfig) {
		AppConfigEntity existAppConfig = this.get(appConfig.getId());
		if (null == existAppConfig) {
			this.insert(appConfig);
			return appConfig;
		} else {
			String[] ignoreProperties = { "id" };
			BeanUtils.copyProperties(appConfig, existAppConfig, ignoreProperties);
			this.update(existAppConfig);
			return appConfig;
		}
	}

	public List<AppConfigEntity> findAll() {
		return appConfigDao.findAll();
	}

	public List<AppConfigEntity> findListByClusterId(Integer clusterId) {
		return appConfigDao.findListByClusterId(clusterId);
	}

	public List<AppConfigEntity> findListByClusterIdAndStart(Integer clusterId) {
		return appConfigDao.findListByClusterIdAndStart(clusterId);
	}

	public List<AppConfigEntity> findListByIsAlert() {
		return appConfigDao.findListByIsAlert();
	}

	public boolean isExistByAlertGroupId(Integer alertGroupId) {
		if (countByAlertGroupId(alertGroupId) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int countByAlertGroupId(Integer alertGroupId) {
		return appConfigDao.countByAlertGroupId(alertGroupId);
	}

}