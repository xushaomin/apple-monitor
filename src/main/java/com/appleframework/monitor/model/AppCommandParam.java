package com.appleframework.monitor.model;

import java.io.Serializable;

public class AppCommandParam implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String host;

	private String installPath;
	
	private String env;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getInstallPath() {
		return installPath;
	}

	public void setInstallPath(String installPath) {
		this.installPath = installPath;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public AppCommandParam(Integer id, String host, String installPath, String env) {
		super();
		this.id = id;
		this.host = host;
		this.installPath = installPath;
		this.env = env;
	}
	
	public static AppCommandParam create(Integer id, String host, String installPath, String env) {
		return new AppCommandParam(id, host, installPath, env);
	}

}