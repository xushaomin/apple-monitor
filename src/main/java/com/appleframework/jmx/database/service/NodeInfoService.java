package com.appleframework.jmx.database.service;

import java.util.List;

import com.appleframework.exception.ServiceException;
import com.appleframework.jmx.database.entity.NodeInfoEntity;

public interface NodeInfoService {
	
	public NodeInfoEntity get(Integer id);
	
	public void update(NodeInfoEntity nodeInfo) throws ServiceException;
	
	public void save(NodeInfoEntity nodeInfo) throws ServiceException;
	
	public NodeInfoEntity getByHost(String host);
			
	public boolean isUniqueByHost(String oldHost, String newHost);
	
	public boolean isExistByHost(String host);
	
	public NodeInfoEntity saveWithHost(String host, String ip);
	
	public List<NodeInfoEntity> findAll();
	
}