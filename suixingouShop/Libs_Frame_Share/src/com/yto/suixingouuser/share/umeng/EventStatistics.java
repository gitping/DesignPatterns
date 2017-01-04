package com.yto.suixingouuser.share.umeng;

import java.util.Map;

import android.content.Context;

import com.frame.lib.constant.FrameConstant;
import com.umeng.analytics.MobclickAgent;

/**
 * 事件统计工具类
 * @author Andy
 * Create on 2014 2014-10-20 上午10:48:03
 */
public class EventStatistics {

	
	/**计算事件********************************************************************************************/
	public static void statisticsCalculate(Context con,int id, Map<String,String> m, int du){
		MobclickAgent.onEventValue(FrameConstant.appCon, getString(FrameConstant.appCon,id), m, du);
	}
	
	
	/**计数事件********************************************************************************************/
	
	public static void statisticsCount(Context con,int id){
		MobclickAgent.onEvent(FrameConstant.appCon, getString(FrameConstant.appCon,id));
	}
	public static void statisticsCount(Context con,int id,Map<String,String> m){
		MobclickAgent.onEvent(FrameConstant.appCon, getString(FrameConstant.appCon,id),m);
	}
	
	public static String getString(Context con,int id){
		String re = "";
		try {
			re = con.getString(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}
	
}
