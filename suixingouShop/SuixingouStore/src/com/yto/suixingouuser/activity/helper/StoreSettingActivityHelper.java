package com.yto.suixingouuser.activity.helper;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;

import com.frame.lib.log.L;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.AfinalUtil;
import com.frame.sxgou.model.FrameRequest;
import com.frame.view.dialog.DialogLoading;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.yto.suixingouuser.activity.helper.model.IFCorrelation;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopAddEditReqJo;
import com.yto.zhang.util.modle.ShopAddEditResJo;

/**
 * 店铺信息获取和设置的helper
 * @author ShenHua
 * 2015年5月28日上午10:18:36
 */
public class StoreSettingActivityHelper {

	private Activity ac;
	private DialogLoading dl;
	public StoreSettingActivityHelper(Activity ac) {
		this.ac = ac;
	}
	
	/**获取店铺信息  M017
	 * @param callBack
	 */
	public void getShopDetail(final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(IFCorrelation.shopDetail);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("StoreSettingActivityHelper_getShopDetail,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, ShopAddEditResJo> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, ShopAddEditResJo>>() {}.getType());
				callBack.onSuccess(res);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("StoreSettingActivityHelper_getShopDetail,onFailure: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}
	
	/**店铺设置  M016
	 * @param req
	 * @param callBack
	 */
	public void shopSetting(ShopAddEditReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req, IFCorrelation.shopSetting);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("ExpressHelper,getReceiptScan,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
				callBack.onSuccess(res);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("ExpressHelper,getReceiptScan,fail: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}
}
