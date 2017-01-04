package com.yto.zhang.util.modle;

/**
 * 商品条目,用在商品上架
 * @author Andy
 * Create on 2014 2014-7-21 下午1:27:44
 */
public class ProductCategoryResJo extends Version {
	/**
	 * 类目id
	 */
	private String categoryId;
	
	/**
	 * 图片地址
	 */
	private String imgUrl;
	
	/**
	 * 产品数量
	 */
	private int productNum;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

}
