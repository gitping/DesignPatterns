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
import com.yto.zhang.util.modle.CollectCommonStatus;
import com.yto.zhang.util.modle.CollectOrderInfo4ShopResJo;
import com.yto.zhang.util.modle.CollectOrderListReqJo;
import com.yto.zhang.util.modle.ResponseDTO2;

/**
 * 店铺的快件列表
 * @author ShenHua
 * 2015年4月16日上午9:30:54
 */
public class ExpressListHelper {

	private Activity ac;
	private DialogLoading dl;
	public ExpressListHelper(Activity ac) {
		this.ac = ac;
	}
	
	/**快递单状态列表列表
	 * @param callBack
	 */
	public void getstatusList(final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(IFCorrelation.orderStatus);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("ExpressListHelper_getstatusList,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<CollectCommonStatus, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<CollectCommonStatus, Object>>() {}.getType());
				callBack.onSuccess(res);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("ExpressListHelper_getstatusList,onFailure: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}
	
	/**获取首页快递单列表
	 * @param callBack
	 */
	public void getorderDetail(CollectOrderListReqJo req, final FRequestCallBack callBack){
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req, IFCorrelation.orderList);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("MainActivityHelper_getorderDetail,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<CollectOrderInfo4ShopResJo, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<CollectOrderInfo4ShopResJo, Object>>() {}.getType());
				callBack.onSuccess(res);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("MainActivityHelper_getorderDetail,onFailure: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}
}
