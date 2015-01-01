package com.ryan.doubletrack.enums;


public enum MessageEnum {

	CUSTOMER_SAVE_SUCCESS("101001","customer save success!","客户信息已保存！"),
	CUSTOMER_DELETE_SUCCESS("101002","customer delete success!","客户已删除！"),
	ORDER_SAVE_SUCCESS("102001","order save success!","订单信息已保存！"),
	ORDER_DELETE_SUCCESS("102002","order delete success!","订单已删除！"),
	ORDER_CLEAN_HISTORY_SUCCESS("102002","order delete success!","订单历史信息已清除！"),
	REBATES_SETTLEMENT_SUCCESS("103001","rebates results of settlement success!","业绩结算成功！"),
	;
	
	private String key;
	
	private String value;
	
	private String description;

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	MessageEnum(String key,String value,String description) {
		this.key = key;
		this.value = value;
		this.description = description;
	}
}
