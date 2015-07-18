package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.entity.AppDowntimeHistoryEntity;
import com.appleframework.jmx.database.entity.AppDowntimeHistoryEntityExample;
import com.appleframework.jmx.database.mapper.AppDowntimeHistoryEntityMapper;
import com.appleframework.jmx.database.service.AppDowntimeHistoryService;

@Service("appDowntimeHistoryService")
public class AppDowntimeHistoryServiceImpl implements AppDowntimeHistoryService {

	@Resource
	private AppDowntimeHistoryEntityMapper appDowntimeHistoryEntityMapper;

	public AppDowntimeHistoryEntity get(Integer id) {
		return appDowntimeHistoryEntityMapper.selectByPrimaryKey(id);
	}
	
	public void update(AppDowntimeHistoryEntity historyEntity) {
		historyEntity.setUpdateTime(new Date());
		appDowntimeHistoryEntityMapper.updateByPrimaryKey(historyEntity);
	}
	
	public void insert(AppDowntimeHistoryEntity historyEntity) {
		historyEntity.setCreateTime(new Date());
		appDowntimeHistoryEntityMapper.insert(historyEntity);
	}
	
	/*public AppDowntimeHistory saveOrUpdate(AppDowntimeHistory appInfo) {
		AppDowntimeHistory existAppDowntimeHistory = this.getByNodeAndGroup(appInfo.getNodeId(), appInfo.getGroupId());
		if(null == existAppDowntimeHistory) {
			this.insert(appInfo);
			return appInfo;
		}
		else {
			String[] ignoreProperties = {"id", "createTime", "disorder", "remark", "state"};
			BeanUtils.copyProperties(appInfo, existAppDowntimeHistory, ignoreProperties);
			this.update(existAppDowntimeHistory);
			return appInfo;
		}
	}*/
	
	public List<AppDowntimeHistoryEntity> findAll() {
		AppDowntimeHistoryEntityExample example = new AppDowntimeHistoryEntityExample();
		example.createCriteria();
		return appDowntimeHistoryEntityMapper.selectByExample(example);
	}

}