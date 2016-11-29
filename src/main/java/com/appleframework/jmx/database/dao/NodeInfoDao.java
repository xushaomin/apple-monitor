package com.appleframework.jmx.database.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.appleframework.jmx.database.entity.NodeInfoEntity;
import com.appleframework.jmx.database.entity.NodeInfoEntityExample;
import com.appleframework.jmx.database.mapper.NodeInfoEntityMapper;

@Repository
public class NodeInfoDao {

	@Resource
	private NodeInfoEntityMapper nodeInfoEntityMapper;

	public NodeInfoEntity get(Integer id) {
		return nodeInfoEntityMapper.selectByPrimaryKey(id);
	}

	public void update(NodeInfoEntity nodeInfo) {
		nodeInfoEntityMapper.updateByPrimaryKey(nodeInfo);
	}

	public void save(NodeInfoEntity nodeInfo) {
		Date now = new Date();
		nodeInfo.setCreateTime(now);
		nodeInfo.setUpdateTime(now);
		nodeInfoEntityMapper.insert(nodeInfo);
	}

	public int countByHost(String host) {
		NodeInfoEntityExample example = new NodeInfoEntityExample();
		example.createCriteria().andHostEqualTo(host);
		return nodeInfoEntityMapper.countByExample(example);
	}

	public NodeInfoEntity getByHost(String host) {
		NodeInfoEntityExample example = new NodeInfoEntityExample();
		example.createCriteria().andHostEqualTo(host).andStateEqualTo((short) 1);
		List<NodeInfoEntity> list = nodeInfoEntityMapper.selectByExample(example);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<NodeInfoEntity> findAll() {
		NodeInfoEntityExample example = new NodeInfoEntityExample();
		example.createCriteria();
		example.setOrderByClause("ip");
		example.setDistinct(true);
		return nodeInfoEntityMapper.selectByExample(example);
	}

}