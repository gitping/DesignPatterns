package com.yto.suixingouuser.activity.helper;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.frame.lib.modle.FRequestCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.CollectOrderByMailNoReqJo;
import com.yto.zhang.util.modle.CollectOrderByMailNoResJo;
import com.yto.zhang.util.modle.CollectOrderNumReqJo;
import com.yto.zhang.util.modle.CollectOrderNumResJo;
import com.yto.zhang.util.modle.ResponseDTO2;

public class CollectFindParcelHelper {
	
	public void getData(CollectOrderByMailNoReqJo req,final FRequestCallBack requestCallBack){
		FrameRequest fr=FMakeRequest.getSerachCollect(req);
		FinalHttp http=new FinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("CollectParcelActivityHelper--getDataCollect: onSuccess " + t);
				Gson gs=new Gson();
				ResponseDTO2<CollectOrderByMailNoResJo, Object> res=gs.fromJson(t, new TypeToken<ResponseDTO2<CollectOrderByMailNoResJo, Object>>() {}.getType());
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
}
