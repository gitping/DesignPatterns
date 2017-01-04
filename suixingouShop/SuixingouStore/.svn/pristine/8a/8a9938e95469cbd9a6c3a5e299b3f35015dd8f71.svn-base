package com.yto.suixingouuser.activity.helper;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.ExpressFeeReqJo;
import com.yto.zhang.util.modle.ExpressFeeResJo;

public class PriceOfContryActivityHelper {

	public void getData(ExpressFeeReqJo mOrderJo,final FRequestCallBack requestcallback)
	{FrameRequest fr = FMakeRequest.getpriceModle(mOrderJo);

	FinalHttp fh = new FinalHttp();
	fh.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
		@Override
		public void onSuccess(String t) {
			super.onSuccess(t);
			Trace.i("FMainActivityHelper,onSuccess: " + t);
			FMakeRequest.parseParameter(t, ExpressFeeResJo.class, requestcallback);
		}

		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			super.onFailure(t, errorNo, strMsg);
			Trace.i("FMainActivityHelper,onFailure: " + t);
			requestcallback.onFailure(t, FConstants.FAILUREERROR, strMsg);
		}
	});
	}
	
}
