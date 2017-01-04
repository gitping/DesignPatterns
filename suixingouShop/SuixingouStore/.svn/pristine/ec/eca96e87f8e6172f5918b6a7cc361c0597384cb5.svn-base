package com.yto.suixingouuser.activity.helper;

import java.util.Map;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;
import com.frame.lib.log.L;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.frame.view.dialog.DialogLoading;
import com.google.gson.JsonObject;
import com.suixingou.sdkcommons.constant.CMDFactory;
import com.suixingou.sdkcommons.constant.HttpConstant;
import com.suixingou.sdkcommons.packet.CResponseBody;
import com.suixingou.sdkcommons.utils.GsonUtil;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.yto.suixingouuser.util.AfinalUtil;

/**
 * 改版后的helper，所有请求都只用这一个helper
 * @author ShenHua
 * 2015年6月23日上午11:08:26
 */
public class MainHelper {

	private static MainHelper instance = null;
	
    public static MainHelper getInstance() {
		if (instance == null) {
			instance = new MainHelper();
		}
		return instance;
    }
    
	private DialogLoading dl;
    
    public void getDate(Byte cmd, Object reqObj, Map<String, String> map, final String methodCode, String uuid, final FRequestCallBack requestcallback){
    	FrameRequest ar = FMakeRequest.packFrameRequestMain(cmd, reqObj, map, methodCode, uuid);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				L.i("MainHelper_getDate" + methodCode + t);
				JsonObject jsonObject = GsonUtil.getBean(t, JsonObject.class);
				int code = jsonObject.get("code").getAsInt();
				String prompt = jsonObject.get("prompt").getAsString();
				//if(code == SXGConstants.success){
					if(jsonObject.get("cmd") != null){
						byte cmd = jsonObject.get("cmd").getAsByte();
						
						CResponseBody<?> res = null;
						try{
							res = GsonUtil.getBean(jsonObject, CMDFactory.buildRespTypeTokenByCmd(cmd).getType());							
						} catch (Exception e){
							e.printStackTrace();
							onFailure(null, code, "json解析错误");
						}
						requestcallback.onSuccess(res);
					}else{
						CResponseBody res = GsonUtil.getBean(jsonObject, CResponseBody.class);
						requestcallback.onSuccess(res);
					}
				/*}else{
					onFailure(null, code, prompt);
				}*/
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				requestcallback.onFailure(t, errorNo, strMsg);
			}
		});
    }
    
    public void getDateDialog(Byte cmd, Object reqObj, Map<String, String> map, final String methodCode, String uuid, Activity ac, final FRequestCallBack requestcallback){
    	dl = DialogLoading.getInstance(ac, false);
		dl.show();
		FrameRequest ar = FMakeRequest.packFrameRequestMain(cmd, reqObj, map, methodCode, uuid);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(), ar.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				L.i("MainHelper_getDateDialog" + methodCode + t);
				dl.dismiss();
				JsonObject jsonObject = GsonUtil.getBean(t, JsonObject.class);
				int code = jsonObject.get("code").getAsInt();
				String prompt = jsonObject.get("prompt").getAsString();
				//if(code == SXGConstants.success || code == 1045){
					if(jsonObject.get("cmd") != null){
						byte cmd = jsonObject.get("cmd").getAsByte();
						
						CResponseBody<?> res = null;
						try{
							res = GsonUtil.getBean(jsonObject, CMDFactory.buildRespTypeTokenByCmd(cmd).getType());						
						} catch (Exception e){
							e.printStackTrace();
							onFailure(null, code, "json解析错误");
						}
						requestcallback.onSuccess(res);
					}else{
						CResponseBody res = GsonUtil.getBean(jsonObject, CResponseBody.class);
						requestcallback.onSuccess(res);
					}
				/*}else{
					onFailure(null, code, prompt);
				}*/
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				dl.dismiss();
				requestcallback.onFailure(t, errorNo, strMsg);
			}
		});
    }
}
