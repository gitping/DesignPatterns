package com.yto.zhang.util.modle;

import java.io.Serializable;

/**
 * 获取手机验证码
 * @author longyong
 *
 */
public class CodeResJo implements Serializable {

	/**
	 * 手机短信验证码
	 */
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
