package com.appleframework.monitor.model;

public enum LoggingLevelType {

	//ALL("ALL", (short)1),
	DEBUG("DEBUG", (short)2),
	INFO("INFO", (short)3),
	WARN("WARN", (short)4),
	ERROR("ERROR", (short)5),
	FATAL("FATAL", (short)6),
	//OFF("OFF", (short)7),
	TRACE("TRACE", (short)8);
	
	// 成员变量
	private String name;
	private short index;

	// 构造方法
	private LoggingLevelType(String name, short index) {
		this.name = name;
		this.index = index;
	}
	
	// 普通方法
	public static String getName(int index) {
		for (LoggingLevelType c : LoggingLevelType.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getIndex() {
		return index;
	}

	public void setIndex(short index) {
		this.index = index;
	}
	
}
