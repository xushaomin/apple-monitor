package com.appleframework.monitor.model;

import java.io.Serializable;

public class AlertContactSo implements Serializable {

	private static final long serialVersionUID = -1176369717629985251L;

	private Integer id;

    private String name;

    private String mobile;

    private String email;

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
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    
    
    
}