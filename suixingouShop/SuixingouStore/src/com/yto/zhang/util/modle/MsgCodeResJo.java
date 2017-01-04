package com.yto.zhang.util.modle;

import java.io.Serializable;

/**
 * @description 验证码响应
 * @author x.zl
 * @date 2015/1/9
 */
public class MsgCodeResJo implements Serializable{
	
	/**
	 * 验证码
	 */
	private String validCode;

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	

}
