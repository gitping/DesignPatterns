package com.yto.suixingouuser.activity.helper.model;

/**成功,失败,开始,进度方法
 * @author 12345678
 */
public  abstract class FRequestCallBack{
	public void onStart() {
	}
	public void onLoading(long count, long current) {
	}
	public abstract void onSuccess(Object t);
	public abstract void onFailure(Throwable t, int errorNo, String strMsg);
}
