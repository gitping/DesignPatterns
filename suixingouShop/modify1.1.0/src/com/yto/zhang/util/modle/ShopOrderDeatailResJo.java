package com.yto.zhang.util.modle;

import java.util.List;





/**
 * 商家订单详情
 * 
 * @author jingzhong
 * 
 */
public class ShopOrderDeatailResJo extends Version {

	/**
	 * id
	 */
	private Long id;

	/**
	 * 订单编号
	 */
	private String orderCode;

	/**
	 * 期望配送时间
	 */
	private String expireExpressTime;

	/**
	 * 预计配送时间
	 */
	private String actualExpressTime;
	/**
	 * 地址
	 */
	private String buyerAddress;

	/**
	 * 买家姓名
	 */
	private String buyerName;

	/**
	 * 买家电话
	 */
	private String telephone;
	
	

	/**
	 * 备注信息
	 */
	private String remark;

	/**
	 * 买家商品
	 */
	List<ProductResJo> productResJos;
	/**
	 * 价格总计
	 */
	private String totalPrice;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 不接单的说明
	 */
	private String statusDesc; 
	/**
	 * 红包信息
	 */
	private RedEnvelopesResJo redEnvelopesResJo;
	public RedEnvelopesResJo getRedEnvelopesResJo() {
		return redEnvelopesResJo;
	}

	public void setRedEnvelopesResJo(RedEnvelopesResJo redEnvelopesResJo) {
		this.redEnvelopesResJo = redEnvelopesResJo;
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

	public String getExpireExpressTime() {
		return expireExpressTime;
	}

	public void setExpireExpressTime(String expireExpressTime) {
		this.expireExpressTime = expireExpressTime;
	}

	public String getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<ProductResJo> getProductResJos() {
		return productResJos;
	}

	public void setProductResJos(List<ProductResJo> productResJos) {
		this.productResJos = productResJos;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getActualExpressTime() {
		return actualExpressTime;
	}

	public void setActualExpressTime(String actualExpressTime) {
		this.actualExpressTime = actualExpressTime;
	}
	
	
	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	@Override
	public String toString() {
		return "orderCode"+orderCode+"orderstatus"+orderStatus;
	}

}
