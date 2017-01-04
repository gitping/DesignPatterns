package com.yto.zhang.util.modle;



/**
 * 更新代收包裹订单的状态
 * 
 * @author jingzhong
 * 
 */

public class CollectOrderUpdateReqJo extends UUID {

	/**
	 * 订单id
	 */
	private Long id;

	/**
	 * 订单状态
	 */
	private String orderStatus;// 0：待取件 ,1：取件中, 2：已取件, 3：取件异常

	private String type;// 0:app 取件 1：密码取件

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
