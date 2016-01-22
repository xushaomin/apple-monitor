package com.appleframework.monitor.model;

import com.appleframework.jmx.database.entity.AppInfoEntity;

public class AppInfoBo extends AppInfoEntity {
	
	private static final long serialVersionUID = 1L;
	
    private Boolean isAlert;

	public Boolean getIsAlert() {
		return isAlert;
	}

	public void setIsAlert(Boolean isAlert) {
		this.isAlert = isAlert;
	}
    
}