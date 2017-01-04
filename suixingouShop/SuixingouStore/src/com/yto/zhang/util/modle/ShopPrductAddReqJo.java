package com.yto.zhang.util.modle;



/**
 * 
 *  店铺添加商品
 *
 */

public class ShopPrductAddReqJo   extends UUID{
	
	
	/**
	 * 名字
	 */
	private String productName;
	
	/**
	 * 规格
	 */
	private String productSku;
	
	
	private String categoryId;
	
	/**
	 * 品牌
	 */
	private String productBrand;
	
	/**
	 * 描述
	 */
	private String productDesc;
	
	private String productUrl;
	/**
	 * 单位
	 */
	private String productUnit;
	
	/**
	 * 名称全拼的首字母
	 * 
	 */
	private String firstLetter;
	
	
	/**
	 * 价格
	 */
	private Double price;
	
	
	private String productCode;
	
	
	
	
	


	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductSku() {
		return productSku;
	}

	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}


	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}
	
	
	
	

	
	
	
}
