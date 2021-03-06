package com.ryan.doubletrack.order.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.ryan.framework.entity.Model;

/**
 * Order generated by hbm2java
 */
@Entity
@Table(name = "orders")
public class Order implements Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private OrderProductList orderProductList;

	public Order() {
	}

	public Order(Long orderId, String orderCode, Long customerId, String customerCode,
			String customerName) {
		this.orderId = orderId;
		this.orderCode = orderCode;
		this.customerId = customerId;
		this.customerCode = customerCode;
		this.customerName = customerName;
	}

	public Order(Long orderId, String orderCode, Long customerId, String customerCode,
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

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "order_id", unique = true, nullable = false)
	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Column(name = "order_code", nullable = false, length = 50)
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@Column(name = "customer_id", nullable = false)
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "customer_code", nullable = false, length = 20)
	public String getCustomerCode() {
		return this.customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	@Column(name = "customer_name", nullable = false, length = 50)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "customer_type")
	public Integer getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	@Column(name = "total_price")
	public Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Transient
	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	@Transient
	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modify_time", length = 19)
	public Date getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Column(name = "remark", length = 1024)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="order_id")
	public OrderProductList getOrderProductList() {
		return orderProductList;
	}

	public void setOrderProductList(OrderProductList orderProductList) {
		this.orderProductList = orderProductList;
	}

}
