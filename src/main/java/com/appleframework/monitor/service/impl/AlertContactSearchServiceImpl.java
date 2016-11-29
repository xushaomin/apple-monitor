package com.appleframework.monitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.dao.AlertContactDao;
import com.appleframework.jmx.database.entity.AlertContactEntity;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.monitor.service.AlertContactSearchService;

@Service("alertContactSearchService")
public class AlertContactSearchServiceImpl implements AlertContactSearchService {

	@Resource
	private AlertContactDao alertContactDao;
	
	public Pagination findPage(Pagination page, Search search) {
		List<AlertContactEntity> list = alertContactDao.findByPage(page, search);
		page.setList(list);
		return page;
	}
}
