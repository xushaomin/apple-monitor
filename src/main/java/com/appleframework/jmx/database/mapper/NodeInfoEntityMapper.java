package com.appleframework.jmx.database.mapper;

import com.appleframework.jmx.database.entity.NodeInfoEntity;
import com.appleframework.jmx.database.entity.NodeInfoEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeInfoEntityMapper {
	
    int countByExample(NodeInfoEntityExample example);

    int deleteByExample(NodeInfoEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NodeInfoEntity record);

    int insertSelective(NodeInfoEntity record);

    List<NodeInfoEntity> selectByExample(NodeInfoEntityExample example);

    NodeInfoEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NodeInfoEntity record, @Param("example") NodeInfoEntityExample example);

    int updateByExample(@Param("record") NodeInfoEntity record, @Param("example") NodeInfoEntityExample example);

    int updateByPrimaryKeySelective(NodeInfoEntity record);

    int updateByPrimaryKey(NodeInfoEntity record);
}