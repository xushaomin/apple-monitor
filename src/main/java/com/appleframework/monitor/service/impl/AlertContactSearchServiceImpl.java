package com.appleframework.monitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.entity.AlertContactEntity;
import com.appleframework.jmx.database.mapper.extend.AlertContactExtendMapper;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.monitor.service.AlertContactSearchService;

@Service("alertContactSearchService")
public class AlertContactSearchServiceImpl implements AlertContactSearchService {

	@Resource
	private AlertContactExtendMapper alertContactExtendMapper;
	
	public Pagination findPage(Pagination page, Search search) {
		List<AlertContactEntity> list = alertContactExtendMapper.selectByPage(page, search);
		page.setList(list);
		return page;
	}
}
