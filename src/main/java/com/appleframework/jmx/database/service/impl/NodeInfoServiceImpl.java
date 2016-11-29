package com.appleframework.jmx.database.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.appleframework.jmx.database.constant.StateType;
import com.appleframework.jmx.database.dao.NodeInfoDao;
import com.appleframework.jmx.database.entity.NodeInfoEntity;
import com.appleframework.jmx.database.service.NodeInfoService;


@Service("nodeInfoService")
public class NodeInfoServiceImpl implements NodeInfoService {

	@Resource
	private NodeInfoDao nodeInfoDao;

	public NodeInfoEntity get(Integer id) {
		return nodeInfoDao.get(id);
	}
	
	public void update(NodeInfoEntity nodeInfo) {
		nodeInfoDao.update(nodeInfo);
	}
	
	public void save(NodeInfoEntity nodeInfo) {
		nodeInfoDao.save(nodeInfo);
	}
	
	public boolean isExistByHost(String host) {
	    if(this.countByHost(host) > 0) {
	    	return true;
	    } else {
			return false;
		}
	}
	
	public int countByHost(String host) {
		return nodeInfoDao.countByHost(host);
	}
	
	public NodeInfoEntity getByHost(String host) {
		return nodeInfoDao.getByHost(host);
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
			nodeInfo.setIp(ip);
			nodeInfo.setRemark("");
			nodeInfo.setState(StateType.START.getIndex());
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
		return nodeInfoDao.findAll();
	}

}