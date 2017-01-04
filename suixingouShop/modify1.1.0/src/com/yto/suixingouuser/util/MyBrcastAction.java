package com.yto.suixingouuser.util;

public class MyBrcastAction {
	/**
	 * 商家首页订单
	 */
	public static String RECEIVEORDER="com.receiver.order";//接单广播的action
	public static String DELIVERING="com.delivering.order";//配送广播的action
	public static String UPDATEHASHMAP="com.hash.order";//更改hashmap广播的action
	
	/**
	 * 商家 我的快递单
	 */
	public static String MYRECEIVEORDER="com.myreceiver.order";//接单广播的action
	public static String MYDELIVERING="com.mydelivering.order";//接单广播的action
	public static String MYUPDATEHASHMAP="com.mhash.order";//更改hashmap广播的action
	public static String MYHAVERECEIVE="com.mhash.havereceive";//更改hashmap广播的action
	public static String NUMNULL="com.null.order";//所有状态为0的广播
	
	public static String MYCOUNTRECEIVEORDER="com.mycount.receive";//接单广播的action
	public static String MYDELETECOUNTRECEIVEORDER="com.mydeletecount.receive";//接单广播的action
	
	/**
	 * 购物车
	 */
	//更改产品类目颜色action
	public static String CHANGECOLOR="com.changecolor.action";
	//获取购物车界面每个类目的数量的action
	public static String COATGORYNUM="com.cartnum.action";
	//商品删除的广播
	public static String DELETEPRODUCT="com.deleteproduct.action";
	//商品编辑的广播
	public static String EDITPRODUCT="com.editproduct.action";
	
}
