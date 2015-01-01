package com.ryan.doubletrack.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.ryan.framework.entity.Model;

@Entity
@Table(name = "product")
public class Product implements Model{

	private static final long serialVersionUID = 1L;
	private Long productId;
	private String productName;
	private String productExpandName;
	private Integer categoryId;
	private Long productTotalCount;
	private Double basicPrice;
	private String description;
	private String merchantProductCode;

	public Product() {
	}

	public Product(Long productId, String productName) {
		this.productId = productId;
		this.productName = productName;
	}

	public Product(Long productId, String productName, String productExpandName,
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
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "product_id", unique = true, nullable = false)
	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Column(name = "product_name", nullable = false, length = 20)
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "product_expand_name", length = 512)
	public String getProductExpandName() {
		return this.productExpandName;
	}

	public void setProductExpandName(String productExpandName) {
		this.productExpandName = productExpandName;
	}

	@Column(name = "category_id")
	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "product_total_count")
	public Long getProductTotalCount() {
		return this.productTotalCount;
	}

	public void setProductTotalCount(Long productTotalCount) {
		this.productTotalCount = productTotalCount;
	}

	@Column(name = "basic_price", precision = 11)
	public Double getBasicPrice() {
		return this.basicPrice;
	}

	public void setBasicPrice(Double basicPrice) {
		this.basicPrice = basicPrice;
	}

	@Column(name = "description", length = 1024)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "merchant_product_code", length = 256)
	public String getMerchantProductCode() {
		return this.merchantProductCode;
	}

	public void setMerchantProductCode(String merchantProductCode) {
		this.merchantProductCode = merchantProductCode;
	}

}
