package com.yto.suixingouuser.activity.helper;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;

import com.frame.lib.log.L;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.frame.view.dialog.DialogLoading;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.yto.suixingouuser.activity.helper.model.IFCorrelation;
import com.yto.suixingouuser.util.AfinalUtil;
import com.yto.zhang.util.modle.CollectOrderGetByExplessPwdReqJo;
import com.yto.zhang.util.modle.CollectOrderGetByExplessPwdResJo;
import com.yto.zhang.util.modle.CollectOrderM012ReqJo;
import com.yto.zhang.util.modle.CollectOrderResJo;
import com.yto.zhang.util.modle.CollectOrderScanMailNoReqJo;
import com.yto.zhang.util.modle.CollectOrderStatusModifyResJo;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ScanMailNoReqJo;
import com.yto.zhang.util.modle.ScanMailNoResJo;

/**快递
 * @author andy
 * 2015年1月8日下午3:11:49
 */
public class ExpressHelper {
	
	private Activity ac;
	private DialogLoading dl;
	public ExpressHelper(Activity ac) {
		this.ac = ac;
	}
	
	/**收件扫码  M011
	 * @param req
	 * @param callBack
	 */
	public void receiptScan(ScanMailNoReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req, IFCorrelation.receiptScan);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("ExpressHelper,getReceiptScan,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<ScanMailNoResJo, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<ScanMailNoResJo, Object>>() {}.getType());
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
	/**书面签收 M004
	 * @param req
	 * @param callBack
	 */
	public void signWritten(CollectOrderGetByExplessPwdReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req,IFCorrelation.signWritten);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("ExpressHelper,signWritten,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, CollectOrderStatusModifyResJo> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, CollectOrderStatusModifyResJo>>() {}.getType());
				callBack.onSuccess(res);
				
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("ExpressHelper,signWritten,onSuccess: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}
	
	
	
	/***密码取件 M005
	 * @param req
	 * @param callBack
	 */
	public void passwordPickUP(CollectOrderGetByExplessPwdReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req,IFCorrelation.passwordPickUP);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("ExpressHelper,passwordPickUP,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<CollectOrderGetByExplessPwdResJo, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<CollectOrderGetByExplessPwdResJo, Object>>() {}.getType());
				callBack.onSuccess(res);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("ExpressHelper,passwordPickUP,onFailure: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}
	/***包裹查询 M006
	 * @param req
	 * @param callBack
	 */
	public void parcelQuery(CollectOrderScanMailNoReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req,IFCorrelation.parcelQuery);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("ExpressHelper,parcelQuery,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<CollectOrderResJo, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<CollectOrderResJo, Object>>() {}.getType());
				callBack.onSuccess(res);
				
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("ExpressHelper,parcelQuery,onFailure: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}
	
	/***包裹转运M012
	 * @param req
	 * @param callBack
	 */
	public void parcelTransfer(CollectOrderM012ReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req,IFCorrelation.parcelTransfer);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("ExpressHelper,parcelTransfer,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, ScanMailNoResJo> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, ScanMailNoResJo>>() {}.getType());
				callBack.onSuccess(res);
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("ExpressHelper,parcelTransfer,onFailure: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}
	/***二次代收M013
	 * @param req
	 * @param callBack
	 */
	public void secondCollection(CollectOrderM012ReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req,IFCorrelation.secondCollection);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("ExpressHelper,parcelTransfer,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, ScanMailNoResJo> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, ScanMailNoResJo>>() {}.getType());
				callBack.onSuccess(res);
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("ExpressHelper,parcelTransfer,onFailure: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}
	/***客户拒收M014
	 * @param req
	 * @param callBack
	 */
	public void customerRefused(CollectOrderGetByExplessPwdReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req,IFCorrelation.customerRefused);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("ExpressHelper,parcelTransfer,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, CollectOrderStatusModifyResJo> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, CollectOrderStatusModifyResJo>>() {}.getType());
				callBack.onSuccess(res);
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("ExpressHelper,parcelTransfer,onFailure: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}
	
	
	
	/***扫码取件 C002
	 * @param req
	 * @param callBack
	 */
	public void scanPickUP(ScanMailNoReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req,IFCorrelation.scanPickUP);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("ExpressHelper,scanPickUP,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
				callBack.onSuccess(res);
				
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("ExpressHelper,scanPickUP,onFailure: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}
	
	
	/***订单详情 C003
	 * @param req
	 * @param callBack
	 */
	public void expressDetail(ScanMailNoReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req,IFCorrelation.expressDetail);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("ExpressHelper,expressDetail,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
				callBack.onSuccess(res);
				
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("ExpressHelper,expressDetail,onFailure: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}

}
