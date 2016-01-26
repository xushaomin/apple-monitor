package com.appleframework.monitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.entity.AlertGroupEntity;
import com.appleframework.jmx.database.mapper.extend.AlertGroupExtendMapper;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.monitor.service.AlertGroupSearchService;

@Service("alertGroupSearchService")
public class AlertGroupSearchServiceImpl implements AlertGroupSearchService {

	@Resource
	private AlertGroupExtendMapper alertGroupExtendMapper;
	
	public Pagination findPage(Pagination page, Search search) {
		List<AlertGroupEntity> list = alertGroupExtendMapper.selectByPage(page, search);
		page.setList(list);
		return page;
	}
}
