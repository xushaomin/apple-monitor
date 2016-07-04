package com.appleframework.monitor.model;

import java.io.Serializable;

public class CommandParam implements Serializable {

	private static final long serialVersionUID = 1L;

	//{"hosts": ["192.168.1.204"], "option": "stop", "path": "/work/www/biz-task/work/bin/stop.sh"}

	private String[] hosts;
	private String option;
	private String env;
	private String path;

	public String[] getHosts() {
		return hosts;
	}

	public void setHosts(String[] hosts) {
		this.hosts = hosts;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
