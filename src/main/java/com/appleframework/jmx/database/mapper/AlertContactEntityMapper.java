package com.appleframework.jmx.database.mapper;

import com.appleframework.jmx.database.entity.AlertContactEntity;
import com.appleframework.jmx.database.entity.AlertContactEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertContactEntityMapper {
	
    int countByExample(AlertContactEntityExample example);

    int deleteByExample(AlertContactEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AlertContactEntity record);

    int insertSelective(AlertContactEntity record);

    List<AlertContactEntity> selectByExample(AlertContactEntityExample example);

    AlertContactEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AlertContactEntity record, @Param("example") AlertContactEntityExample example);

    int updateByExample(@Param("record") AlertContactEntity record, @Param("example") AlertContactEntityExample example);

    int updateByPrimaryKeySelective(AlertContactEntity record);

    int updateByPrimaryKey(AlertContactEntity record);
}