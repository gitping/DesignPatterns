package com.yto.zhang.util.modle;


/**
 * 
 * 转运接口 M012
二次签收接口 M013
 * @author thw
 * 
 */
public class CollectOrderM012ReqJo extends UUID {
 
private String 	mailNo ;//快递单号
	private String expressNo;//    快递公司
	private String  id ;//  数据库ID
	private String pageIndex, pageSize;
	public String getMailNo() {
		return mailNo;
	}
	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
	
 
}
