package com.ryan.doubletrack.product.dto;

import com.ryan.framework.dto.Dto;

public class ProductDto implements Dto {

	private Long productId;
	private String productName;
	private String productExpandName;
	private Integer categoryId;
	private Long productTotalCount;
	private Double basicPrice;
	private String description;
	private String merchantProductCode;

	public ProductDto() {
	}

	public ProductDto(Long productId, String productName) {
		this.productId = productId;
		this.productName = productName;
	}

	public ProductDto(Long productId, String productName, String productExpandName,
			Integer categoryId, Long productTotalCount, Double basicPrice,
			String description, String merchantProductCode) {
		this.productId = productId;
		this.productName = productName;
		this.productExpandName = productExpandName;
		this.categoryId = categoryId;
		this.productTotalCount = productTotalCount;
		this.basicPrice = basicPrice;
		this.description = description;
		this.merchantProductCode = merchantProductCode;
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

	public String getProductExpandName() {
		return this.productExpandName;
	}

	public void setProductExpandName(String productExpandName) {
		this.productExpandName = productExpandName;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Long getProductTotalCount() {
		return this.productTotalCount;
	}

	public void setProductTotalCount(Long productTotalCount) {
		this.productTotalCount = productTotalCount;
	}

	public Double getBasicPrice() {
		return this.basicPrice;
	}

	public void setBasicPrice(Double basicPrice) {
		this.basicPrice = basicPrice;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getMerchantProductCode() {
		return this.merchantProductCode;
	}

	public void setMerchantProductCode(String merchantProductCode) {
		this.merchantProductCode = merchantProductCode;
	}

}
