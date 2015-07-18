package com.appleframework.jmx.database.mapper;

import com.appleframework.jmx.database.entity.AppDowntimeHistoryEntity;
import com.appleframework.jmx.database.entity.AppDowntimeHistoryEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppDowntimeHistoryEntityMapper {
	
    int countByExample(AppDowntimeHistoryEntityExample example);

    int deleteByExample(AppDowntimeHistoryEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppDowntimeHistoryEntity record);

    int insertSelective(AppDowntimeHistoryEntity record);

    List<AppDowntimeHistoryEntity> selectByExample(AppDowntimeHistoryEntityExample example);

    AppDowntimeHistoryEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppDowntimeHistoryEntity record, @Param("example") AppDowntimeHistoryEntityExample example);

    int updateByExample(@Param("record") AppDowntimeHistoryEntity record, @Param("example") AppDowntimeHistoryEntityExample example);

    int updateByPrimaryKeySelective(AppDowntimeHistoryEntity record);

    int updateByPrimaryKey(AppDowntimeHistoryEntity record);
}