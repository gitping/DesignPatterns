package com.yto.zhang.util.modle;

import java.util.Date;


import  com.yto.zhang.util.modle.UUID;

/**
 * 获取代收包裹各种订单
 * 
 * @author jingzhong
 * 
 */

public class CollectOrderResJo extends UUID {

	/**
	 * db 主键
	 */
	private Long id;
	/**
	 * 快递单号
	 */
	private String mailNo;

	/**
	 * 手机号码
	 */
	private String telephone;

	/**
	 * 创建时间=到货时间
	 */
	private Long createTime;

	/**
	 * 订单编号=取件密码
	 */
	private String orderCode;

	/**
	 * 订单状态
	 */
	private String orderStatus;
	
	//取件时间
	private Long expireExpressTime;
	
	//快递类型
	private  String expressCompany;
	
	//扫描时用：是否是自己的快递单：1是0否
	private boolean mine=false;
	
	
	private Integer num;
	
	
	
	
	
	



	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public Long getExpireExpressTime() {
		return expireExpressTime;
	}

	public void setExpireExpressTime(Long expireExpressTime) {
		this.expireExpressTime = expireExpressTime;
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
