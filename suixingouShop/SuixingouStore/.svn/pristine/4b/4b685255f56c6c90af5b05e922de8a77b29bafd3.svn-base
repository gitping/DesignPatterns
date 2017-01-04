package com.yto.suixingouuser.activity.helper;

import java.util.HashMap;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.util.MyBrcastAction;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.ProductReqJo;
import com.yto.zhang.util.modle.ProductResJo;
import com.yto.zhang.util.modle.ResponseDTO;

public class StoreMyShopActivityHelper {

	Context mcontext;
	public StoreMyShopActivityHelper(Context mcontext) {
		super();
		this.mcontext = mcontext;
	}
	public void getData(ProductReqJo mOrderJo,final FRequestCallBack requestcallback)
	{
		FrameRequest fr = FMakeRequest.ShowMyGoods(mOrderJo);

		FinalHttp fh = new FinalHttp();
		fh.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("StoreMyShopActivityHelper,onSuccess: " + t);
				FMakeRequest.parseParameter(t, ProductResJo.class, requestcallback);
				ResponseDTO rdo = new ResponseDTO();
				rdo = JSON.parseObject(t, ResponseDTO.class);
				if (rdo.getCode() == FConstants.SUCCESS) {
					if (rdo.getResult().get("num")!=null) {
						String num=rdo.getResult().get("num").toString();
						@SuppressWarnings("unchecked")
						HashMap<String, Integer> hash = JSON.parseObject(num, HashMap.class);
						Intent intent=new Intent(MyBrcastAction.COATGORYNUM);
						intent.putExtra("hash", hash);
						mcontext.sendBroadcast(intent);
					}
						
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Trace.i("StoreMyShopActivityHelper,onFailure: " + t);
				requestcallback.onFailure(t, FConstants.FAILUREERROR, strMsg);
			}
		});
	}
}
