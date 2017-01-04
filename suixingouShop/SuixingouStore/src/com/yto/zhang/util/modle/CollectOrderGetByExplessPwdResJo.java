package com.yto.zhang.util.modle;


/**
 * 密码取件相应
 * 
 * @author Tiansheng.Cheng
 * @since 2015-01-19
 */

public class CollectOrderGetByExplessPwdResJo extends UUID {
	private static final long serialVersionUID = 1L;
	
	/** 取货地址 */
	private String buyerAddress;
	/** 取货城市 */
	private String buyerCity;
	/** 取货地区 */
	private String buyerDistrict;
	/** 取货省 */
	private String buyerProvince;
	/** 买家电话 */
	private String buyerTelephone;
	/** 商品 */
	private String goods;
	/** Db 主键 */
	private Long id;
	/** 快递单号 */
	private String mailNo;
	/** 订单编号 */
	private String orderCode;
	/** 订单状态 */
	private String orderStatus;
	/** 备注 */
	private String remark;
	/** 商店地址 */
	private String shopAddress;
	/** 商店id */
	private Integer shopId;
	/** 商店名称 */
	private String shopName;
	/** 状态描述 */
	private String statusDesc;
	/** 电话 */
	private String telephone;
	/** 类型（2：上门取件、3：代收包裹） */
	private String type;
	/** 重量 */
	private Double weight;
	/** 取件密码 */
	private String expressPwd;
	/** 序号 */
	private Integer num;
	/**退件时间*/
	private Long backDate;
	/**签收时间*/
	private Long signDate;
	/** 扫件日期 */
	private Long scanDate;
	/** 更新时间 */
	private Long updateTime;
	/** 快递公司名称 */
	private String expressName;
	/** 提示语 */
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getExpressPwd() {
		return expressPwd;
	}
	public void setExpressPwd(String expressPwd) {
		this.expressPwd = expressPwd;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
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
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
}
