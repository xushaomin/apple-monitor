package com.appleframework.jmx.database.constant;

public enum StateType {

	STOP("禁用", (short)0),
	START("启用", (short)1),
	DELETE("删除", (short)2);
	
	// 成员变量
	private String name;
	private short index;

	// 构造方法
	private StateType(String name, short index) {
		this.name = name;
		this.index = index;
	}
	
	// 普通方法
	public static String getName(int index) {
		for (StateType c : StateType.values()) {
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
