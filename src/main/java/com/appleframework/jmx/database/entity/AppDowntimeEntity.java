package com.appleframework.jmx.database.entity;

import java.io.Serializable;
import java.util.Date;

public class AppDowntimeEntity implements Serializable {
	
    private Integer id;

    private Short state;

    private Date recordingStart;

    private Date recordingEnd;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public Date getRecordingStart() {
        return recordingStart;
    }

    public void setRecordingStart(Date recordingStart) {
        this.recordingStart = recordingStart;
    }

    public Date getRecordingEnd() {
        return recordingEnd;
    }

    public void setRecordingEnd(Date recordingEnd) {
        this.recordingEnd = recordingEnd;
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