package com.appleframework.jmx.database.mapper;

import com.appleframework.jmx.database.entity.AppInfoEntity;
import com.appleframework.jmx.database.entity.AppInfoEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppInfoEntityMapper {
	
    int countByExample(AppInfoEntityExample example);

    int deleteByExample(AppInfoEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppInfoEntity record);

    int insertSelective(AppInfoEntity record);

    List<AppInfoEntity> selectByExample(AppInfoEntityExample example);

    AppInfoEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppInfoEntity record, @Param("example") AppInfoEntityExample example);

    int updateByExample(@Param("record") AppInfoEntity record, @Param("example") AppInfoEntityExample example);

    int updateByPrimaryKeySelective(AppInfoEntity record);

    int updateByPrimaryKey(AppInfoEntity record);
}