package com.yto.zhang.util.modle;

import java.io.Serializable;

/**
 * @description 验证码Json对象
 * @author x.zl
 * @date 2015-1-8
 */
public class MsgCodeReqJo implements Serializable{
	
	/**验证码有效时间  以分为单位*/
	public static final int ACTION_TIME=60;
	/**验证码类型   1：注册验证码        2：找回密码*/
	public static final int MSG_CODE_1=1,MSG_CODE_2=2;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 角色    0: 买家版   1:商家版  2：快递员版
	 */
	private Integer role;
	/**
	 * 验证码类型    1：注册验证码  2：找回密码
	 */
	private Integer type;
	/**
	 * 验证码
	 */
	private String code;
	/**校验用的验证码*/
	private String validCode;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValidCode() {
		return validCode;
	}
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	
}
