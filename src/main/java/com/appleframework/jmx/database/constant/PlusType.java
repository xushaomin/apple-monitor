package com.appleframework.jmx.database.constant;

//类型 =1短信 =2推送 =3邮件 =4微信
public enum PlusType {

	SMS("短信", 1),
	PUSH("推送", 2),
	MAIL("邮件", 3),
	WEIXIN("微信", 4);
	
	// 成员变量
	private String name;
	private int index;

	// 构造方法
	private PlusType(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	// 普通方法
	public static String getName(int index) {
		for (PlusType c : PlusType.values()) {
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
