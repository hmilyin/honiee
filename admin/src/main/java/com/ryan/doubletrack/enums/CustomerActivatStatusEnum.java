package com.ryan.doubletrack.enums;


public enum CustomerActivatStatusEnum {

	NOACTIVAT(new Integer(0),"未激活"),
	PHONEACTIVAT(new Integer(1),"手机激活"),
	EMAILACTIVAT(new Integer(2),"邮箱激活"),
	ALLACTIVAT(new Integer(3),"全部激活");
	
	private Integer key;
	
	private String value;

	public Integer getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	CustomerActivatStatusEnum(Integer key,String value) {
		this.key = key;
		this.value = value;
	}
}
