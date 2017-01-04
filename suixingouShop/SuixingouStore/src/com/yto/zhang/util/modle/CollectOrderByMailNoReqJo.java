package com.yto.zhang.util.modle;


/**
 * 凭手机号或面单号获取包裹M70
 * 
 * @author Alex
 * @since 2014-11-25
 */

public class CollectOrderByMailNoReqJo extends UUID {

	/**
	 * 快递单号OR手机号码
	 */
	private String mailNoOrTelephone;

	public void setMailNoOrTelephone(String mailNoOrTelephone) {
		this.mailNoOrTelephone = mailNoOrTelephone;
	}

	public String getMailNoOrTelephone() {
		return mailNoOrTelephone;
	}


}
