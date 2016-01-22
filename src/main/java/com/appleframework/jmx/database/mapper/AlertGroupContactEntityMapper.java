package com.appleframework.jmx.database.mapper;

import com.appleframework.jmx.database.entity.AlertGroupContactEntity;
import com.appleframework.jmx.database.entity.AlertGroupContactEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertGroupContactEntityMapper {
	
    int countByExample(AlertGroupContactEntityExample example);

    int deleteByExample(AlertGroupContactEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AlertGroupContactEntity record);

    int insertSelective(AlertGroupContactEntity record);

    List<AlertGroupContactEntity> selectByExample(AlertGroupContactEntityExample example);

    AlertGroupContactEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AlertGroupContactEntity record, @Param("example") AlertGroupContactEntityExample example);

    int updateByExample(@Param("record") AlertGroupContactEntity record, @Param("example") AlertGroupContactEntityExample example);

    int updateByPrimaryKeySelective(AlertGroupContactEntity record);

    int updateByPrimaryKey(AlertGroupContactEntity record);
}