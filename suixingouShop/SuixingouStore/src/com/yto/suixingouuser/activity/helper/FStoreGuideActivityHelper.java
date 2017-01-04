package com.yto.suixingouuser.activity.helper;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.util.AfinalUtil;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.RedEnvelopesStatisticsResJo;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopAddEditReqJo;
import com.yto.zhang.util.modle.ShopAddEditResJo;
import com.yto.zhang.util.modle.ShopPauseReqJo;

public class FStoreGuideActivityHelper {
	
	
	public void getData(ShopAddEditReqJo shopAddRq,final FRequestCallBack fRequestCallBack){
		FrameRequest fr=FMakeRequest.OpenStoreDetail(shopAddRq);
		FinalHttp http=AfinalUtil.getFinalHttp();
		http.post(fr.getUrl(), fr.getAp(),new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("FStoreGuideActivityHelper,onSuccess:"+t);
				FMakeRequest.parseParameter(t, ShopAddEditResJo.class, fRequestCallBack);
			}
			
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Trace.i("FLoginActivityHelper,onFailure: " + t);
				fRequestCallBack.onFailure(t, FConstants.JSONERROR, "internet error");
				
			}
			
		});
	}
	
	
	
	public void changeShopPause(ShopPauseReqJo spaurq,final FRequestCallBack requestCallBack){
		FrameRequest fr=FMakeRequest.changeShopPau(spaurq);
		FinalHttp http=AfinalUtil.getFinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("changshopStateActivityHelper,onSuccess:"+t);
				Gson gs = new Gson();
				ResponseDTO2<Object, Object> dto2 = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
				requestCallBack.onSuccess(dto2);
			}
			
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Trace.i("changshopStateActivityHelper,onFailure:"+t);
				requestCallBack.onFailure(t, errorNo, strMsg);
//				requestCallBack.onFailure(t, FConstants.JSONERROR, "internet error");
			}
		});
	}

}
