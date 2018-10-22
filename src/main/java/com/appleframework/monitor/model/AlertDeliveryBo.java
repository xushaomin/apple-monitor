package com.appleframework.monitor.model;

import java.io.Serializable;

public class AlertDeliveryBo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String host;

    private String name;
    
    private Integer alertGroupId;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAlertGroupId() {
		return alertGroupId;
	}

	public void setAlertGroupId(Integer alertGroupId) {
		this.alertGroupId = alertGroupId;
	}
    
}