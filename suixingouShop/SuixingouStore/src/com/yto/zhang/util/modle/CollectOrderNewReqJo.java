package com.yto.zhang.util.modle;




/**
 * 代收包裹新订单
 * 
 * @author jingzhong
 * 
 */

public class CollectOrderNewReqJo extends UUID {


	/**
	 * 快递单号
	 */
	private String mailNo;
	
	/**
	 * 手机号码
	 */
	private String telephone;
	
	
	
	/**
	 * 快递公司： 圆通：yto 申通：sto 中通：zto 汇通：hto 韵达：yunda 邮政：ems
	 */
	private String expressCompany;
	
	
	
	
	
	
	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
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
	
	

}
