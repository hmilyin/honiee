package com.ryan.doubletrack.wage.dto;

import java.util.Date;

import com.ryan.framework.dto.Dto;

public class WageDto implements Dto {

	private Long wageId;
	private Long customerId;
	private String customerCode;
	private String customerName;
	private String phone;
	private String idCardNumber;
	private String bankType;
	private String bankAccount;
	private Float salary;
	private Date createTime;
	private Date startCreateTime;
	private Date endCreateTime;
	private Date lastModifyTime;

	public WageDto() {
	}

	public WageDto(Long wageId) {
		this.wageId = wageId;
	}

	public WageDto(Long wageId, Long customerId, Float salary, Date createTime,
			Date lastModifyTime) {
		this.wageId = wageId;
		this.customerId = customerId;
		this.salary = salary;
		this.createTime = createTime;
		this.lastModifyTime = lastModifyTime;
	}


	public Long getWageId() {
		return this.wageId;
	}

	public void setWageId(Long wageId) {
		this.wageId = wageId;
	}


	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Float getSalary() {
		return this.salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
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

}
