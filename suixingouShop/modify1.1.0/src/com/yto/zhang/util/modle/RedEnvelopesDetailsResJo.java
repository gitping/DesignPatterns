package com.yto.zhang.util.modle;

import java.util.List;

/**
 * 红包
 * @author Andy
 * Create on 2014 2014-5-19 下午5:22:19
 */
public class RedEnvelopesDetailsResJo extends Version{
	
	/**红包条目*/
	private RedEnvelopesStatisticsResJo redes;
	/**订单详情*/
	private List<ShopOrderDeatailResJo> orderDetails;
	public RedEnvelopesStatisticsResJo getRedes() {
		return redes;
	}
	public void setRedes(RedEnvelopesStatisticsResJo redes) {
		this.redes = redes;
	}
	public List<ShopOrderDeatailResJo> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<ShopOrderDeatailResJo> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	
}
