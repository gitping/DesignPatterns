package com.yto.suixingouuser.activity.helper;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.frame.lib.modle.FRequestCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.yto.suixingouuser.activity.helper.model.IFCorrelation;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.util.AfinalUtil;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.CollectOrderNewReqJo;
import com.yto.zhang.util.modle.CollectOrderNewResJo;
import com.yto.zhang.util.modle.CollectOrderNewResJoPWD;
import com.yto.zhang.util.modle.CollectOrderNumReqJo;
import com.yto.zhang.util.modle.CollectOrderNumResJo;
import com.yto.zhang.util.modle.CollectOrderReqJo;
import com.yto.zhang.util.modle.CollectOrderResJo;
import com.yto.zhang.util.modle.CollectOrderUpdateReqJo;
import com.yto.zhang.util.modle.MsgNewReqJo;
import com.yto.zhang.util.modle.ResponseDTO2;

public class CollectParcelActivityHelper {
	
	public void getData(CollectOrderNumReqJo req,final FRequestCallBack requestCallBack){
		FrameRequest fr=FMakeRequest.getCollect(req);
		FinalHttp http=new FinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("CollectParcelActivityHelper--getDataCollect: onSuccess " + t);
				Gson gs=new Gson();
				ResponseDTO2<Object, CollectOrderNumResJo> res=gs.fromJson(t, new TypeToken<ResponseDTO2<Object, CollectOrderNumResJo>>() {}.getType());
				requestCallBack.onSuccess(res);
			}
			
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				requestCallBack.onFailure(t, errorNo, strMsg);
				Trace.i("CollectParcelActivityHelper--getDataCollect: onFailure " + t);
			}
			
		});
		
		
	}
	
	
	public void getData(CollectOrderReqJo req,final FRequestCallBack requestCallBack){
		FrameRequest fr=FMakeRequest.getCollectByPass(req);
		FinalHttp http=new FinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("CollectParcelActivityHelper--getDataCollectByPass: onSuccess " + t);
				Gson gs=new Gson();
				ResponseDTO2<CollectOrderResJo, Object> res=gs.fromJson(t, new TypeToken<ResponseDTO2<CollectOrderResJo, Object>>() {}.getType());
				requestCallBack.onSuccess(res);
			}
			
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				requestCallBack.onFailure(t, errorNo, strMsg);
				Trace.i("CollectParcelActivityHelper--getDataCollectByPass: onFailure " + t);
			}
			
		});
		
		
	}
	
	
	
	public void getData(CollectOrderNewReqJo req,final FRequestCallBack requestCallBack){
		FrameRequest fr=FMakeRequest.getTakeCollect(req);
		FinalHttp http=new FinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("CollectParcelActivityHelper--finish: onSuccess " + t);
				Gson gs=new Gson();
				ResponseDTO2<Object, CollectOrderNewResJo> res=gs.fromJson(t, new TypeToken<ResponseDTO2<Object, CollectOrderNewResJo>>() {}.getType());
				requestCallBack.onSuccess(res);
			}
			
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				requestCallBack.onFailure(t, errorNo, strMsg);
				Trace.i("CollectParcelActivityHelper--finish: onFailure " + t);
			}
			
		});
		
		
	}
	
	
	public void getData(MsgNewReqJo req,final FRequestCallBack requestCallBack){
		FrameRequest fr=FMakeRequest.sendOwnMessage(req);
		FinalHttp http=new FinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("CollectParcelActivityHelper--send: onSuccess " + t);
				Gson gs=new Gson();
				ResponseDTO2<Object, Object> res=gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
				requestCallBack.onSuccess(res);
			}
			
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				requestCallBack.onFailure(t, errorNo, strMsg);
				Trace.i("CollectParcelActivityHelper--send: onFailure " + t);
			}
			
		});
		
		
	}
	
	public void updateData(CollectOrderUpdateReqJo req,final FRequestCallBack requestCallBack){
		FrameRequest fr=FMakeRequest.updateStatus(req);
		FinalHttp http=new FinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("CollectParcelActivityHelper--send: onSuccess " + t);
				Gson gs=new Gson();
				ResponseDTO2<Object, Object> res=gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
				requestCallBack.onSuccess(res);
			}
			
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				requestCallBack.onFailure(t, errorNo, strMsg);
				Trace.i("CollectParcelActivityHelper--send: onFailure " + t);
			}
			
		});
		
		
	}
	
	/**
	 * 快递录入 M010（原M59接口） 2015/5/25
	 * @param req
	 * @param requestCallBack
	 */
	public void expressEntering(CollectOrderNewReqJo req,final FRequestCallBack requestCallBack){
		FrameRequest ar = FMakeRequest.packFrameRequest(req, IFCorrelation.shopExpressEntering);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("CollectParcelActivityHelper--finish: onSuccess " + t);
				Gson gs=new Gson();
				ResponseDTO2<Object, CollectOrderNewResJoPWD> res=gs.fromJson(t, new TypeToken<ResponseDTO2<Object, CollectOrderNewResJoPWD>>() {}.getType());
				requestCallBack.onSuccess(res);
			}
			
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				requestCallBack.onFailure(t, errorNo, strMsg);
				Trace.i("CollectParcelActivityHelper--finish: onFailure " + t);
			}
			
		});
	}
}
