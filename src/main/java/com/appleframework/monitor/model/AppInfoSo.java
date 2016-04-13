package com.appleframework.monitor.model;

import java.io.Serializable;
import java.util.Date;

public class AppInfoSo implements Serializable {
	
    private Long id;

    private Long nodeId;

    private Long clusterId;

    private String appCode;

    private Integer webPort;

    private String webContext;

    private Integer jmxPort;

    private Integer servicePort;

    private String installPath;
    
    private String appVersion;

    private String confEnv;

    private Integer disorder;

    private String remark;

    private Short state;

    private Date createTime;

    private Date updateTime;
    
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getClusterId() {
		return clusterId;
	}

	public void setClusterId(Long clusterId) {
		this.clusterId = clusterId;
	}

	public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public Integer getWebPort() {
        return webPort;
    }

    public void setWebPort(Integer webPort) {
        this.webPort = webPort;
    }

    public String getWebContext() {
        return webContext;
    }

    public void setWebContext(String webContext) {
        this.webContext = webContext == null ? null : webContext.trim();
    }

    public Integer getJmxPort() {
        return jmxPort;
    }

    public void setJmxPort(Integer jmxPort) {
        this.jmxPort = jmxPort;
    }

    public Integer getServicePort() {
        return servicePort;
    }

    public void setServicePort(Integer servicePort) {
        this.servicePort = servicePort;
    }

    public String getInstallPath() {
        return installPath;
    }

    public void setInstallPath(String installPath) {
        this.installPath = installPath == null ? null : installPath.trim();
    }

    public Integer getDisorder() {
        return disorder;
    }

    public void setDisorder(Integer disorder) {
        this.disorder = disorder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getConfEnv() {
		return confEnv;
	}

	public void setConfEnv(String confEnv) {
		this.confEnv = confEnv;
	}

	public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    
}