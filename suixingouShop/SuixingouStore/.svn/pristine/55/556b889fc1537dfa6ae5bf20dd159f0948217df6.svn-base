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
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.yto.suixingouuser.activity.helper.model.IFCorrelation;
import com.yto.suixingouuser.util.AfinalUtil;
import com.yto.zhang.util.modle.Dictionary;
import com.yto.zhang.util.modle.LoginBusinessResJo;
import com.yto.zhang.util.modle.LoginUserReqJo;
import com.yto.zhang.util.modle.MsgCodeReqJo;
import com.yto.zhang.util.modle.MsgCodeResJo;
import com.yto.zhang.util.modle.RegisterBusinessUserReqJo;
import com.yto.zhang.util.modle.RegisterBusinessUserResJo;
import com.yto.zhang.util.modle.RegisterUserReqJo;
import com.yto.zhang.util.modle.RegisterUserResJo;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopAddEditReqJo;
import com.yto.zhang.util.modle.UpdatePasswordReqJo;

public class LoginAndRegisterHelper {

	/**
	 * @param phoneNumvoid
	 */
	private Activity ac;
	private DialogLoading dl;
	public LoginAndRegisterHelper(Activity ac) {
		this.ac = ac;
	}
	
	/**
	 * 校验验证码
	 * 
	 * @param req
	 * @param requestcallbackvoid
	 */
	public void VerificationCodeCheck(MsgCodeReqJo req, final FRequestCallBack requestcallback) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req, FConstants.VertionCodeCheck);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				L.i("FLoginActivityHelper,getVerificationCode,onSuccess: " + t);
				dl.dismiss();
				Gson gs = new Gson();
				ResponseDTO2<Object, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
				requestcallback.onSuccess(res);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("FLoginActivityHelper,getVerificationCode,onFailure: " + t);
				requestcallback.onFailure(t, -2, "internet error");
			}
		});
	}

	/**
	 * 获取验证码
	 * 
	 * @param req
	 * @param requestcallbackvoid
	 */
	public void getVerificationCode(MsgCodeReqJo req, final FRequestCallBack requestcallback) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req, FConstants.VertionCode);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("FLoginActivityHelper,getVerificationCode,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, MsgCodeResJo> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, MsgCodeResJo>>() {}.getType());
				requestcallback.onSuccess(res);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("FLoginActivityHelper,getVerificationCode,onFailure: " + t);
				requestcallback.onFailure(t, -2, "internet error");
			}
		});
	}
	/**
	 * 找回密码获取验证码
	 * 
	 * @param req
	 * @param requestcallbackvoid
	 */
	public void getVerificationCodeFindPW(MsgCodeReqJo req, final FRequestCallBack requestcallback) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req, FConstants.VertionCodeFindPW);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("FLoginActivityHelper,getVerificationCode,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, MsgCodeResJo> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, MsgCodeResJo>>() {}.getType());
				requestcallback.onSuccess(res);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				L.i("FLoginActivityHelper,getVerificationCode,onFailure: " + t);
				requestcallback.onFailure(t, -2, "internet error");
			}
		});
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
	
	/**
	 * 登录
	 * @param req
	 * @param requestcallback
	 */
	public void login(LoginUserReqJo req, final FRequestCallBack requestcallback) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req, FConstants.LogIn);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				L.i("FLoginActivityHelper,getVerificationCode,onSuccess: " + t);
				dl.dismiss();
				Gson gs = new Gson();
				ResponseDTO2<Object, LoginBusinessResJo> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, LoginBusinessResJo>>() {}.getType());
				requestcallback.onSuccess(res);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				L.i("FLoginActivityHelper,getVerificationCode,onFailure: " + t);
				dl.dismiss();
				requestcallback.onFailure(t, -2, "internet error");
			}
		});
	}
	/**
	 * 找回密码
	 * @param req
	 * @param requestcallback
	 */
	public void findPW(UpdatePasswordReqJo req, final FRequestCallBack requestcallback) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req, FConstants.FindPW);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				L.i("FLoginActivityHelper,getVerificationCode,onSuccess: " + t);
				dl.dismiss();
				Gson gs = new Gson();
				ResponseDTO2<Object, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
				requestcallback.onSuccess(res);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				L.i("FLoginActivityHelper,getVerificationCode,onFailure: " + t);
				dl.dismiss();
				requestcallback.onFailure(t, -2, "internet error");
			}
		});
	}

	/**获取店铺类型  M009
	 * @param req
	 * @param callBack
	 */
	public void getShopType(Dictionary req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req, IFCorrelation.getShopType);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				dl.dismiss();
				L.i("ExpressHelper,getReceiptScan,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Dictionary, Object> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Dictionary, Object>>() {}.getType());
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
	
	/**新的注册  M007 2015/5/25
	 * @param req
	 * @param callBack
	 */
	public void registerNew(RegisterBusinessUserReqJo req, final FRequestCallBack callBack) {
		dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequest(req, IFCorrelation.registerNew);
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

}
