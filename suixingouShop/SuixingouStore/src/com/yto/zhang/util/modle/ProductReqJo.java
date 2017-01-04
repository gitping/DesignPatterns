package com.yto.zhang.util.modle;

import java.io.Serializable;

/**
 * 获取店铺内的商品信息
 * @author longyong
 *
 */
public class ProductReqJo extends UUID {

	
	/**
	 * 店铺id
	 */
	private Integer shopId;
	
	
	/**
	 * 搜索关键词
	 */
	private String keyWord;
	
	/**
	 * 类目id
	 */
	private Integer categoryId;
	
	
	/**
	 * 页码
	 */
	private String pageIndex;
	
	
	/**
	 * 商品状态0上架 1下架（用于搜索）
	 */
	private String productStatus;

	

	

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	
}
