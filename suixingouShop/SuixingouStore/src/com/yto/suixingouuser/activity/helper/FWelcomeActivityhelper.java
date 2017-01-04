package com.yto.suixingouuser.activity.helper;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.alibaba.fastjson.JSON;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.AppVersionReqJo;
import com.frame.sxgou.model.FrameRequest;
import com.frame.sxgou.request.SxgRequest;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.yto.suixingouuser.model.VersionBean;
import com.yto.zhang.util.modle.LoginReqJo;

public class FWelcomeActivityhelper {

	/**
	 * login
	 * 
	 * @param uname
	 * @param upassword
	 * @param requestcallback
	 */
	public void getLoginInfo(LoginReqJo logrq, final FRequestCallBack requestcallback) {
		FLoginActivityHelper.getLoginInfo(logrq, requestcallback);
	}
	
	/**
	 * login
	 * 
	 * @param uname
	 * @param upassword
	 * @param requestcallback
	 */
	public void getLoginInfo(String uname, final FRequestCallBack requestcallback) {
		FLoginActivityHelper.getLoginInfo(uname, requestcallback);
	}
	
	
	/**随心购检查更新
	 * @param curVersion
	 * @param rcb
	 */
	public void sxgcheckForUpdata(AppVersionReqJo req,final FRequestCallBack rcb){
		SxgRequest.sxgcheckForUpdata(FConstants.BASEURL,req, rcb);
	}
	
	
	
	
	
	/**检查更新
	 * @param curVersion
	 * @param rcb
	 */
	public void checkUpdata(int curVersion,final FRequestCallBack rcb){
		FrameRequest ar = FMakeRequest.checkUpdata(curVersion+"");
		FinalHttp fh = new FinalHttp();
		fh.configTimeout(300000);
		fh.post(ar.getUrl(), new AjaxCallBack<String>(){
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				
				
				VersionBean vb = null;
				try {
					vb = JSON.parseObject(t, VersionBean.class);
				} catch (Exception e) {
					rcb.onFailure(e, FConstants.JSONERROR, "");
					e.printStackTrace();
				} 
				rcb.onSuccess(vb);
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				rcb.onFailure(t, FConstants.FAILUREERROR, strMsg);
			}
		});
	}
	
}
