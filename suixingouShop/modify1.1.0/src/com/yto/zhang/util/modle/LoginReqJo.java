package com.yto.zhang.util.modle;



import java.io.Serializable;

/**
 * 客户端登录
 * 
 * @author longyong
 * 
 */
public class LoginReqJo implements Serializable {

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 手机验证码
	 */
	private String code;
	

	/**
	 * mac地址
	 */
	private String mac;
	
	
	/**
	 * 买家：0  商家1
	 */
	private String type;
	
	
	
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
