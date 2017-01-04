package com.yto.suixingouuser.activity.helper;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.util.AfinalUtil;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.DetailAccountReqJo;
import com.yto.zhang.util.modle.DetailAccountResJo;
import com.yto.zhang.util.modle.FundReqJo;
import com.yto.zhang.util.modle.FundResJo;
import com.yto.zhang.util.modle.ResponseDTO2;

public class FragmentHelper {
	
	public void getFincailData(FundReqJo fundrq,final FRequestCallBack requestCallBack){
		FrameRequest fr=FMakeRequest.getFincail(fundrq);
		FinalHttp http=AfinalUtil.getFinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("FragmentHelper--getFincailData: onSuccess " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, FundResJo> dto2 = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, FundResJo>>() {}.getType());
				requestCallBack.onSuccess(dto2); 
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				requestCallBack.onFailure(t, errorNo, strMsg);
				Trace.i("FragmentHelper--getFincailData: onFailure " + t);
			}
			
		});
	}
	
	public void getAccountDetailData(DetailAccountReqJo detailrq,final FRequestCallBack requestCallBack){
		FrameRequest fr=FMakeRequest.getAccountDetail(detailrq);
		FinalHttp http=AfinalUtil.getFinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("FragmentHelper--getAccountDetailData: onSuccess " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, DetailAccountResJo> dto2 = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, DetailAccountResJo>>() {}.getType());
				requestCallBack.onSuccess(dto2); 
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				requestCallBack.onFailure(t, errorNo, strMsg);
				Trace.i("FragmentHelper--getAccountDetailData: onFailure " + t);
			}
			
		});
	}

}
