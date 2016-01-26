package com.appleframework.monitor.service;

import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;

public interface AlertGroupSearchService {

	public Pagination findPage(Pagination page, Search search);
}
