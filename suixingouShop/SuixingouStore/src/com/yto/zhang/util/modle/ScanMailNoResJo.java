package com.yto.zhang.util.modle;

import java.io.Serializable;

/**
 * @description 扫描返回对象
 * @author x.zl
 * @date 2015/1/19
 */
public class ScanMailNoResJo implements Serializable{
	
	private String buyerAddress;
	private String buyerCity;
	private String buyerDistrict;
	private String buyerProvince;
	private String buyerTelephone;
	private String goods;
	private Long id;
	private String mailNo;
	private String orderCode;
	private String orderStatus;
	private String remark;
	private String shopAddress;
	private Integer shopId;
	private String shopName;
	private String statusDesc;
	private String telephone;
	private String type;
	private Double weight;
	private String expressCompany;
	private String expressName;
	private Long updateTime;
	/**退件时间*/
	private Long backDate;
	/**签收时间*/
	private Long signDate;
	/**扫描时间*/
	private Long scanDate;
	private Integer num;
	private String prompt;
	public String getBuyerAddress() {
		return buyerAddress;
	}
	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}
	public String getBuyerCity() {
		return buyerCity;
	}
	public void setBuyerCity(String buyerCity) {
		this.buyerCity = buyerCity;
	}
	public String getBuyerDistrict() {
		return buyerDistrict;
	}
	public void setBuyerDistrict(String buyerDistrict) {
		this.buyerDistrict = buyerDistrict;
	}
	public String getBuyerProvince() {
		return buyerProvince;
	}
	public void setBuyerProvince(String buyerProvince) {
		this.buyerProvince = buyerProvince;
	}
	public String getBuyerTelephone() {
		return buyerTelephone;
	}
	public void setBuyerTelephone(String buyerTelephone) {
		this.buyerTelephone = buyerTelephone;
	}
	public String getGoods() {
		return goods;
	}
	public void setGoods(String goods) {
		this.goods = goods;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMailNo() {
		return mailNo;
	}
	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public Long getBackDate() {
		return backDate;
	}
	public void setBackDate(Long backDate) {
		this.backDate = backDate;
	}
	public Long getSignDate() {
		return signDate;
	}
	public void setSignDate(Long signDate) {
		this.signDate = signDate;
	}
	public Long getScanDate() {
		return scanDate;
	}
	public void setScanDate(Long scanDate) {
		this.scanDate = scanDate;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
}
