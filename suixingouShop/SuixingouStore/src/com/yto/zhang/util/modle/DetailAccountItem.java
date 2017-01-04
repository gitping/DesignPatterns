package com.yto.zhang.util.modle;

import java.util.List;

/**
 * 帐目明细的条目
 * 
 * @author Andy Create on 2014 2014-7-21 上午10:47:26
 */
public class DetailAccountItem extends Version {
	/**
	 * 类别  0:订单补贴   1:邀请奖励
	 */
	private int type;
	
	/**
	 * 订单状态  1:待结算 2:已结算
	 */
	private int status;
	/**
	 * 日期
	 */
	private String date;
	/**
	 * 订单或邀请的数量
	 */
	private int orderNum;
	/**
	 * 金额
	 */
	private Double money;
	
	/**
	 * 帐单的id
	 */
	private Integer billId;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}



}
