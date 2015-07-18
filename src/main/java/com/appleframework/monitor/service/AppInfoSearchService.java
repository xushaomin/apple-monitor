package com.appleframework.monitor.service;

import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.monitor.model.AppInfoSo;

public interface AppInfoSearchService {

	public Pagination findPage(Pagination page, Search search, AppInfoSo so);
}
