package com.yto.zhang.util.modle;

/**
 * @description 商家版登录
 * @author x.zl
 * @date 2015/1/21
 */
public class LoginBusinessResJo {
	
	private String uuid;
	
	private String companyName;
	
	private String insertAuthority;
	
	private String account;
	
	private String userName;
	
	private String bankFullName;
	
	private Integer shopId;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getInsertAuthority() {
		return insertAuthority;
	}

	public void setInsertAuthority(String insertAuthority) {
		this.insertAuthority = insertAuthority;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBankFullName() {
		return bankFullName;
	}

	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

}
