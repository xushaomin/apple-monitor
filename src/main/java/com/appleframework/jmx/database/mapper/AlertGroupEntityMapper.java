package com.appleframework.jmx.database.mapper;

import com.appleframework.jmx.database.entity.AlertGroupEntity;
import com.appleframework.jmx.database.entity.AlertGroupEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertGroupEntityMapper {
	
    int countByExample(AlertGroupEntityExample example);

    int deleteByExample(AlertGroupEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AlertGroupEntity record);

    int insertSelective(AlertGroupEntity record);

    List<AlertGroupEntity> selectByExample(AlertGroupEntityExample example);

    AlertGroupEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AlertGroupEntity record, @Param("example") AlertGroupEntityExample example);

    int updateByExample(@Param("record") AlertGroupEntity record, @Param("example") AlertGroupEntityExample example);

    int updateByPrimaryKeySelective(AlertGroupEntity record);

    int updateByPrimaryKey(AlertGroupEntity record);
}