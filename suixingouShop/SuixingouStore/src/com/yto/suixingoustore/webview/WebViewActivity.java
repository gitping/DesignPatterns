package com.yto.suixingoustore.webview;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.modle.FWebViewCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.view.R;
import com.frame.view.webview.ProgressWebView;
import com.yto.suixingoustore.activity.express.FLoginActivity;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.log.Trace;

/**
 * WebView的使用类,加入返回控制等功能
 * @author andy
 */
public class WebViewActivity extends Activity {

	public static ProgressWebView mWebView;
	public static RelativeLayout progress;
	private TextView loading_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DialogUtil.showLoadingDialog(this, false);
		this.setContentView(R.layout.progresswebview);
		progress=(RelativeLayout)findViewById(R.id.web_loading);
		mWebView = (ProgressWebView) findViewById(R.id.progresswebview);
		loading_text=(TextView)findViewById(R.id.loading_text);
		loading_text.setVisibility(8);
		mWebView.setProgressbar(progress,new WebviewCallBack());
		Bundle bun = getIntent().getExtras();     
		String url = FConstants.shopRank;
		if(bun != null){
			url = bun.getString("url");
		}
		Trace.i("WebViewActivity,url: " + url);
		JSImpl jsImpl = JSImpl.getInstance();
		jsImpl.initAct(this);
		mWebView.init(jsImpl);
		mWebView.setMainActivity(this);
//		mWebView.loadUrl("file:///android_asset/main.html");
		mWebView.loadUrl(url);
//		mWebView.loadUrl(StaticVariable.home_url);
		// mWebView.loadUrl("http://www.baidu.com/");
	}
	
	class WebviewCallBack extends FWebViewCallBack{
		@Override
		public void onReceivedError(Object t) {
			DialogUtil.showTwoBntTextDialog(WebViewActivity.this, "网页加载错误", false, getResources().getColor(R.color.mainColor), null, "返回", "重试", new DialogClickCallBack() {
				@Override
				public void confirmClick(Object obj) {
					mWebView.loadUrl(FConstants.shopRank);
				}
				@Override
				public void cancelClick(Object obj) {
					super.cancelClick(obj);
					WebViewActivity.this.finish();
				}
			});
		}
		
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		String url = mWebView.getUrl();
		if (keyCode==KeyEvent.KEYCODE_BACK&&mWebView.canGoBack()&& !url.equals("file:///android_asset/error.html")) {
			mWebView.goBack();
			return true;
		}else
		{
			return super.onKeyDown(keyCode, event);
		}
	}
	
	
	

}