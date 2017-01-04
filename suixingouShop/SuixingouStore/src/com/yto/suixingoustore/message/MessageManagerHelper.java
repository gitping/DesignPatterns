package com.yto.suixingoustore.message;

import net.tsz.afinal.FinalHttp;

import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.frame.lib.modle.FRequestCallBack;
import com.frame.sxgou.model.FrameRequest;
import com.yto.suixingouuser.util.AfinalUtil;
import com.yto.suixingouuser.util.HttpUtil;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.MsgNotifyReqJo;
import com.yto.zhang.util.modle.MsgNotifyResJo;

public class MessageManagerHelper {
//	public void getData(MsgNotifyReqJo req, final FRequestCallBack requestCallBack) {
//		String t = "";
//		try {
//			String url = FMakeRequest.packFrameRequest2(req, "M24");
//			t = HttpUtil.getMethod2(url);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Trace.i("MessageManagerHelper,onSuccess: " + t);
//		FMakeRequest.parseParameter(t, MsgNotifyResJo.class, requestCallBack);
//	}
//	public void getData(MsgNotifyReqJo req, final FRequestCallBack requestCallBack) {
//		FrameRequestXutil fr = FMakeRequest.packFrameRequest1(req, "M24");
//		HttpUtils hu = new HttpUtils();
//		ResponseStream rs;
//		String t = "";
//		try {
//			rs = hu.sendSync(HttpMethod.POST, fr.getUrl(), fr.getRp());
//			t = rs.readString();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Trace.i("MessageManagerHelper,onSuccess: " + t);
//		FMakeRequest.parseParameter(t, MsgNotifyResJo.class, requestCallBack);
//	}
	
	
	
	
	
	public void getData(MsgNotifyReqJo req, final FRequestCallBack requestCallBack) {
		FrameRequest fr = FMakeRequest.messagePush(req);
		FinalHttp fh = AfinalUtil.getFinalHttp();
		String t = (String) fh.postSync(fr.getUrl(), fr.getAp());
		Trace.i("MessageManagerHelper,onSuccess: " + t);
		FMakeRequest.parseParameter(t, MsgNotifyResJo.class, requestCallBack);
		
	}
}
