package com.appleframework.monitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.mapper.extend.AppInfoExtendMapper;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.monitor.model.AppInfoBo;
import com.appleframework.monitor.model.AppInfoSo;
import com.appleframework.monitor.service.AppInfoSearchService;

@Service("appInfoSearchService")
public class AppInfoSearchServiceImpl implements AppInfoSearchService {

	@Resource
	private AppInfoExtendMapper appInfoExtendMapper;
	
	public Pagination findPage(Pagination page, Search search, AppInfoSo so) {
		List<AppInfoBo> list = appInfoExtendMapper.selectByPage(page, search, so);
		page.setList(list);
		return page;
	}
}
