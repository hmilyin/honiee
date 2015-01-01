package com.ryan.doubletrack.rebates.dto;

import java.util.Date;

import com.ryan.framework.dto.Dto;

public class RebatesDto implements Dto{

	private Long rebatesId;
	private Long customerId;
	private String customerCode;
	private String customerName;
	private Integer count;
	private Integer customerType;
	private Date createTime;
	private Date lastModifyTime;

	public RebatesDto() {
	}

	public RebatesDto(Long rebatesId) {
		this.rebatesId = rebatesId;
	}

	public RebatesDto(Long rebatesId, Long customerId, String customerCode,
			String customerName, Integer customerType, Integer count, 
			Date createTime, Date lastModifyTime) {
		this.rebatesId = rebatesId;
		this.customerId = customerId;
		this.customerCode = customerCode;
		this.customerName = customerName;
		this.customerType = customerType;
		this.count = count;
		this.createTime = createTime;
		this.lastModifyTime = lastModifyTime;
	}

	public Long getRebatesId() {
		return this.rebatesId;
	}

	public void setRebatesId(Long rebatesId) {
		this.rebatesId = rebatesId;
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

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

}
