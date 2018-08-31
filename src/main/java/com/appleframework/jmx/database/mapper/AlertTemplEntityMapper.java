package com.appleframework.jmx.database.mapper;

import org.springframework.stereotype.Repository;

import com.appleframework.jmx.database.entity.AlertTemplEntity;

@Repository
public interface AlertTemplEntityMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(AlertTemplEntity record);

    int insertSelective(AlertTemplEntity record);

    AlertTemplEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AlertTemplEntity record);

    int updateByPrimaryKey(AlertTemplEntity record);
}