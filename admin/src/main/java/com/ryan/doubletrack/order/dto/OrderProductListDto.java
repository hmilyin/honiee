package com.ryan.doubletrack.order.dto;

import com.ryan.framework.dto.Dto;

public class OrderProductListDto implements Dto{

	private Long id;
	private Long orderId;
	private Long productId;
	private String productName;
	private Double productPrice;
	private Integer productCount;
	private Double totalPrice;

	public OrderProductListDto() {
	}

	public OrderProductListDto(Long productId, Double totalPrice) {
		this.productId = productId;
		this.totalPrice = totalPrice;
	}

	public OrderProductListDto(Long productId, String productName,
			Double productPrice, Integer productCount, Double totalPrice) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productCount = productCount;
		this.totalPrice = totalPrice;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
