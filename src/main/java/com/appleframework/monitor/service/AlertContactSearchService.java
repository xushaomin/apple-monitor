package com.appleframework.monitor.service;

import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;

public interface AlertContactSearchService {

	public Pagination findPage(Pagination page, Search search);
}
