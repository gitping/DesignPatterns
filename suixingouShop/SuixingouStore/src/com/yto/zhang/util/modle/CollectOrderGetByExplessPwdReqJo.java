package com.yto.zhang.util.modle;


/**
 * 密码取件M005
 * 书面签收也使用用这个M004
 * 
 * @author Tiansheng.Cheng
 * @since 2015-01-19
 */

public class CollectOrderGetByExplessPwdReqJo extends UUID {

	private static final long serialVersionUID = 1L;
	
	/** 面单号 */
	private String mailNo;
	/** 密码 */
	private String password;
	/** DB 主键 */
	private Long id;
	/** 用户电话 */
	private String userTel;
	
	public String getMailNo() {
		return mailNo;
	}
	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

}
