package com.yto.zhang.util.modle;

/**
 * 快递单列表请求参数对象
 * 
 * @author Alex
 * @since 2015-01-08
 */
public class CollectOrderListReqJo extends UUID {

	private static final long serialVersionUID = 6027794203447998726L;
	
	private Integer shopId;
	private String orderStatus;
	private String startTime;
	private String endTime;
	private Integer currentPage;
	private Integer pageSize;
	
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
