package com.appleframework.jmx.database.mapper;

import com.appleframework.jmx.database.entity.AppClusterEntity;
import com.appleframework.jmx.database.entity.AppClusterEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppClusterEntityMapper {
	
    int countByExample(AppClusterEntityExample example);

    int deleteByExample(AppClusterEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppClusterEntity record);

    int insertSelective(AppClusterEntity record);

    List<AppClusterEntity> selectByExample(AppClusterEntityExample example);

    AppClusterEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppClusterEntity record, @Param("example") AppClusterEntityExample example);

    int updateByExample(@Param("record") AppClusterEntity record, @Param("example") AppClusterEntityExample example);

    int updateByPrimaryKeySelective(AppClusterEntity record);

    int updateByPrimaryKey(AppClusterEntity record);
}