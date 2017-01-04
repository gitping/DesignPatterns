package com.frame.lib.modle;


/**
 * 自定义Dialog点击事件的回调方法
 * @author 胡亚敏
 *
 */
public  abstract class DialogClickCallBack {
	

	/**
	 * dialog确定按钮的点击方法，如果只有一个按钮默认此方法
	 */
	public abstract void confirmClick(Object obj);
	
	/**
	 * 如果有中间的按钮点击，执行此方法
	 */
	public void centerClick(Object obj){}//dialog中间按钮点击方法
	
	/**
	 * dialog右边按钮的点击方法
	 */
	public void cancelClick(Object obj){}//dialog右边的方法
}
