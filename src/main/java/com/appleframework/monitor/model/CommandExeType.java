package com.appleframework.monitor.model;

public enum CommandExeType {

	START("start"),
	STOP("stop"),
	RESTART("restart"),
	SERVER("server"),
	DUMP("dump");
	
	// 成员变量
	private String name;

	// 构造方法
	private CommandExeType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
