package com.appleframework.jmx.database.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.constant.StateType;
import com.appleframework.jmx.database.dao.AppInfoDao;
import com.appleframework.jmx.database.entity.AppInfoEntity;
import com.appleframework.jmx.database.service.AppInfoService;

@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService {

	@Resource
	private AppInfoDao appInfoDao;

	public AppInfoEntity get(Integer id) {
		return appInfoDao.get(id);
	}

	public void update(AppInfoEntity appInfo) {
		appInfoDao.update(appInfo);
	}

	public void updateLogLevel(Integer id, String logLevel) {
		AppInfoEntity info = this.get(id);
		info.setLogLevel(logLevel);
		appInfoDao.update(info);
	}

	public void insert(AppInfoEntity appInfo) {
		appInfoDao.insert(appInfo);
	}

	public boolean isExistByNodeAndCluster(Integer nodeId, Integer clusterId) {
		if (this.countByNodeAndCluster(nodeId, clusterId) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int countByNodeAndCluster(Integer nodeId, Integer clusterId) {
		return appInfoDao.countByNodeAndCluster(nodeId, clusterId);
	}

	public AppInfoEntity getByNodeAndCluster(Integer nodeId, Integer clusterId) {
		return appInfoDao.getByNodeAndCluster(nodeId, clusterId);
	}

	public AppInfoEntity saveOrUpdate(AppInfoEntity appInfo) {
		AppInfoEntity existAppInfo = appInfoDao.getByNodeAndCluster(appInfo.getNodeId(), appInfo.getClusterId());
		if (null == existAppInfo) {
			appInfoDao.insert(appInfo);
			return appInfo;
		} else {
			String[] ignoreProperties = { "id", "createTime", "disorder", "remark", "state" };
			BeanUtils.copyProperties(appInfo, existAppInfo, ignoreProperties);
			appInfoDao.update(existAppInfo);
			return existAppInfo;
		}
	}

	public List<AppInfoEntity> findAll() {
		return appInfoDao.findAll();
	}

	public List<AppInfoEntity> findListByClusterId(Integer clusterId) {
		return appInfoDao.findListByClusterId(clusterId);
	}

	public int countByClusterId(Integer clusterId) {
		return appInfoDao.countByClusterId(clusterId);
	}

	public Integer delete(Integer id) {
		AppInfoEntity entity = this.get(id);
		entity.setState(StateType.DELETE.getIndex());
		appInfoDao.update(entity);
		return id;
	}

}