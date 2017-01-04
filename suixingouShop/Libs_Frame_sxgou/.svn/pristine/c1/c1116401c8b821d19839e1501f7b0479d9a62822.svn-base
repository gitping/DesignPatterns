package com.frame.sxgou.request;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.frame.lib.log.L;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.AfinalUtil;
import com.frame.sxgou.SxgouFrameUtil;
import com.frame.sxgou.constants.IFCorrelation;
import com.frame.sxgou.constants.SXGConstants;
import com.frame.sxgou.model.AppVersionReqJo;
import com.frame.sxgou.model.AppVersionResJo;
import com.frame.sxgou.model.FrameRequest;
import com.frame.sxgou.model.ResponseDTO2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SxgRequest {
	
	
	/**
	 * 包装参数
	 * 
	 * @param requestDTO
	 * @returnFrameRequest
	 */
	public static FrameRequest packFrameRequest(Object obj, String method,String baseUrl) {
		FrameRequest hr = SxgouFrameUtil.packRequest(obj, method, SXGConstants.getCipher(), SXGConstants.getUUID(), SXGConstants.getVersion());
		hr.setUrl(baseUrl);
		return hr;
	}
	
	/**随心购检查更新
	 * @param curVersion
	 * @param rcb
	 */
	public static void sxgcheckForUpdata(String baseUrl,AppVersionReqJo req ,final FRequestCallBack callBack){
		FrameRequest ar = packFrameRequest(req,IFCorrelation.sxgcheckForUpdata,baseUrl);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		fh.post(ar.getUrl(),ar.getAp(), new AjaxCallBack<String>(){
			@Override
			public void onSuccess(String t) {
				L.i("sxgcheckForUpdata,onSuccess: " + t);
				Gson gs = new Gson();
				ResponseDTO2<Object, AppVersionResJo> res = gs.fromJson(t, new TypeToken<ResponseDTO2<Object, AppVersionResJo>>() {}.getType());
				callBack.onSuccess(res);
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				L.i("sxgcheckForUpdata,onFailure: " + t);
				callBack.onFailure(t, errorNo, strMsg);
			}
		});
	}

}
