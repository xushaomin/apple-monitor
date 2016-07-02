package com.appleframework.jmx.database.entity;

import java.io.Serializable;
import java.util.Date;

public class AppInfoEntity implements Serializable {
	
    private Integer id;

    private Integer nodeId;

    private Integer clusterId;

    private String appName;

    private String appVersion;

    private Integer webPort;

    private String webContext;

    private Integer jmxPort;

    private Integer servicePort;

    private String installPath;
    
    private String logLevel;

    private Integer disorder;

    private String remark;

    private Short state;

    private String confDataid;

    private String confGroup;

    private String confEnv;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getClusterId() {
        return clusterId;
    }

    public void setClusterId(Integer clusterId) {
        this.clusterId = clusterId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion == null ? null : appVersion.trim();
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

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public String getConfDataid() {
        return confDataid;
    }

    public void setConfDataid(String confDataid) {
        this.confDataid = confDataid == null ? null : confDataid.trim();
    }

    public String getConfGroup() {
        return confGroup;
    }

    public void setConfGroup(String confGroup) {
        this.confGroup = confGroup == null ? null : confGroup.trim();
    }

    public String getConfEnv() {
        return confEnv;
    }

    public void setConfEnv(String confEnv) {
        this.confEnv = confEnv == null ? null : confEnv.trim();
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

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
    
}