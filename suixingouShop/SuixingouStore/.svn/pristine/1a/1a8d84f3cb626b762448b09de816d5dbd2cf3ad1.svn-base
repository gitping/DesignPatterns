package com.yto.suixingouuser.activity.helper;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.BankReqJo;
import com.yto.zhang.util.modle.ResponseDTO2;
import com.yto.zhang.util.modle.ShopInfoReqJo;
import com.yto.zhang.util.modle.ShopInfoResJo;

public class StoreMyBackAccountActivityHelper {
	
	public void getData(BankReqJo req, final FRequestCallBack requestCallBack) {

		FrameRequest fr = FMakeRequest.postBank(req);
		FinalHttp http = new FinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("BankActivityHelper--getData: onSuccess " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, Object> dto2 = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, Object>>() {}.getType());
				requestCallBack.onSuccess(dto2);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				requestCallBack.onFailure(t, errorNo, strMsg);
				Trace.i("BankActivityHelper--getData: onFailure " + t);
			}

		});

	}
	
	
	public void getMyShopId(ShopInfoReqJo shoprq,final FRequestCallBack requestCallBack){
		FrameRequest fr = FMakeRequest.forShopId(shoprq);
		FinalHttp http = new FinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("StoreBackAcounthelper--forshopid: onSuccess " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, ShopInfoResJo> dto2 = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, ShopInfoResJo>>() {}.getType());
				requestCallBack.onSuccess(dto2);
			}
			
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				requestCallBack.onFailure(t, errorNo, strMsg);
				Trace.i("StoreBackAcounthelper--forshopid: onFailure " + t);
			}
			
		});
	}

}
