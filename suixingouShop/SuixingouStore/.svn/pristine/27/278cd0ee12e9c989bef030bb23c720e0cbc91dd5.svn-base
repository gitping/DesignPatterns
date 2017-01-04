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
import com.yto.zhang.util.modle.RegisterBusinessUserReqJo;
import com.yto.zhang.util.modle.RegisterBusinessUserResJo;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ScanMailNoReqJo;
import com.yto.zhang.util.modle.ScanMailNoResJo;

/**注册,登录
 * @author andy
 * 2015年1月8日下午3:11:49
 */
public class RegisterHelper {
	
	private Activity ac;
	private DialogLoading dl;
	public RegisterHelper(Activity ac) {
		this.ac = ac;
	}
	
	/**注册  M009
	 * @param req
	 * @param callBack
	 */
	public void register(RegisterBusinessUserReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req, IFCorrelation.register);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("ExpressHelper,getReceiptScan,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, RegisterBusinessUserResJo> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, RegisterBusinessUserResJo>>() {}.getType());
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
	public void signWritten(ScanMailNoReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req,IFCorrelation.signWritten);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("EvaluateListActivityHelper,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, ScanMailNoResJo> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, ScanMailNoResJo>>() {}.getType());
				callBack.onSuccess(res);
				
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("CarSettlementActivityHelper,onSuccess: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}
	
	
	
	/***密码取件 M005
	 * @param req
	 * @param callBack
	 */
	public void passwordPickUP(ScanMailNoReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req,IFCorrelation.passwordPickUP);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("EvaluateListActivityHelper,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
				callBack.onSuccess(res);
			
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("CarSettlementActivityHelper,onSuccess: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}
	/***包裹查询 M006
	 * @param req
	 * @param callBack
	 */
	public void parcelQuery(ScanMailNoReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req,IFCorrelation.parcelQuery);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("EvaluateListActivityHelper,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
				callBack.onSuccess(res);
				
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("CarSettlementActivityHelper,onSuccess: " + t);
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
				L.i("EvaluateListActivityHelper,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
				callBack.onSuccess(res);
				
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("CarSettlementActivityHelper,onSuccess: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}
	/***注册时提交店家信息M009
	 * @param req
	 * @param callBack
	 */
	public void submitStoreInfo(ScanMailNoReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req,IFCorrelation.register);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("EvaluateListActivityHelper,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
				callBack.onSuccess(res);
				
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("CarSettlementActivityHelper,onSuccess: " + t);
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
				L.i("EvaluateListActivityHelper,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
				callBack.onSuccess(res);
				
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("CarSettlementActivityHelper,onSuccess: " + t);
				callBack.onFailure(t, errorNo, "");
			}
		});
	}

}
