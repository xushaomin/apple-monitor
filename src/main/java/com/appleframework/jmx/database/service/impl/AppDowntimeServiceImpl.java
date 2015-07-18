package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.entity.AppDowntimeEntity;
import com.appleframework.jmx.database.entity.AppDowntimeEntityExample;
import com.appleframework.jmx.database.mapper.AppDowntimeEntityMapper;
import com.appleframework.jmx.database.service.AppDowntimeService;



@Service("appDowntimeService")
public class AppDowntimeServiceImpl implements AppDowntimeService {

	@Resource
	private AppDowntimeEntityMapper appDowntimeEntityMapper;
	

	public AppDowntimeEntity get(Integer id) {
		return appDowntimeEntityMapper.selectByPrimaryKey(id);
	}
	
	public void update(AppDowntimeEntity appDowntime) {
		appDowntime.setUpdateTime(new Date());
		appDowntimeEntityMapper.updateByPrimaryKey(appDowntime);
	}
	
	public void insert(AppDowntimeEntity appDowntime) {
		appDowntime.setCreateTime(new Date());
		appDowntimeEntityMapper.insert(appDowntime);
	}
	
	
	/*public AppDowntime saveOrUpdate(AppDowntime appInfo) {
		AppDowntime existAppDowntime = this.getByNodeAndGroup(appInfo.getNodeId(), appInfo.getGroupId());
		if(null == existAppDowntime) {
			this.insert(appInfo);
			return appInfo;
		}
		else {
			String[] ignoreProperties = {"id", "createTime", "disorder", "remark", "state"};
			BeanUtils.copyProperties(appInfo, existAppDowntime, ignoreProperties);
			this.update(existAppDowntime);
			return appInfo;
		}
	}*/
	
	public List<AppDowntimeEntity> findAll() {
		AppDowntimeEntityExample example = new AppDowntimeEntityExample();
		example.createCriteria();
		return appDowntimeEntityMapper.selectByExample(example);
	}

}