package com.yto.zhang.util.modle;

import java.util.List;

/**
 * 订单信息
 * 
 * @author longyong
 * 
 */
public class OrderDeatailResJo extends Version {

	/**
	 * 订单id
	 */
	private Long id;

	/**
	 * 订单编号
	 */
	private String orderCode;

	/**
	 * 店铺名称
	 */
	private String shopName;

	/**
	 * 买家电话
	 */
	private String telephone;

	/**
	 * 买家联系人
	 */
	private String buyerName;

	/**
	 * 卖家电话
	 */

	private String sellerTelphone;

	/**
	 * 期望配送时间
	 */
	private String expireExpressTime;

	/**
	 * 实际配送时间
	 */
	private String actualExpressTime;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 配送省
	 */
	private String buyerProvinceCode;

	/**
	 * 配送市
	 */
	private String buyerCityCode;

	/**
	 * 配送地区
	 */
	private String buyerDistrictCode;

	/**
	 * 配送详细地址
	 */
	private String buyerAddress;

	/**
	 * 是否有小票
	 */
	private String haveTips;

	/**
	 * 备注信息
	 */
	private String remark;

	/**
	 * 投诉信息
	 */
	private ComplaintResJo complaintResJo;

	/**
	 * 商品列表
	 */
	private List<ProductResJo> productResJos;

	/**
	 * 订单所在的店铺信息
	 */
	private ShopResJo shopResJo;
	
	
	/**
	 * 取消订单原因
	 */
	private String statusDesc;
	
	
	
	

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getSellerTelphone() {
		return sellerTelphone;
	}

	public void setSellerTelphone(String sellerTelphone) {
		this.sellerTelphone = sellerTelphone;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getExpireExpressTime() {
		return expireExpressTime;
	}

	public void setExpireExpressTime(String expireExpressTime) {
		this.expireExpressTime = expireExpressTime;
	}

	public String getActualExpressTime() {
		return actualExpressTime;
	}

	public void setActualExpressTime(String actualExpressTime) {
		this.actualExpressTime = actualExpressTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getBuyerProvinceCode() {
		return buyerProvinceCode;
	}

	public void setBuyerProvinceCode(String buyerProvinceCode) {
		this.buyerProvinceCode = buyerProvinceCode;
	}

	public String getBuyerCityCode() {
		return buyerCityCode;
	}

	public void setBuyerCityCode(String buyerCityCode) {
		this.buyerCityCode = buyerCityCode;
	}

	public String getBuyerDistrictCode() {
		return buyerDistrictCode;
	}

	public void setBuyerDistrictCode(String buyerDistrictCode) {
		this.buyerDistrictCode = buyerDistrictCode;
	}

	public String getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	public String getHaveTips() {
		return haveTips;
	}

	public void setHaveTips(String haveTips) {
		this.haveTips = haveTips;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ComplaintResJo getComplaintResJo() {
		return complaintResJo;
	}

	public void setComplaintResJo(ComplaintResJo complaintResJo) {
		this.complaintResJo = complaintResJo;
	}

	public List<ProductResJo> getProductResJos() {
		return productResJos;
	}

	public void setProductResJos(List<ProductResJo> productResJos) {
		this.productResJos = productResJos;
	}

	public ShopResJo getShopResJo() {
		return shopResJo;
	}

	public void setShopResJo(ShopResJo shopResJo) {
		this.shopResJo = shopResJo;
	}

}
