package com.appleframework.jmx.database.mapper;

import com.appleframework.jmx.database.entity.ThirdPlusEntity;
import com.appleframework.jmx.database.entity.ThirdPlusEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPlusEntityMapper {
	
    int countByExample(ThirdPlusEntityExample example);

    int deleteByExample(ThirdPlusEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ThirdPlusEntity record);

    int insertSelective(ThirdPlusEntity record);

    List<ThirdPlusEntity> selectByExample(ThirdPlusEntityExample example);

    ThirdPlusEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ThirdPlusEntity record, @Param("example") ThirdPlusEntityExample example);

    int updateByExample(@Param("record") ThirdPlusEntity record, @Param("example") ThirdPlusEntityExample example);

    int updateByPrimaryKeySelective(ThirdPlusEntity record);

    int updateByPrimaryKey(ThirdPlusEntity record);
}