package com.yto.suixingoustore.message;

import net.tsz.afinal.FinalHttp;

import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.activity.helper.model.FrameRequest;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.MsgNotifyReqJo;
import com.yto.zhang.util.modle.MsgNotifyResJo;

public class MessageManagerHelper {
	public void getData(MsgNotifyReqJo req, final FRequestCallBack requestCallBack) {
		FrameRequest fr = FMakeRequest.messagePush(req);
		FinalHttp fh = new FinalHttp();
		String t = (String) fh.postSync(fr.getUrl(), fr.getAp());
		Trace.i("MessageManagerHelper,onSuccess: " + t);
		FMakeRequest.parseParameter(t, MsgNotifyResJo.class, requestCallBack);
	}
}
