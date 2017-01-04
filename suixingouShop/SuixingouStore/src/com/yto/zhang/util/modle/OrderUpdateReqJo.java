package com.yto.zhang.util.modle;


/**
 * 订单状态更新
 * 
 * @author longyong
 * 
 */
public class OrderUpdateReqJo extends UUID {

	/**
	 * 订单id
	 */
	private Long id;

	/**
	 * 订单状态
	 */
	private String status;

	/**
	 * 备注信息
	 */
	private String remark;

	/**
	 * 预计送达时间
	 */
	private String actualExpressTime;
	private Integer type;
	
	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActualExpressTime() {
		return actualExpressTime;
	}

	public void setActualExpressTime(String actualExpressTime) {
		this.actualExpressTime = actualExpressTime;
	}

}
