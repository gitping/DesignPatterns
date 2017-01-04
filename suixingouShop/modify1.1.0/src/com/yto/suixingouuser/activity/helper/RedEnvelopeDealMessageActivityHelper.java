package com.yto.suixingouuser.activity.helper;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yto.suixingouuser.activity.helper.model.FMakeRequest;
import com.yto.suixingouuser.activity.helper.model.FRequestCallBack;
import com.yto.suixingouuser.activity.helper.model.FrameRequest;
import com.yto.suixingouuser.util.log.Trace;
import com.yto.zhang.util.modle.InviteSubsidyDetailReqJo;
import com.yto.zhang.util.modle.InviteSubsidyDetailResJo;
import com.yto.zhang.util.modle.InviteSubsidyReqJo;
import com.yto.zhang.util.modle.InviteSubsidyResJo;
import com.yto.zhang.util.modle.RedEnvelopesStatisticsReqJo;
import com.yto.zhang.util.modle.RedEnvelopesStatisticsResJo;
import com.yto.zhang.util.modle.ResponseDTO2;

public class RedEnvelopeDealMessageActivityHelper {
	
	
	public void getData(RedEnvelopesStatisticsReqJo redrq,final FRequestCallBack requestCallBack){
		FrameRequest fr=FMakeRequest.getRedDealMessage(redrq);
		FinalHttp http=new FinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Trace.i("RedEnvelopeDealMessageActivityHelper--getData: onSuccess " + t);
//				FMakeRequest.parseParameter(t, RedEnvelopesStatisticsResJo.class, requestCallBack);
				Gson gs = new Gson();
				ResponseDTO2<RedEnvelopesStatisticsResJo, Double> dto2 = gs.fromJson(t, new TypeToken<ResponseDTO2<RedEnvelopesStatisticsResJo, Double>>() {}.getType());
				requestCallBack.onSuccess(dto2);
			}
			
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				requestCallBack.onFailure(t, errorNo, strMsg);
				Trace.i("RedEnvelopeDealMessageActivityHelper--getData: onFailure " + t);
			}
			
		});
	}
	
	
	public void getRewardData(InviteSubsidyReqJo inreq,final FRequestCallBack requestCallBack){
		FrameRequest fr=FMakeRequest.getRewardMessage(inreq);
		FinalHttp http=new FinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("RedEnvelopeDealMessageActivityHelper--getRewardData: onSuccess " + t);
				Gson gs=new Gson();
				ResponseDTO2<InviteSubsidyResJo, Double> dto2 = gs.fromJson(t, new TypeToken<ResponseDTO2<InviteSubsidyResJo, Double>>() {}.getType());
				requestCallBack.onSuccess(dto2);
			}
			
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				requestCallBack.onFailure(t, errorNo, strMsg);
				Trace.i("RedEnvelopeDealMessageActivityHelper--getRewardData: onFailure " + t);
			}
			
		});
	}
	
	
	public void getRewardDetailData(InviteSubsidyDetailReqJo indreq,final FRequestCallBack requestCallBack){
		FrameRequest fr=FMakeRequest.getRewardDetailMessage(indreq);
		FinalHttp http=new FinalHttp();
		http.post(fr.getUrl(), fr.getAp(), new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				Trace.i("RedEnvelopeDealMessageActivityHelper--getRewardDetailData: onSuccess " + t);
				Gson gs=new Gson();
				ResponseDTO2<InviteSubsidyDetailResJo, Object> dto2 = gs.fromJson(t, new TypeToken<ResponseDTO2<InviteSubsidyDetailResJo, Object>>() {}.getType());
				requestCallBack.onSuccess(dto2);
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				requestCallBack.onFailure(t, errorNo, strMsg);
				Trace.i("RedEnvelopeDealMessageActivityHelper--getRewardDetailData: onFailure " + t);
			}
			
		});
	}

}
