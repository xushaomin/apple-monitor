package com.appleframework.jmx.database.mapper.extend;

import java.util.List;

import com.appleframework.jmx.database.entity.AppClusterEntity;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppClusterExtendMapper {

    List<AppClusterEntity> selectByPage(@Param("page")Pagination page, @Param("se")Search search);

}