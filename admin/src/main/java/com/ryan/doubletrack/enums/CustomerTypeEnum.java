package com.ryan.doubletrack.enums;


public enum CustomerTypeEnum {

	SELL(new Integer(1),"业务员"),
	BEAUTY_GUIDING_SHOP(new Integer(2),"美导店"),
	BEAUTY_SHOP(new Integer(3),"美容店");
	
	private Integer key;
	
	private String value;

	public Integer getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	CustomerTypeEnum(Integer key,String value) {
		this.key = key;
		this.value = value;
	}
}
