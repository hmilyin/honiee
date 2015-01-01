package com.ryan.doubletrack.enums;


public enum ProductCategoryEnum {
	
	BASIS_SUIT(new Integer(1),"基础套装");
	
	private Integer key;
	
	private String value;

	public Integer getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	ProductCategoryEnum(Integer key,String value) {
		this.key = key;
		this.value = value;
	}
}
