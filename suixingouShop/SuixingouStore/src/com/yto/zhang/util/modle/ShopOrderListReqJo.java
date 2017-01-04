package com.yto.zhang.util.modle;



/**
 * 
 * @author jingzhong
 * 
 */

public class ShopOrderListReqJo extends UUID {

	public static final String WAITING = "0";
	public static final String RECEIVING = "1";
	public static final String DELIVERING = "2";
	public static final String FINISHED = "3";
	/**
	 * user id
	 */
	private Integer id;

	/**
	 * 类型 0:待接单 1：已接单 2：配送中 3：已结束
	 */
	private String type;

	/**
	 * 页码
	 */
	private String pageIndex;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

}
