package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.entity.NodeInfoEntity;
import com.appleframework.jmx.database.entity.NodeInfoEntityExample;
import com.appleframework.jmx.database.mapper.NodeInfoEntityMapper;
import com.appleframework.jmx.database.service.NodeInfoService;


@Service("nodeInfoService")
public class NodeInfoServiceImpl implements NodeInfoService {

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
	
	public boolean isExistByHost(String host) {
	    if(this.countByHost(host) > 0) {
	    	return true;
	    } else {
			return false;
		}
	}
	
	public int countByHost(String host) {
		NodeInfoEntityExample example = new NodeInfoEntityExample();
		example.createCriteria().andHostEqualTo(host);
		return nodeInfoEntityMapper.countByExample(example);
	}
	
	public NodeInfoEntity getByHost(String host) {
		NodeInfoEntityExample example = new NodeInfoEntityExample();
		example.createCriteria().andHostEqualTo(host);
		List<NodeInfoEntity> list = nodeInfoEntityMapper.selectByExample(example);
		if(list.size() > 0) {
			return list.get(0);
		}
		else {
			return null;
		}
	}
	
	public boolean isUniqueByHost(String oldHost, String newHost) {
		if (StringUtils.equalsIgnoreCase(oldHost, newHost)) {
			return true;
		} else {
			if (this.isExistByHost(newHost)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	public NodeInfoEntity saveWithHost(String host, String ip) {
		NodeInfoEntity nodeInfo = this.getByHost(host);
		if(null == nodeInfo) {
			nodeInfo = new NodeInfoEntity();
			nodeInfo.setHost(host);
			nodeInfo.setCreateTime(new Date());
			nodeInfo.setDisorder(1);
			nodeInfo.setHost(host);
			nodeInfo.setIp(ip);
			nodeInfo.setRemark("");
			nodeInfo.setState((short)1);
			this.save(nodeInfo);
			return nodeInfo;
		}
		else {
			return nodeInfo;
		}
	}
	
	/*public Pagination findPage(Pagination page, String keyword) {
		PageQuery query = PageQuery.create(page);
		query.addParameters("keyword", keyword);
		List<AppInfo> list = appInfoDao.findPage(query);
		page.setList(list);
		return page;
	}*/
	
	public List<NodeInfoEntity> findAll() {
		NodeInfoEntityExample example = new NodeInfoEntityExample();
		example.createCriteria();
		return nodeInfoEntityMapper.selectByExample(example);
	}

}