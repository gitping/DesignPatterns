package com.yto.suixingouuser.activity.helper;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.yto.suixingouuser.model.UserInfo;
import com.yto.suixingouuser.util.AfinalUtil;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.CodeReqJo;
import com.yto.zhang.util.modle.CodeResJo;
import com.yto.zhang.util.modle.LoginReqJo;
import com.yto.zhang.util.modle.LoginResJo;

public class FLoginActivityHelper {
	
	
	/**
	 * @param phoneNumvoid
	 */
	public static void getLoginInfo(String phoneNum, final FRequestCallBack requestcallback){
		if(TextUtils.isEmpty(phoneNum)){
			requestcallback.onFailure(null, -3, "uname or upassword is null");
		}else{
			
		}
	}
	/**
	 * login
	 * 登录
	 * 
	 */
	public void getData(LoginReqJo logrq,final FRequestCallBack requestcallback){
			FrameRequest fr=FMakeRequest.LoginDetail(logrq);
			FinalHttp http=AfinalUtil.getFinalHttp();
			http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
				@Override
				public void onSuccess(String t) {
					super.onSuccess(t);
					Trace.i("FLoginActivityHelper,onSuccess:"+t);
					FMakeRequest.parseParameter(t, LoginResJo.class, requestcallback);
				}
				
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					super.onFailure(t, errorNo, strMsg);
					Trace.i("FLoginActivityHelper,onFailure: " + t);
					requestcallback.onFailure(t, FConstants.JSONERROR, "internet error");
				}
			});
//		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * login
	 * @author zl获取短信验证码
	 * 
	 */
	
	public void getVerificationCode(CodeReqJo coderq,final FRequestCallBack requestcallback){
		FrameRequest fr=FMakeRequest.getVerificationCode(coderq);
		FinalHttp http=AfinalUtil.getFinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("FLoginActivityHelper,onSuccess:"+t);
				FMakeRequest.parseParameter(t,CodeResJo.class,requestcallback);
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Trace.i("FLoginActivityHelper,onFailure: " + t);
				requestcallback.onFailure(t, -2, "internet error");
			}
			
			
		});
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * login
	 * @param uname
	 * @param upassword
	 * @param requestcallback
	 */
	public static void getLoginInfo(LoginReqJo logrq, final FRequestCallBack requestcallback) {
			FrameRequest ar = FMakeRequest.LoginDetail(logrq);
			FinalHttp fh = new FinalHttp();
			fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
				@Override
				public void onSuccess(String t) {
					super.onSuccess(t);
					Log.v("loginn", t);
					UserInfo user = new UserInfo();
					try {
						user =JSON.parseObject(t, UserInfo.class);
						requestcallback.onSuccess(user);
					} catch (Exception e) {
						requestcallback.onFailure(e, -1, "uname or upassword error");
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					super.onFailure(t, errorNo, strMsg);
					requestcallback.onFailure(t, -2, "internet error");
				}
			});
		}
//	}
}
