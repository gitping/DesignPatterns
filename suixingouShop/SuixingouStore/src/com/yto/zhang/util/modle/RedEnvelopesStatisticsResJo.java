package com.yto.zhang.util.modle;

/**
 * 红包统计条目
 * 总计在DTO2的T2中传
 * @author Andy
 * Create on 2014 2014-5-19 下午5:22:06
 */
public class RedEnvelopesStatisticsResJo extends Version{
	
	private Integer id;
	/**时间*/
	private String data;
	/**红包数量*/
	private Integer redEnvelopesNum;
	/**支付金额*/
	private Double paymentAmount;
	/**红包阙状态    已结算: 0         结算中: 1   待结算: 2 */
	private int status;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Integer getRedEnvelopesNum() {
		return redEnvelopesNum;
	}
	public void setRedEnvelopesNum(Integer redEnvelopesNum) {
		redEnvelopesNum = redEnvelopesNum;
	}
	public Double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
