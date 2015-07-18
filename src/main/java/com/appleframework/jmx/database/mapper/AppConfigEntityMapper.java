package com.appleframework.jmx.database.mapper;

import com.appleframework.jmx.database.entity.AppConfigEntity;
import com.appleframework.jmx.database.entity.AppConfigEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppConfigEntityMapper {
	
    int countByExample(AppConfigEntityExample example);

    int deleteByExample(AppConfigEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppConfigEntity record);

    int insertSelective(AppConfigEntity record);

    List<AppConfigEntity> selectByExample(AppConfigEntityExample example);

    AppConfigEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppConfigEntity record, @Param("example") AppConfigEntityExample example);

    int updateByExample(@Param("record") AppConfigEntity record, @Param("example") AppConfigEntityExample example);

    int updateByPrimaryKeySelective(AppConfigEntity record);

    int updateByPrimaryKey(AppConfigEntity record);
}