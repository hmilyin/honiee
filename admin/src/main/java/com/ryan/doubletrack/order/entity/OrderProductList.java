package com.ryan.doubletrack.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.ryan.framework.entity.Model;

@Entity
@Table(name = "order_product_list")
public class OrderProductList implements Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long orderId;
	private Long productId;
	private String productName;
	private Double productPrice;
	private Integer productCount;
	private Double totalPrice;

	public OrderProductList() {
	}

	public OrderProductList(Long productId, Double totalPrice) {
		this.productId = productId;
		this.totalPrice = totalPrice;
	}

	public OrderProductList(Long productId, String productName,
			Double productPrice, Integer productCount, Double totalPrice) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productCount = productCount;
		this.totalPrice = totalPrice;
	}

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "order_id")
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Column(name = "product_id", nullable = false)
	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Column(name = "product_name", length = 50)
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "product_price")
	public Double getProductPrice() {
		return this.productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	@Column(name = "product_count")
	public Integer getProductCount() {
		return this.productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	@Column(name = "total_price", nullable = false, precision = 15)
	public Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
