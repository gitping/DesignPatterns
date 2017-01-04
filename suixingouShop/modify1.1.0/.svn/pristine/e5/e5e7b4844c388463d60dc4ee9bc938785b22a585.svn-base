package com.yto.suixingouuser.activity.helper;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.activity.helper.model.FrameRequest;
import com.yto.suixingouuser.model.VersionBean;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.AppVersionResJo;
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
	public void sxgcheckForUpdata(int curVersion,final FRequestCallBack rcb){
		FrameRequest ar = FMakeRequest.checkUpdata(curVersion+"");
		FinalHttp fh = new FinalHttp();
		fh.post(ar.getUrl(),ar.getAp(), new AjaxCallBack<String>(){
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("checkForUpdata,onSuccess: " + t);
				FMakeRequest.parseParameter(t, AppVersionResJo.class, rcb);
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Trace.i("checkForUpdata,onFailure: " + t);
				rcb.onFailure(t, FConstants.FAILUREERROR, strMsg);
			}
		});
	}
	
	
	
	
	
	/**检查更新
	 * @param curVersion
	 * @param rcb
	 */
	public void checkUpdata(int curVersion,final FRequestCallBack rcb){
		FrameRequest ar = FMakeRequest.checkUpdata(curVersion+"");
		FinalHttp fh = new FinalHttp();
		fh.post(ar.getUrl(), new AjaxCallBack<String>(){
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				
				
				ObjectMapper om = new ObjectMapper();
				VersionBean vb = null;
				om.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				try {
					vb = om.readValue(t, VersionBean.class);
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
