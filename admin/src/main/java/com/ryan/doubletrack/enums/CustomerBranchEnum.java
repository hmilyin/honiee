package com.ryan.doubletrack.enums;


public enum CustomerBranchEnum {
	
	LEFT(new Integer(1),"左区"),
	RIGHT(new Integer(2),"右区");
	
	private Integer key;
	
	private String value;

	public Integer getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	CustomerBranchEnum(Integer key,String value) {
		this.key = key;
		this.value = value;
	}
}
