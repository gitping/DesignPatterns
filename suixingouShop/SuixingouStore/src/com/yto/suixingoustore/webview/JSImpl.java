package com.yto.suixingoustore.webview;


import android.content.Intent;

import com.frame.lib.modle.FRequestCallBack;
import com.frame.view.webview.JSIF;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.log.Trace;
/**网页调用android的接口
 * @author andy
 *
 */
public class JSImpl implements JSIF{
	
	private WebViewActivity act;
	
	private JSImpl(){};
	private static JSImpl jsImpl;
	public static JSImpl getInstance(){
		if(jsImpl == null){
			jsImpl = new JSImpl();
		}
		return jsImpl;
	}
	
	public void initAct(WebViewActivity act){
		this.act = act;
	}
	
	
	
	
	
	/**关闭webview
	 * void
	 */
	public void finish(){
		act.finish();
	}


}
