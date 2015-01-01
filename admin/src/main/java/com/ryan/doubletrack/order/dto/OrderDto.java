package com.ryan.doubletrack.order.dto;

import java.util.Date;

import com.ryan.framework.dto.Dto;

public class OrderDto implements Dto{

	private Long orderId;
	private String orderCode;
	private Long customerId;
	private String customerCode;
	private String customerName;
	private Integer customerType;
	private Double totalPrice;
	private Date createTime;
	private Date startCreateTime;
	private Date endCreateTime;
	private Date lastModifyTime;
	private String remark;
	private OrderProductListDto orderProductListDto;

	public OrderDto() {
	}

	public OrderDto(Long orderId, String orderCode, Long customerId, String customerCode,
			String customerName) {
		this.orderId = orderId;
		this.orderCode = orderCode;
		this.customerId = customerId;
		this.customerCode = customerCode;
		this.customerName = customerName;
	}

	public OrderDto(Long orderId, String orderCode, Long customerId, String customerCode,
			String customerName, Integer customerType, Double totalPrice,
			Date createTime, Date lastModifyTime) {
		this.orderId = orderId;
		this.orderCode = orderCode;
		this.customerId = customerId;
		this.customerCode = customerCode;
		this.customerName = customerName;
		this.customerType = customerType;
		this.totalPrice = totalPrice;
		this.createTime = createTime;
		this.lastModifyTime = lastModifyTime;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerCode() {
		return this.customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}


	public Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public Date getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public OrderProductListDto getOrderProductListDto() {
		return orderProductListDto;
	}

	public void setOrderProductListDto(OrderProductListDto orderProductListDto) {
		this.orderProductListDto = orderProductListDto;
	}
}
