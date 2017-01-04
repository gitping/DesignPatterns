package com.yto.suixingouuser.activity.helper;

import android.app.Application;

import com.frame.lib.modle.FRequestCallBack;
import com.yto.suixingouuser.uti.baidul.GPSBaiduUtil;
import com.yto.suixingouuser.uti.baidul.GPSCallBack;

public class ChooseNeighborhoodsActivityHelper {
	
	public void getData(Application app,String key,final FRequestCallBack frcb){
		/*GPSBaiduUtil.getInstance().findAround(app,key, "上海", new GPSCallBack() {
//			GPSBaiduUtil.getInstance().findAround(key, FConstants.gpsBE.getCityName(), new GPSCallBack() {
			@Override
			public void onSuccess(Object t) {
				frcb.onSuccess(t);
			}
			
			@Override
			public void onFail(String err) {
				frcb.onFailure(null, 0, "");
			}
		});*/
	}

}

