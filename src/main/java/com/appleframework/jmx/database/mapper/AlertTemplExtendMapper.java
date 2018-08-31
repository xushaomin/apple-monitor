package com.appleframework.jmx.database.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.appleframework.jmx.database.entity.AlertTemplEntity;

@Repository
public interface AlertTemplExtendMapper {

	AlertTemplEntity selectByTypeAndCode(@Param("type") Integer type, @Param("code") String code);

}