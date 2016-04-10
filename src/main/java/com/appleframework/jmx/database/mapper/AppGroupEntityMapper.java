package com.appleframework.jmx.database.mapper;

import com.appleframework.jmx.database.entity.AppGroupEntity;
import com.appleframework.jmx.database.entity.AppGroupEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppGroupEntityMapper {
	
    int countByExample(AppGroupEntityExample example);

    int deleteByExample(AppGroupEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppGroupEntity record);

    int insertSelective(AppGroupEntity record);

    List<AppGroupEntity> selectByExample(AppGroupEntityExample example);

    AppGroupEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppGroupEntity record, @Param("example") AppGroupEntityExample example);

    int updateByExample(@Param("record") AppGroupEntity record, @Param("example") AppGroupEntityExample example);

    int updateByPrimaryKeySelective(AppGroupEntity record);

    int updateByPrimaryKey(AppGroupEntity record);
}