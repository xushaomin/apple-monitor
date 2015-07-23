package com.appleframework.jmx.database.constant;

public enum IsCluster {

	NO("NO", (short)0),
	YES("启用", (short)1);
	
	// 成员变量
	private String name;
	private short index;

	// 构造方法
	private IsCluster(String name, short index) {
		this.name = name;
		this.index = index;
	}
	
	// 普通方法
	public static String getName(int index) {
		for (IsCluster c : IsCluster.values()) {
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
