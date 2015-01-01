package com.ryan.doubletrack.customer.dto;
import com.ryan.framework.dto.Dto;

public class CustomerDto implements Dto {

	private Long customerId;
	private String customerCode;
	private String customerName;
	private Integer customerType;
	private Integer level;
	private String phone;
	private String contact;
	private String idCardNumber;
	private String qq;
	private String bankType;
	private String bankAccount;
	private String address;
//	private Long parentCustomerId;
//	private Long recommendCustomerId;
	private CustomerDto parentCustomerDto;
	private CustomerDto recommendCustomerDto;
	private String customerSequence;
	private Integer status;
	private Integer activatStatus;  //0-都未激活；1-手机号激活；2-邮箱激活；3-都激活
	private String email;//客户邮箱
	private String remark;


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public CustomerDto() {
	}

	public CustomerDto(Long customerId, String customerCode, String customerName) {
		this.customerId = customerId;
		this.customerCode = customerCode;
		this.customerName = customerName;
	}

	public CustomerDto(Long customerId, String customerCode, String customerName,
			Integer customerType, Integer level, String phone, String contact,
			String idCardNumber, String qq, String bankType,
			String bankAccount, String address, Long parentCustomerId,
			Long recommendCustomerId, String customerSequence) {
		this.customerId = customerId;
		this.customerCode = customerCode;
		this.customerName = customerName;
		this.customerType = customerType;
		this.level = level;
		this.phone = phone;
		this.contact = contact;
		this.idCardNumber = idCardNumber;
		this.qq = qq;
		this.bankType = bankType;
		this.bankAccount = bankAccount;
		this.address = address;
//		this.parentCustomerId = parentCustomerId;
//		this.recommendCustomerId = recommendCustomerId;
		this.customerSequence = customerSequence;
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getIdCardNumber() {
		return this.idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getBankType() {
		return this.bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankAccount() {
		return this.bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

//	public Long getParentCustomerId() {
//		return this.parentCustomerId;
//	}
//
//	public void setParentCustomerId(Long parentCustomerId) {
//		this.parentCustomerId = parentCustomerId;
//	}
//
//	public Long getRecommendCustomerId() {
//		return this.recommendCustomerId;
//	}
//
//	public void setRecommendCustomerId(Long recommendCustomerId) {
//		this.recommendCustomerId = recommendCustomerId;
//	}

	public String getCustomerSequence() {
		return this.customerSequence;
	}

	public void setCustomerSequence(String customerSequence) {
		this.customerSequence = customerSequence;
	}

	public CustomerDto getParentCustomerDto() {
		return parentCustomerDto;
	}

	public void setParentCustomerDto(CustomerDto parentCustomerDto) {
		this.parentCustomerDto = parentCustomerDto;
	}

	public CustomerDto getRecommendCustomerDto() {
		return recommendCustomerDto;
	}

	public void setRecommendCustomerDto(CustomerDto recommendCustomerDto) {
		this.recommendCustomerDto = recommendCustomerDto;
	}

	public Integer getActivatStatus() {
		return activatStatus;
	}

	public void setActivatStatus(Integer activatStatus) {
		this.activatStatus = activatStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
