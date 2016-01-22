package com.appleframework.jmx.database.mapper.extend;

import java.util.List;

import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.monitor.model.AppInfoBo;
import com.appleframework.monitor.model.AppInfoSo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppInfoExtendMapper {

    List<AppInfoBo> selectByPage(@Param("page")Pagination page, @Param("se")Search search, @Param("so")AppInfoSo so);

}