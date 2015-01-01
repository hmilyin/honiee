package com.ryan.doubletrack.enums;


public enum ExceptionEnum {

	CUSTOMER_SAVE_ERROR("101001","customer save error!","客户信息保存失败！"),
	CUSTOMER_BRANCH_ERROR("101002","branch is exists!","该分区已经存在，不能重复添加！"),
	CUSTOMER_ID_NOT_NULL("101003","customer id not null!","客户不能为空！请选择要删除的客户！"),
	CUSTOMER_ID_NOT_EMPTY("101004","customer id not empty!","客户不能为空！请选择要删除的客户！"),
	CUSTOMER_DELETE_ERROR("101005","customer delete faile！","客户删除失败！"),
	ORDER_SAVE_ERROR("102001","order save error!","订单信息保存失败！"),
	ORDER_ID_NOT_NULL("102002","order id not null!","订单不能为空！请选择要删除的订单！"),
	ORDER_ID_NOT_EMPTY("102003","order id not empty!","订单不能为空！请选择要删除的订单！"),
	ORDER_DELETE_ERROR("102004","order delete faile！","订单删除失败！"),
	ORDER_CODE_REPEAT("102005","order_code repeat！","订单编号已经存在，订单信息保存失败！"),
	ORDER_CLEAN_HISTORY_SUCCESS("102006","order clean faile！","清除订单历史信息失败！"),
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

	ExceptionEnum(String key,String value,String description) {
		this.key = key;
		this.value = value;
		this.description = description;
	}
}
