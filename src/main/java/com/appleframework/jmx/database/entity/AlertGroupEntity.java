package com.appleframework.jmx.database.entity;

import java.io.Serializable;
import java.util.Date;

public class AlertGroupEntity implements Serializable {
	
    private Integer id;

    private String name;
    
    private Boolean needSms;
    
    private Boolean needMail;
    
    private Boolean needWeixin;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

	public Boolean getNeedSms() {
		return needSms;
	}

	public void setNeedSms(Boolean needSms) {
		this.needSms = needSms;
	}

	public Boolean getNeedMail() {
		return needMail;
	}

	public void setNeedMail(Boolean needMail) {
		this.needMail = needMail;
	}

	public Boolean getNeedWeixin() {
		return needWeixin;
	}

	public void setNeedWeixin(Boolean needWeixin) {
		this.needWeixin = needWeixin;
	}
    
}