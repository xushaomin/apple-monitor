package com.appleframework.jmx.database.entity;

import java.io.Serializable;
import java.util.Date;

public class AlertTemplEntity implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String title;

    private Integer tmpType;

    private String tmpCode;

    private String tmpThirdId;

    private String template;

    private String authId;

    private Boolean isContain;

    private Boolean isDelete;

    private String remark;

    private Date createTime;

    private Date updateTime;


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getTmpType() {
        return tmpType;
    }

    public void setTmpType(Integer tmpType) {
        this.tmpType = tmpType;
    }

    public String getTmpCode() {
        return tmpCode;
    }

    public void setTmpCode(String tmpCode) {
        this.tmpCode = tmpCode == null ? null : tmpCode.trim();
    }

    public String getTmpThirdId() {
        return tmpThirdId;
    }

    public void setTmpThirdId(String tmpThirdId) {
        this.tmpThirdId = tmpThirdId == null ? null : tmpThirdId.trim();
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template == null ? null : template.trim();
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId == null ? null : authId.trim();
    }

    public Boolean getIsContain() {
        return isContain;
    }

    public void setIsContain(Boolean isContain) {
        this.isContain = isContain;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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