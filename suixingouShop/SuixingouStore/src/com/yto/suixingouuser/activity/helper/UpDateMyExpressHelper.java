package com.yto.suixingouuser.activity.helper;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.ExpressOrderUpdateReqJo;
import com.yto.zhang.util.modle.ExpressOrderUpdateResJo;
public class UpDateMyExpressHelper {
	public void getData(ExpressOrderUpdateReqJo mExpressJo,final FRequestCallBack requestcallback)
	{
		FrameRequest fr = FMakeRequest.ExpressStutasUpdate(mExpressJo);

		FinalHttp fh = new FinalHttp();
		fh.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("UpDateMyExpressHelper,onSuccess: " + t);
				FMakeRequest.parseParameter(t, ExpressOrderUpdateResJo.class, requestcallback);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Trace.i("UpDateMyExpressHelper,onFailure: " + t);
				requestcallback.onFailure(t, FConstants.FAILUREERROR, strMsg);
			}
		});
	}
}
