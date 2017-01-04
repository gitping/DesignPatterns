package com.yto.zhang.util.modle;

/**
 * @description 注册JSON对象
 * @author x.zl
 * @date 2015/1/9
 */
public class RegisterUserReqJo extends UUID{
	
	/**登陆密码*/
	public String password;
	/**手机的唯一标记(买家可不传，商家必传)*/
	public String mac;
	/**手机号码*/
	public String mobile;
	/**移动端类型, 0: 买家版   1:商家版  2：快递员版*/
	public Integer role;
	/**公司名称*/
	public String companyName;
	/**验证码*/
	public String validCode;
	/**IOS push Token,IOS必填*/
	public String deviceNum;
	/**Android 1, ios 2*/
	public Integer systemType;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getValidCode() {
		return validCode;
	}
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	public String getDeviceNum() {
		return deviceNum;
	}
	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}
	public Integer getSystemType() {
		return systemType;
	}
	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
	} 
	
	
	

}
