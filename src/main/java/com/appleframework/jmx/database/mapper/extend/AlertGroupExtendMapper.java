package com.appleframework.jmx.database.mapper.extend;

import java.util.List;

import com.appleframework.jmx.database.entity.AlertGroupEntity;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertGroupExtendMapper {

    List<AlertGroupEntity> selectByPage(@Param("page")Pagination page, @Param("se")Search search);

}