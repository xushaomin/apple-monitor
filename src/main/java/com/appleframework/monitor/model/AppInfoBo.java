package com.appleframework.monitor.model;

import com.appleframework.jmx.database.entity.AppInfoEntity;

public class AppInfoBo extends AppInfoEntity {
	
	private static final long serialVersionUID = 1L;
	
    private Boolean isAlert;
    
    private Boolean isDown;

	public Boolean getIsAlert() {
		return isAlert;
	}

	public void setIsAlert(Boolean isAlert) {
		this.isAlert = isAlert;
	}

	public Boolean getIsDown() {
		return isDown;
	}

	public void setIsDown(Boolean isDown) {
		this.isDown = isDown;
	}
    
}