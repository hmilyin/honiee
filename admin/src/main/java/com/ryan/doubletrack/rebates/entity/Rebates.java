package com.ryan.doubletrack.rebates.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.ryan.framework.entity.Model;

@Entity
@Table(name = "rebates")
public class Rebates implements Model{

	private static final long serialVersionUID = 1L;
	private Long rebatesId;
	private Long customerId;
	private String customerCode;
	private String customerName;
	private Integer count;
	private Integer customerType;
	private Date createTime;
	private Date lastModifyTime;

	public Rebates() {
	}

	public Rebates(Long rebatesId) {
		this.rebatesId = rebatesId;
	}

	public Rebates(Long rebatesId, Long customerId, String customerCode,
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

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "rebates_id", unique = true, nullable = false)
	public Long getRebatesId() {
		return this.rebatesId;
	}

	public void setRebatesId(Long rebatesId) {
		this.rebatesId = rebatesId;
	}

	@Column(name = "customer_id")
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "customer_code", length = 20)
	public String getCustomerCode() {
		return this.customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	@Column(name = "customer_name", length = 30)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "count")
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}


	@Column(name = "customer_type")
	public Integer getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modify_time", length = 19)
	public Date getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

}
