package com.ryan.doubletrack.enums;


public enum CustomerStatusEnum {

	NORMAL(new Integer(1),"正常"),
//	DELETE(new Integer(3),"删除"),
	FREEZE(new Integer(2),"冻结")
	;
	
	private Integer key;
	
	private String value;

	public Integer getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	CustomerStatusEnum(Integer key,String value) {
		this.key = key;
		this.value = value;
	}
}
