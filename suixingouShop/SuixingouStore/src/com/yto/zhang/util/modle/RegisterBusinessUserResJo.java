package com.yto.zhang.util.modle;



/**
 * @description 注册商家用户
 * @author x.zl
 * @date 2015/1/16
 */
public class RegisterBusinessUserResJo extends UUID{
	
	private String shopName;
	
	private Integer level;
	

	
	private String prompt;
	
	private Integer shopId;
	
	private String insertAuthority;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getInsertAuthority() {
		return insertAuthority;
	}

	public void setInsertAuthority(String insertAuthority) {
		this.insertAuthority = insertAuthority;
	}
	
	
	
}
