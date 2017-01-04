package com.yto.suixingouuser.activity.helper;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.activity.helper.model.FrameRequest;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.OrderUpdateReqJo;
import com.yto.zhang.util.modle.OrderUpdateResJo;

public class UpDateMyOrderHelper {
	public void getData(OrderUpdateReqJo mOrderJo,final FRequestCallBack requestcallback)
	{
		FrameRequest fr = FMakeRequest.OrderStutasUpdate(mOrderJo);
		FinalHttp fh = new FinalHttp();
		fh.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("UpDateMyOrderHelper,onSuccess: " + t);
				FMakeRequest.parseParameter(t, OrderUpdateResJo.class,
						requestcallback);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Trace.i("UpDateMyOrderHelper,onFailure: " + t);
				requestcallback.onFailure(t, FConstants.FAILUREERROR, strMsg);
			}
		});
	}
}
